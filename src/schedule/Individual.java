package schedule;
//Klasse in Schedule umbennenen

import java.util.ArrayList;

public class Individual {
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

			// ideeen

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
				}
			}
		}
	}
}
