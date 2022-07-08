/**
 * 
 */
package Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import GSG.Bel_GSG;
import GSG.Bel_GSG_Conditionned_Transform;
import GSG.Bel_GSG_Direct_Transform;
import GSG.Bel_GSG_SNF;
import GSG.Bel_GSG_TBM_Transform;
import GSG.GSG;
import GSG.GSG_SNF;
import GSG.GSG_hypergraphique;
import GSG.Generateur;
import GSG.Node;
import GSG.Universe;
import Model_cplex.Gilpin_model;
import Model_cplex.Trabelsi_for_hypergraphical_games;
import Model_cplex.Trabelsi_model;
import Model_cplex.Trabelsi_without_objective;
import GSG.GSG_MF;
import Test_model.Test_model;
import Verif.Verif;

/**
 * @author agautier
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) {
		
		/**int nb_attacker = 1 ;
		int nb_defender = 1;
		int[] seeFunction = {0};
		String uti_att = "Team-poach and bribe";
		String uti_def = "captor";
		ArrayList<ArrayList<Integer>> possible_actions = new ArrayList<ArrayList<Integer>>();
		int nb_location = 3;
		for (int j=0; j<nb_attacker+nb_defender; j++) {
			ArrayList<Integer> temp = new ArrayList<Integer>();
			for (int l=0; l<nb_location; l++) {
				temp.add(l);
			}
			possible_actions.add(temp);
		}
		int[] herd = {2};
		int fine_or_bribe = 2;
		ArrayList<int[][]> focal_element = new ArrayList<int[][]>();
		int[][] f = {{0}};
		int[][] f1 = {{1},{2}};
		focal_element.add(f);
		focal_element.add(f1);
		float[] m = {(float) 0.5,(float) 0.5};
		String method = "TBEU";
		float[] alpha = {0};
		int[] gps = {0};
		Bel_GSG_Direct_Transform gsg = new Bel_GSG_Direct_Transform(nb_attacker,nb_defender,uti_att,uti_def,possible_actions,herd,fine_or_bribe,nb_location,focal_element,m,method,alpha,seeFunction,gps);
		gsg.calcul_val();
		Bel_GSG_TBM_Transform gsg3 = new Bel_GSG_TBM_Transform(nb_attacker,nb_defender,uti_att,uti_def,possible_actions,herd,fine_or_bribe,nb_location,focal_element,m,alpha,seeFunction,gps);
		gsg3.calcul_val();
		Bel_GSG_Conditionned_Transform gsg4 = new Bel_GSG_Conditionned_Transform(nb_attacker,nb_defender,uti_att,uti_def,possible_actions,herd,fine_or_bribe,nb_location,focal_element,m,method,alpha,seeFunction,gps);
		gsg4.calcul_val();
		//gsg.writeInFile("Hyper.txt");
		Bel_GSG_SNF gsg2 = new Bel_GSG_SNF(nb_attacker,nb_defender,uti_att,uti_def,possible_actions,herd,fine_or_bribe,nb_location,focal_element,m,method,alpha,seeFunction,gps);
		gsg2.calcul_val();
		//gsg2.writeInFile("SNF.txt");
		ArrayList<int[]> act = gsg2.getProfiles();
		ArrayList<float[]> uti = gsg2.getUtilities();
		Trabelsi_model trabel = new Trabelsi_model(act,uti);
		Map<Integer, ArrayList<int[]>> profils = gsg.getProfils();
		Map<Integer, ArrayList<float[]>> utilites = gsg.getUtilites();
		Map<Integer, ArrayList<int[]>> profils3 = gsg3.getProfils();
		Map<Integer, ArrayList<float[]>> utilites3 = gsg3.getUtilites();
		Map<Integer, ArrayList<int[]>> profils4 = gsg4.getProfils();
		Map<Integer, ArrayList<float[]>> utilites4 = gsg4.getUtilites();
		ArrayList<ArrayList<Integer>> player_by_game = gsg.getPlayer_by_game();
		ArrayList<ArrayList<Integer>> player_by_game3 = gsg3.getPlayer_by_game();
		ArrayList<ArrayList<Integer>> player_by_game4 = gsg4.getPlayer_by_game();
		Trabelsi_for_hypergraphical_games trabel2 = new Trabelsi_for_hypergraphical_games(profils,utilites,player_by_game);
		Trabelsi_for_hypergraphical_games trabel3 = new Trabelsi_for_hypergraphical_games(profils3,utilites3,player_by_game3);
		Trabelsi_for_hypergraphical_games trabel4 = new Trabelsi_for_hypergraphical_games(profils4,utilites4,player_by_game4);
		trabel.construct_model();
		trabel2.construct_model();
		trabel3.construct_model();
		trabel4.construct_model();
		System.out.println(trabel.equilibrium());
		System.out.println(trabel2.equilibrium());
		System.out.println(trabel3.equilibrium());
		System.out.println(trabel4.equilibrium());
		Verif v = new Verif();
		System.out.println(v.verifAsSameBelGsg(gsg2, gsg));
		System.out.println(v.verifAsSameBelGsg(gsg2, gsg3));
		System.out.println(v.verifAsSameBelGsg(gsg2, gsg4));
		*/
		Generateur g = new Generateur();
		ArrayList<Universe> universes = g.generateUniverseForBel_Gsg(3, 2, 2, 1, 10);
		String uti_att = "Team-poach and bribe";
		String uti_def = "captor";
		String method = "CEU";
		ArrayList<Bel_GSG_Direct_Transform> dTransfo = g.generate_Bel_GSG_Direct_Transform(universes, uti_att, uti_def, method);
		ArrayList<Bel_GSG_Conditionned_Transform> cTransfo = g.Bel_GSG_Conditionned_Transform(universes, uti_att, uti_def, method);
		ArrayList<Bel_GSG_TBM_Transform> tbmTransfo = g.Bel_GSG_TBM_Transform(universes, uti_att, uti_def);
		//ArrayList<Bel_GSG_SNF> gsg = g.generate_Bel_GSG_SNF(universes, uti_att, uti_def, method);
		try {
		    File file = new File("comparTRANSFO_CEU.txt");
		    String content = "";
	
		    // créer le fichier s'il n'existe pas
		    if (!file.exists()) {
		     file.createNewFile();
		    }
	
		    FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		    BufferedWriter bw = new BufferedWriter(fw);
		    //content = "Nb_lieu, Nb_herd, Nb_attacker, Nb_Defender, Nb_joueur_moy, tps_dtransfo, tps_ctransfo, nb_equi\n";
		    //bw.write(content);
			double time1 = 0;
			double time2 = 0;
			double time3 = 0;
			double time4 = 0;
			int size = 0;
			float total_size = 0;
			int nb_equi = 0;
		    
			for (int i=0; i<10; i++) {
				   
				System.out.println("JEUX "+i);
				//ArrayList<int[]> profils = gsg.get(i).getProfiles();
				//ArrayList<float[]> utilites = gsg.get(i).getUtilities();
				Map<Integer, ArrayList<int[]>> profils2 = dTransfo.get(i).getProfils();
				Map<Integer, ArrayList<float[]>> utilites2 = dTransfo.get(i).getUtilites();
				Map<Integer, ArrayList<int[]>> profils3 = cTransfo.get(i).getProfils();
				Map<Integer, ArrayList<float[]>> utilites3 = cTransfo.get(i).getUtilites();
				Map<Integer, ArrayList<int[]>> profils4 = tbmTransfo.get(i).getProfils();
				Map<Integer, ArrayList<float[]>> utilites4 = tbmTransfo.get(i).getUtilites();
				ArrayList<ArrayList<Integer>> player_by_game2 = dTransfo.get(i).getPlayer_by_game();
				ArrayList<ArrayList<Integer>> player_by_game3 = cTransfo.get(i).getPlayer_by_game();
				ArrayList<ArrayList<Integer>> player_by_game4 = tbmTransfo.get(i).getPlayer_by_game();
				//Trabelsi_model trabel = new Trabelsi_model(profils,utilites);
				size = dTransfo.get(i).getNodes().size();
				total_size += size;
				boolean stop = false;
				for (ArrayList<Integer> play : player_by_game2) {
					System.out.println(play.size());
					if ( play.size() > 8) {
						stop = true;
						break;
					}
				}
				if (stop) {
					break;
				}
				System.out.println(size);
				Trabelsi_for_hypergraphical_games trabel2 = new Trabelsi_for_hypergraphical_games(profils2,utilites2,player_by_game2,size);
				Trabelsi_for_hypergraphical_games trabel3 = new Trabelsi_for_hypergraphical_games(profils3,utilites3,player_by_game3,size);
				Trabelsi_for_hypergraphical_games trabel4 = new Trabelsi_for_hypergraphical_games(profils4,utilites4,player_by_game4,size);
				//trabel.construct_model();
				trabel2.construct_model();
				trabel3.construct_model();
				trabel4.construct_model();
				//time1 += trabel.get_solving_time();
				time2 = trabel2.get_solving_time();
				time3 = trabel3.get_solving_time();
				time4 += trabel4.get_solving_time();
				boolean equi = trabel2.equilibrium();
				content = "3, 2, 2, 1, "+size+", "+time2+", "+time3+", "+equi+"\n";
				bw.write(content);
				//System.out.println(trabel.equilibrium());
				System.out.println(trabel2.equilibrium());
				System.out.println(trabel3.equilibrium());
				//System.out.println(trabel4.equilibrium());
			}
			bw.close();
		}
		catch (IOException e) {
			   e.printStackTrace();
			  }
		
	}
}
