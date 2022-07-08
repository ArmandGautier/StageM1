package Test_model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import GSG.GSG_SNF;
import Model_cplex.Daskalakis_model;
import Model_cplex.Gilpin_model;
import Model_cplex.Trabelsi_model;
import Model_cplex.Trabelsi_without_objective;


public class Test_model {

	public void test_on_GSG_SNF(ArrayList<GSG_SNF> gsgs, String filename, int nb_joueur, int nb_action) {
		  try {

			   File file = new File(filename);
			   boolean first = false;
			   String content = "";

			   // créer le fichier s'il n'existe pas
			   if (!file.exists()) {
			    file.createNewFile();
			    first = true;
			   }

			   FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			   BufferedWriter bw = new BufferedWriter(fw);
			   
			   if (first) {
				   //content = "Nb_joueur, Nb_actions, nb_sol_t1, nb_sol_t2\n";
				   content = "Nb_joueur, Nb_actions, daska_s, trabel_s, gilpin, daska_s&c, trabel_s&c, gilpin_s&c, nb_equilibre\n";
				   bw.write(content);
			   }
			   
			   double time1 = 0;
			   double total_time1 = 0;
			   double time2 = 0;
			   double total_time2 = 0;
			   double time2_bis = 0;
			   double total_time2_bis = 0;
			   double nb_jeux = gsgs.size();
			   double nb_equilibrium = 0;
			   //double nb_sol1 = 0;
			   //double nb_sol2 = 0;
			   
			   int i=0;
			   for (GSG_SNF gsg : gsgs) {
				   i++;
				   //System.out.println("Jeux" + i);
				   ArrayList<int[]> act = gsg.getProfiles();
				   ArrayList<float[]> uti = gsg.getUtilities();
				   Daskalakis_model m1 = new Daskalakis_model(act,uti);
				   Gilpin_model m2 = new Gilpin_model(act,uti);
				   Trabelsi_without_objective m2_bis = new Trabelsi_without_objective(act,uti);
					 
				   m1.construct_model();
				   time1 += m1.get_solving_time();
				   total_time1 += m1.get_construction_and_solving_time();
					 
				   m2.construct_model();
				   time2 += m2.get_solving_time();
				   total_time2 += m2.get_construction_and_solving_time();
				   //nb_sol1 += m2.getNb_sol();
					 
				   m2_bis.construct_model();
				   time2_bis += m2_bis.get_solving_time();
				   total_time2_bis += m2_bis.get_construction_and_solving_time();
				   //nb_sol2 += m2_bis.getNb_sol();
				   
				   if ( m1.equilibrium() ) {
					   if ( m2_bis.equilibrium() && m2.equilibrium() ) {
					   		nb_equilibrium++;
					   }
					   else {
						   System.out.println("Soucis1");
					   }
				   }
				
			  }
			   
			  content = nb_joueur+", "+nb_action+", "+time1/nb_jeux+", "+time2_bis/nb_jeux+" ,"+time2/nb_jeux+" ,"+ total_time1/nb_jeux+", "+total_time2_bis/nb_jeux+ " ,"+total_time2/nb_jeux+ " ,"+nb_equilibrium +"\n";
			  bw.write(content);
			  bw.close();

			  } catch (IOException e) {
			   e.printStackTrace();
			  }
	}
}
