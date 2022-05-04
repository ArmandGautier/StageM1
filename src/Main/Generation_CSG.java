/**
 * 
 */
package Main;

import java.util.ArrayList;
import java.util.Arrays;

import GSG.GSG;

/**
 * @author agautier
 *
 */
public class Generation_CSG {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// To change the number of possible actions for players
		int[] actions = {1,2};
		
		// To change the gain of the attacker
		int gain_braconnier = 2;
		
		// To use a specific calcul of utility, can be "gain or zero" or "gain less number of defender"
		String utilite_calcule1 = "gain or zero";
		String utilite_calcule2 = "gain less number of defender";
		GSG gsg = new GSG(2,2,actions,gain_braconnier,utilite_calcule1);
		ArrayList res = gsg.getMatrixes_of_game();
		System.out.println(res);
	}
}
