package RCPSP;

import java.util.ArrayList;

public class CreateSchedule {
	
	ArrayList<Job> jobsListe = new ArrayList<Job>();

	public static void createSchedule(Job[] jobs) {
		
		int jobToSchedule = jobs.length;
		int jobsScheduled = 0;
		
		while (jobsScheduled < jobToSchedule) {
			for (int i = 0; i < jobs.length; i++) {
				int jobNr = jobs[i].nummer;
				
				jobs[i].nachfolger;
			}
			
		}
	}

}
