/**
 * 
 */
package Main;

import java.util.ArrayList;
import java.util.Map;

import GSG.Bel_GSG;
import GSG.Bel_GSG_Direct_Transform;
import GSG.Bel_GSG_SNF;
import GSG.GSG;
import GSG.GSG_SNF;
import GSG.GSG_hypergraphique;
import GSG.Generateur;
import Model_cplex.Gilpin_model;
import Model_cplex.Trabelsi_for_hypergraphical_games;
import Model_cplex.Trabelsi_model;
import Model_cplex.Trabelsi_without_objective;
import GSG.GSG_MF;
import Test_model.Test_model;

/**
 * @author agautier
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int nb_attacker = 1 ;
		int nb_defender = 1;
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
		Bel_GSG_Direct_Transform gsg = new Bel_GSG_Direct_Transform(nb_attacker,nb_defender,uti_att,uti_def,possible_actions,herd,fine_or_bribe,nb_location,focal_element,m,method,alpha,gps);
		gsg.calcul_val();
		//gsg.writeInFile("Hyper.txt");
		Bel_GSG_SNF gsg2 = new Bel_GSG_SNF(nb_attacker,nb_defender,uti_att,uti_def,possible_actions,herd,fine_or_bribe,nb_location,focal_element,m,method,alpha,gps);
		gsg2.calcul_val();
		//gsg2.writeInFile("SNF.txt");
		ArrayList<int[]> act = gsg2.getProfiles();
		ArrayList<float[]> uti = gsg2.getUtilities();
		Trabelsi_model trabel = new Trabelsi_model(act,uti);
		Map<Integer, ArrayList<int[]>> profils = gsg.getProfils();
		Map<Integer, ArrayList<float[]>> utilites = gsg.getUtilites();
		ArrayList<ArrayList<Integer>> player_by_game = gsg.getPlayer_by_game();
		Trabelsi_for_hypergraphical_games trabel2 = new Trabelsi_for_hypergraphical_games(profils,utilites,player_by_game);
		trabel.construct_model();
		trabel2.construct_model();
		System.out.println(trabel.equilibrium());
		System.out.println(trabel2.equilibrium());
	}
}
