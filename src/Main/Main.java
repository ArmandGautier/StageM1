/**
 * 
 */
package Main;

import java.util.ArrayList;

import GSG.GSG;
import GSG.GSG_SNF;
import GSG.GSG_hypergraphique;
import GSG.GSG_MF;
import Model_cplex.Model1;
import Model_cplex.Model2;
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
		
		Test_model t = new Test_model();
		t.test_on_GSG_SNF(2,2,2,1000, "Poach or hide", "Defend the location", "test1.csv");
		t.test_on_GSG_SNF(2,2,3,1000, "Poach or hide", "Defend the location", "test2.csv");	
	}
}
