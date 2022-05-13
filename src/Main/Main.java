/**
 * 
 */
package Main;

import java.util.ArrayList;

import GSG.GSG;
import GSG.GSG_bimatriciel;
import Model_cplex.Model1;
import Model_cplex.Model2;

/**
 * @author agautier
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// To change the number of possible actions for players (list of int in order without exception or repetition)
		ArrayList<Integer> action_j0 = new ArrayList<Integer>();
		action_j0.add(0);
		action_j0.add(1);
		ArrayList<Integer> action_j1 = new ArrayList<Integer>();
		action_j1.add(0);
		action_j1.add(2);
		ArrayList<Integer> action_j2 = new ArrayList<Integer>();
		action_j2.add(0);
		action_j2.add(1);
		action_j2.add(2);
		
		ArrayList<ArrayList<Integer>> actions = new ArrayList<ArrayList<Integer>>();
		actions.add(action_j0);
		actions.add(action_j1);
		actions.add(action_j2);
		
		// To change the gain of the attacker, one gain by action
		float[] val_ressource = {1,1,4};
		
		// To use a specific calcul of utility, can be "gain or zero" or "gain less number of defender"
		String utilite_calcule1 = "gain or zero";
		String utilite_calcule2 = "gain less number of defender";
		
		GSG gsg = new GSG(2,1,val_ressource,utilite_calcule2,actions);
		gsg.calcul_val();
		gsg.afficher_jeux();
		
		Model2 m = new Model2(gsg.getAct(),gsg.getUti());
		m.construct_model();
		m.print_results();
		
	}
}
