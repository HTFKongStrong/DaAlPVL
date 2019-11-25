package RCPSP;
//Klasse in Schedule umbennenen

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Schedule {
	int[] jobListe;
	int[] schedule;

	// Berechnet eine zulaessige Jobliste
	public void initializeJobListe(Job[] jobs) {
		ArrayList<Job> eligibleJobs = new ArrayList<>(); //planbare Jobs
		jobListe = new int[jobs.length];
		// add Job to jobListe
		int count = 0;
		jobListe[count] = jobs[0].nummer(); //DummyJob wird hinzugefügt
		count++;
		ArrayList<Integer> nachfolgerAkt = jobs[0].nachfolger; //job 0 nachfolger

		for (int i = 0; i < nachfolgerAkt.size(); i++) { //job 0 nachfolger werden zu planbare Jobs hinzugefügt
			eligibleJobs.add(Job.getJob(jobs, nachfolgerAkt.get(i)));
		}

		while (count != jobs.length) {
			//Anwendung KOZ Regel auf Nachfolger
			Job min = eligibleJobs.get(0);
			int minDauer = eligibleJobs.get(0).dauer(); //von dem 1.Nachfolger dauer = mindauer
			for (int i = 0; i < eligibleJobs.size(); i++) { //planbar/eligible wird nach kleinerer Dauer durchsucht
				if (eligibleJobs.get(i).dauer < minDauer) {
					minDauer = eligibleJobs.get(i).dauer;
					min = eligibleJobs.get(i);
				}
			}

			jobListe[count] = min.nummer; // Der Nachfolger mit der kleinsten Dauer wird zur Jobliste hinzugefügt
			count++; //nächster JOb wird gesucht
			eligibleJobs.remove(min); //aus Planbar wird dieser Job entfernt
			nachfolgerAkt = min.nachfolger(); // von neuem Job/min alle Nachfolger in Arraylist als nummern

			for (int i = 0; i < nachfolgerAkt.size(); i++) { //Liste der nachfolger wird durchlaufen
				Job aktuellerNachfolgerJob = Job.getJob(jobs, nachfolgerAkt.get(i)); //nachfolger neuer Job (nicht in JObliste)
				ArrayList<Integer> vorgaengerAkt = aktuellerNachfolgerJob.vorgaenger;  //vorgänger dieses nachfolgers wird in Liste gespreichert
				boolean alleVorgaenger = true;
				for (int j = 0; j < vorgaengerAkt.size(); j++) { //Vorgänger wird durchlaufen
					boolean found = false;
					for (int k = 0; k < jobListe.length; k++) { //überprüft ob alle Vorgänger gefunden wurden
						if (jobListe[k] == vorgaengerAkt.get(j)) {
							found = true;
						}
					}
					if (!found) { //wenn nicht alle Vorgänger gefunden wurden springt er in die for und sucht den nächsten Nachfolger (passiert bis ein passender Nachfolger gefunden wurde)
						alleVorgaenger = false;
						break;
					}

				}
				if (alleVorgaenger) { //wenn alle Vorgänger gefunden wurden wird der nächste nachfolgerJob zu Planbaren Jobs hinzugefügt
					eligibleJobs.add(Job.getJob(jobs, nachfolgerAkt.get(i)));
				}
			}
		}
	}
	// Berechnet mit Jobliste fuer jeden Job die zugehoerige Startzeit
	public void decodeJobList(Job[] jobs, Resource[] res){
		//calculate the starting times of the jobs in the order of jobListe


		schedule = new int[jobListe.length];

		//calculate the maximum possible makespan "maxDauer" of the project
		int maxDuration = 0;//alt shift R
		for(int i = 0; i < jobs.length; i++){
			maxDuration += jobs[i].dauer;
		}

		int[][] resourcenTableau = new int[res.length][maxDuration]; //??

		for(int i = 0; i < resourcenTableau.length; i++){
			for(int j = 0; j < resourcenTableau[i].length; j++){
				resourcenTableau[i][j] = res[i].maxVerfuegbarkeit();
			}
		}

		for(int i = 0; i < jobListe.length; i++){

			int nr = jobListe[i];

			Job j = Job.getJob(jobs, nr);

			int p1 = earliestPossibleStarttime(j, jobs);
			int p2 = starttime(j, p1, resourcenTableau);
			actualizeResources(j, resourcenTableau, p2);

			schedule[i] = p2;
		}

	}



	public int earliestPossibleStarttime(Job j, Job[] jobs) {
		// berechneFruehesteStartzeit
		// Der Zeitpunkt, nachdem alle Vorgänger abgearbeitet sind.
		ArrayList<Integer> vorgaenger = j.vorgaenger;
		// Startzeit des spätesten Vorgängers + dauer!
		int fruehestenStart = 0;
		for (int i = 0; i < vorgaenger.size(); i++) {
			Job vorg = Job.getJob(jobs, vorgaenger.get(i));
			for (int k = 0; k < jobListe.length; k++) {
				if (jobListe[k] == vorg.nummer) {
					if ((schedule[k] + vorg.dauer()) > fruehestenStart)
						fruehestenStart = schedule[k] + vorg.dauer();
				}
			}
		}
		return fruehestenStart;
	}

	public void actualizeResources(Job j, int[][] resTab, int start){
		
		int[] verwendeteResourcen = j.verwendeteResourcen;
		for(int k = 0; k < resTab.length; k++){
			for(int i = start; i < (start + j.dauer); i++){
				resTab[k][i] -= verwendeteResourcen[k];
			}
		}
	}


	private int starttime(Job j, int p1, int[][] resourcenTableau) {
		// startzeit in abhaengigkeit zu den ressourcen
		return p1;
	}
	public void ausgabe(String directory, String ausgabeName){
		try{	
			PrintWriter pu = new PrintWriter(new FileWriter(ausgabeName + ".sol"));
			for(int i=0;i<jobListe.length;i++){
				pu.println(jobListe[i] + " " + schedule[i]);
			}
			pu.close();	
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}	

}
