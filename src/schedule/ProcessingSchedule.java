package schedule;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import RCPSP.Schedule;
import RCPSP.Job;
import RCPSP.Resource;

public class ProcessingSchedule {

	public static void main(String[] args) throws FileNotFoundException {
//		Job[] jobs     = Job.read(new File("j1201_5.sm"));//best makespan=112
//		Resource[] res = Resource.read(new File("j1201_5.sm"));
		Job[] jobs = Job.read(new File("j12046_8.sm"));
		Resource[] res = Resource.read(new File("j12046_8.sm"));
		
		


//For Loop Homberger for calculate the Predecessors of every item in jobs		
//		for (int i = 0; i < jobs.length; i++) {
//			jobs[i].calculatePredecessors(jobs);
//		}

// CalcualtePredecessors
		Job.calculatePredecessors(jobs);

		//Schedule s = new Schedule();
		//s.initializeJobListe(jobs);

		auslesen(jobs);
		auslesen(res);
		
		//System.out.println("\n-------------------------------\n");
		
		//System.out.println("Ressource \n--------------\n" + res[1].toString()+"\n");
		
		//System.out.println("Job\n-------------\n"+ jobs[1].toString()+"\n");
	}

//Code for Console Print
	private static void auslesen(Job[] jobs) {
		int gesamtDauer = 0;
		for (int i = 0; i < jobs.length; i++) {
			gesamtDauer += jobs[i].dauer();

			System.out.print("Nummer: " + jobs[i].nummer() + "     |    ");
			System.out.print("Nachfolger: ");
			ArrayList<Integer> nachfolger = jobs[i].nachfolger();
			for (int j = 0; j < nachfolger.size(); j++) {
				System.out.print(" " + nachfolger.get(j) + " ");

			}
			System.out.print(" Vorgaenger: ");
			ArrayList<Integer> vorgaenger = jobs[i].vorgaenger();
			for (int j = 0; j < vorgaenger.size(); j++) {
				System.out.print(" " + vorgaenger.get(j) + " ");

			}
			System.out.print("     |    ");
			System.out.print("Dauer: " + jobs[i].dauer() + "     |    ");
			System.out.println("R1: " + jobs[i].verwendeteResource(0) + "  R2: " + jobs[i].verwendeteResource(1)
					+ "  R3: " + jobs[i].verwendeteResource(2) + "  R4: " + jobs[i].verwendeteResource(3));
		}
		System.out.println("T = " + gesamtDauer);
	}

	private static void auslesen(Resource[] resource) {
		for (int i = 0; i < resource.length; i++) {
			System.out.print("Resource: " + resource[i].nummer() + "     |    ");
			System.out.println("Verfuegbarkeit: " + resource[i].maxVerfuegbarkeit());
		}
	}

}
