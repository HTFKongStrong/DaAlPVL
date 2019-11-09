package RCPSP;

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

		for (Integer integer : jobListe) {
			System.out.println(integer);
		}
	}

	public void decodeJobList(Job[] jobs, Resource[] res) {
		// calculate the starting times of the jobs in the order of jobListe

		schedule = new int[jobListe.length];

		// calculate the maximum possible makespan "maxDauer" of the project
		int maxDuration = 0;// alt shift R
		for (int i = 0; i < jobs.length; i++) {
			maxDuration += jobs[i].dauer;
		}

		int[][] resourcenTableau = new int[res.length][maxDuration];

		for (int i = 0; i < resourcenTableau.length; i++) {
			for (int j = 0; j < resourcenTableau[i].length; j++) {
				resourcenTableau[i][j] = res[i].maxVerfuegbarkeit();
			}
		}

		for (int i = 0; i < jobListe.length; i++) {

			int nr = jobListe[i];

			Job j = Job.getJob(jobs, nr);

			int p1 = earliestPossibleStarttime(j, jobs);
			int p2 = starttime(j, p1, resourcenTableau);
			actualizeResources(j, resourcenTableau, p2);

			schedule[i] = p2;
		}

	}

	public int earliestPossibleStarttime(Job x, Job[] y) {

		return 0;

	}

	public int starttime(Job x, int y, int[][] z) {

		return 0;
	}

	public int actualizeResources(Job x, int[][] y, int z) {

		return 0;
	}

}
