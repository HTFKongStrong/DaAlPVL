package RCPSP;

import java.util.ArrayList;

public class CreateSchedule {

	public static void createSchedule(Job[] jobs) {

		ArrayList<Job> jobsListe = new ArrayList<Job>();
		ArrayList<Integer> schedule = new ArrayList<>();

		int jobToSchedule = jobs.length;
		int jobsScheduled = 0;

		while (jobsScheduled < jobToSchedule) {
			for (int i = 0; i < jobs.length; i++) {

				if (schedule.isEmpty()) {
					schedule.add(jobs[jobs.length].nummer);
				}

				for (Integer vorganger : jobs[jobs.length].vorgaenger) {
					schedule.add(vorganger);

				}
			}

		}
	}

}