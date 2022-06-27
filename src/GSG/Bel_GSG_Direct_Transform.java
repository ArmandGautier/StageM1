package GSG;

import java.util.ArrayList;

public class Bel_GSG_Direct_Transform extends Bel_GSG {
	
	/**
	 * List of local game who are the hypergraphical representation
	 */
	ArrayList<Local_Game> hypergraphical_representation = new ArrayList<Local_Game>();
	
	public Bel_GSG_Direct_Transform(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility,
			ArrayList<ArrayList<Integer>> possible_actions, int[] herd, int fine_or_bribe, int nb_location,
			ArrayList<int[][]> focal_element, float[] mass_function, String method, int[] gps) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, possible_actions, herd, fine_or_bribe, nb_location,
				focal_element, mass_function, method, gps);
	}
	
	public Bel_GSG_Direct_Transform(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility,
			ArrayList<ArrayList<Integer>> possible_actions, int[] herd, int fine_or_bribe, int nb_location,
			ArrayList<int[][]> focal_element, float[] mass_function, String method, float[] alpha, int[] gps) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, possible_actions, herd, fine_or_bribe, nb_location,
				focal_element, mass_function, method, alpha, gps);
	}

	private void create_hypergraphical_gsg() {
		
		create_snf_gsg();
		create_noeuds();
		
		int i=0;
		for (int[][] focal_elt : this.focal_element) {
			Local_Game l = new Local_Game(this,focal_elt,i);
			l.calcul_val();
			this.hypergraphical_representation.add(l);
			i++;
		}
	}
	
	/**
	 * @return the hypergraphical_representation
	 */
	public ArrayList<Local_Game> getHypergraphical_representation() {
		return hypergraphical_representation;
	}

	public void calcul_val() {
		create_hypergraphical_gsg();
	}
	
	public void writeInFile(String filename) {
		int i=0;
		for (Local_Game game : this.hypergraphical_representation) {
			String name = "Jeux"+i+filename;
			i++;
			game.writeInFile(name);
		}
	}

}
