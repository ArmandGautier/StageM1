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
		
		/*Generateur g = new Generateur();
		Test_model t = new Test_model();
		for (int i=2; i<=20; i++) {
			ArrayList<GSG_SNF> gsgs = g.Generate_GSG_SNF(2,i,500,"Team-poach and bribe", "captor");
			t.test_on_GSG_SNF(gsgs, "test_all.csv", 2, i);
		}*/
		/*Generateur g = new Generateur();
		ArrayList<GSG_SNF> gsgs = g.Generate_GSG_SNF(2,4,10,"Team-poach and bribe", "captor");
		for (GSG_SNF gsg : gsgs) {
			ArrayList<int[]> act = gsg.getProfiles();
			ArrayList<float[]> uti = gsg.getUtilities();
			Trabelsi_model m1 = new Trabelsi_model(act,uti);
			Gilpin_model m2 = new Gilpin_model(act,uti);
			m1.construct_model();
			m2.construct_model();
		}*/

		float l = 1;
		int[] t = {0,1};
		GSG_SNF gsg = new GSG_SNF(2, 2, "Team-poach and bribe", "captor", l, t, 2);
		gsg.calcul_val();
		gsg.afficher_jeux();
		gsg.writeInFile("Test1.txt");
	}
}
