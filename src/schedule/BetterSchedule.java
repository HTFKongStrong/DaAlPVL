package schedule;

import RCPSP.Job;
import RCPSP.Resource;

import java.util.ArrayList;
import java.util.Collections;

public class BetterSchedule {
    ArrayList<Integer> jobListe = new ArrayList<>();
    ArrayList<Integer> jobsGeplant = new ArrayList<>();
    ArrayList<Integer> jobsPlanbar = new ArrayList<>(); //alle Jobs die noch nicht in der JobListe sind

    public void initializeJobListe(Job[] jobs) {
        //Erste DummyJob1 wird zur JobListe hinzugefügt & schon zu geplanter Jobs hinzugefügt
        jobListe.add(jobs[0].nummer());
        jobsGeplant.add(jobs[0].nummer());

        //alle Jobs zu Planbar hinzufügen
        for(Job job: jobs){
            jobsPlanbar.add(job.nummer());
        }
        //hinzufügen der ersten JObs, die keine Vorgänger haben
        for (Integer nachfolger: jobs[0].nachfolger()) {
            jobsPlanbar.add(nachfolger);
        }
        //hinzufügen weiterer Jobs: solange bis planbar leer ist
        while (!jobsPlanbar.isEmpty() || jobsPlanbar.size() !=0 ){

            //Auswahl eines Jobs aus Planbar
            int nächsteJobNR = jobGetKOZDauer(jobs, jobsPlanbar);

            jobsPlanbar.remove(nächsteJobNR);
            jobsGeplant.add(nächsteJobNR);
            jobListe.add(nächsteJobNR);

            // für jeden Nachfolger checken ob alle Vorgänger schon geplant sind
            // wenn ja in Planbar hinzufügen
            for (Integer nachfolger : Job.getJob(jobs, nächsteJobNR).nachfolger()) {
                boolean inGeplant = false;
                for (Integer vorgänger: Job.getJob(jobs, nächsteJobNR).vorgaenger()) {
                      if (jobsGeplant.contains(vorgänger)){
                          inGeplant = true;
                      }else{
                          inGeplant = false;
                      }
                }
                if(inGeplant){
                    jobsPlanbar.add(nachfolger);
                }
            }
        }
    }
//    //get Schedule
//    public void decodeJobList(Job[] jobs, Resource[] res){
//        for(int i = 0; i < jobListe.length; i++){
//
//            int nr = jobListe[i];
//
//            Job j = Job.getJob(jobs, nr);
//
//            int p1 = earliestPossibleStarttime(j, jobs);
//            int p2 = starttime(j, p1, resourcenTableau);
//            actualizeResources(j, resourcenTableau, p2);
//
//            schedule[i] = p2;
//        }
//    }
//    public int earliestPossibleStarttime(Job j, Job[] jobs){
//        return;
//    }
//
//    public int starttime(Job j, int earliestStarttime, Resource[] res){
//        return;
//    }
//
//    public void actualizeResources(Job j, Resource[] res, int starttime){
//
//    }

    public int jobGetKOZDauer(Job[] jobs, ArrayList<Integer> jobsPlanbar){
        //Berechnung welcher Job die kürzeste Dauer hat
        int kozJobDauer = 0;
        int kozNr = 0;
        for(int jobNR: jobsPlanbar){
            int dauer = Job.getJob(jobs, jobNR).dauer();
            if (dauer > kozJobDauer){ // nur Job zurückgeben der die kürzeste Bearbeitungszeit hat
                kozJobDauer = dauer;
                kozNr = Job.getJob(jobs, jobNR).nummer();
            }
        }
        return kozNr;
    }

}
