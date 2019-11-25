package OwnRCPSP;

import java.util.ArrayList;
import java.util.Collections;

import RCPSP.Job;
//Kann irgnieuwer werden - ist alt - evtl löschen
public class OwnSchedule {
	ArrayList<Integer> jobListe = new ArrayList<>();
	ArrayList<Integer> jobsGeplant = new ArrayList<>();
	ArrayList<Integer> jobsPlanbar = new ArrayList<>(); // alle Jobs die noch nicht in der JobListe sind
	ArrayList<Integer> jobsSuccesors = new ArrayList<>(); // 1.Stelle Jobnummer, zweite Stelle anzahlNachfolger
	ArrayList<Integer> jobsKOZ = new ArrayList<>(); // (Jobnummer, KOZ) sorted

	public void initializeJobListe(Job[] jobs) {
		// Erste DummyJob1 wird zur JobListe hinzugefügt
		jobListe.add(jobs[0].nummer());

		// alle Jobs zu Planbar hinzufügen
		for (Job job : jobs) {
			jobsPlanbar.add(job.nummer());
		}

		// für Berechnung der Prioritäten
		jobGetMostSuccesors(jobs);
		jobGetKOZDauer(jobs);

		// Berechnung welcher Job die kürzesten Nachfolger hat

		while (!jobsPlanbar.isEmpty()) {

		}
	}

	public void sortList(ArrayList<Integer> list) {
		Collections.sort(list); // check if sorted right
	}

	public void jobGetMostSuccesors(Job[] jobs) {
		// Berechnung welcher Job die meisten Nachfolger hat
		for (Job job : jobs) {
			int anzahlNachfolger = job.anzahlNachfolger();
			jobsSuccesors.add(job.nummer(), anzahlNachfolger);
		}
		sortList(jobsSuccesors);
	}

	public void jobGetKOZDauer(Job[] jobs) {
		// Berechnung welcher Job die kürzeste Dauer hat
		for (Job job : jobs) {
			int dauer = job.dauer();
			jobsKOZ.add(job.nummer(), dauer);
		}
		sortList(jobsKOZ);
	}

	public void jobGetKOZResource(Job[] jobs) {
		// Berechnung welcher Job am wenigsten Resourcenlastig ist
		// sss

	}
}
