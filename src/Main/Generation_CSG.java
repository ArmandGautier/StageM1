/**
 * 
 */
package Main;

import GSG.GSG;
import GSG.GSG_bimatriciel;

/**
 * @author agautier
 *
 */
public class Generation_CSG {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// To change the number of possible actions for players (list of int in order without exception or repetition)
		int[] actions = {0,1};
		
		// To change the gain of the attacker, one gain by action
		float[] gain_braconnier = {5,2};
		
		// To use a specific calcul of utility, can be "gain or zero" or "gain less number of defender"
		String utilite_calcule1 = "gain or zero";
		String utilite_calcule2 = "gain less number of defender";
		
		GSG gsg = new GSG(1,1,actions,gain_braconnier,utilite_calcule1);
		gsg.calcul_val();
		gsg.afficher_jeux();
		
	}
}
