package RCPSP;
//Klasse in Schedule umbennenen

import java.util.ArrayList;

public class Schedule {
	int[] jobListe;
	int[] schedule;

	public void initializeJobListe(Job[] jobs) {
		ArrayList<Job> eligibleJobs = new ArrayList<>();
		jobListe = new int[jobs.length];
		// add Job to jobListe
		int count = 0;
		jobListe[count] = jobs[0].nummer();
		count++;
		ArrayList<Integer> nachfolgerAkt = jobs[0].nachfolger;

		for (int i = 0; i < nachfolgerAkt.size(); i++) {
			eligibleJobs.add(Job.getJob(jobs, nachfolgerAkt.get(i)));
		}

		while (count != jobs.length) {
			Job min = eligibleJobs.get(0);
			int minDauer = eligibleJobs.get(0).dauer();
			for (int i = 0; i < eligibleJobs.size(); i++) {
				if (eligibleJobs.get(i).dauer < minDauer) {
					minDauer = eligibleJobs.get(i).dauer;
					min = eligibleJobs.get(i);
				}
			}

			jobListe[count] = min.nummer;
			count++;
			eligibleJobs.remove(min);
			nachfolgerAkt = min.nachfolger();

			for (int i = 0; i < nachfolgerAkt.size(); i++) {
				Job aktuellerNachfolgerJob = Job.getJob(jobs, nachfolgerAkt.get(i));
				ArrayList<Integer> vorgaengerAkt = aktuellerNachfolgerJob.vorgaenger;
				boolean alleVorgaenger = true;
				for (int j = 0; j < vorgaengerAkt.size(); j++) {
					boolean found = false;
					for (int k = 0; k < jobListe.length; k++) {
						if (jobListe[k] == vorgaengerAkt.get(j)) {
							found = true;
						}
					}
					if (!found) {
						alleVorgaenger = false;
						break;
					}

				}
				if (alleVorgaenger) {
					eligibleJobs.add(Job.getJob(jobs, nachfolgerAkt.get(i)));
				}
			}
		}
	}
}
