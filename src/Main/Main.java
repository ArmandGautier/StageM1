/**
 * 
 */
package Main;

import java.util.ArrayList;

import GSG.GSG;
import GSG.GSG_SNF;
import GSG.GSG_hypergraphique;
import GSG.Generateur;
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
		
		Generateur g = new Generateur();
		ArrayList<GSG_SNF> gsgs = g.Generate_GSG_SNF(3,4,4,10,"Poach and bribe", "Defend the location");
		for (GSG_SNF gsg : gsgs) {
			Test_model t = new Test_model();
			t.test_on_GSG_SNF(gsg);
		}
	}
}
