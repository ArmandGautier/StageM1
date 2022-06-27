/**
 * 
 */
package Main;

import java.util.ArrayList;

import GSG.GSG;
import GSG.GSG_SNF;
import GSG.GSG_hypergraphique;
import GSG.Generateur;
import Model_cplex.Gilpin_model;
import Model_cplex.Trabelsi_model;
import Model_cplex.Trabelsi_without_objective;
import GSG.GSG_MF;
import GSG.GSG_MFWT;
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
		GSG_MFWT gsg = new GSG_MFWT(nb_attacker,nb_defender,uti_att,uti_def,possible_actions,herd,fine_or_bribe,nb_location,focal_element,m,method,alpha,gps);
		gsg.calcul_val();
		gsg.afficher_jeux();
	}
}
