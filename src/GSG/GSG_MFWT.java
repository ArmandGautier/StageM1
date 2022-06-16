/**
 * 
 */
package GSG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author agautier
 *
 */
public class GSG_MFWT{
	
	/**
	 * N players, K attackers and L defenders, the first K players are the attackers
	 */
	int nb_attacker;
	int nb_defender;
	int nb_player;
	/**
	 * can be TO DO
	 * define the method use to compute the utility of attackers
	 */
	String attacker_utility;
	/**
	 * can be TO DO
	 * define the method use to compute the utility of defenders
	 */
	String defender_utility;
	/**
	 * ressource_accessible.get(i) is the list of possible action for player i
	 */
	ArrayList<ArrayList<Integer>> possible_actions = new ArrayList<ArrayList<Integer>>();
	/**
	 * it's a coefficient to play on value of fines and bribes
	 */
	int fine_or_bribe = 1;
	/**
	 * GSG in SNF to represent all possibles worlds
	 */
	ArrayList<GSG_SNF> gsg_snf = new ArrayList<GSG_SNF>();
	ArrayList<int[]> possible_omega = new ArrayList<int[]>();
	/**
	 * represents all nodes of the transformation
	 */
	ArrayList<int[]> nodes = new ArrayList<int[]>();
	/**
	 *  a list of focal element, focal_element.get(i) give the focal element who take the value val_m[i]
	 */
	ArrayList<int[][]> focal_element = new ArrayList<int[][]>();
	/**
	 * a list of float to represent a mass function
	 */
	float[] mass_function;
	/**
	 * The method use to compute the total utility considering all the possible world
	 */
	String method;
	/**
	 * Value of alpha used in the method JEU, can be change
	 */
	double[] alpha;
	/**
	 * Types are informations know by player
	 */
	ArrayList<String[]> types = new ArrayList<String[]>();
	/**
	 * List of GSG_SNF who are the hypergraphical representation
	 */
	ArrayList<Local_Game> hypergraphical_representation = new ArrayList<Local_Game>();
	/**
	 * Value of herds
	 */
	int[] herd;
	/**
	 * number of location in the game
	 */
	int nb_location;
	/**
	 * number of the herd who is tracked by defender
	 */
	int[] gps;

	/**
	 * @param nb_attacker
	 * @param nb_defender
	 * @param attacker_utility
	 * @param defender_utility
	 * @param possible_actions
	 * @param herd
	 * @param fine_or_bribe
	 * @param nb_location
	 * @param focal_element
	 * @param mass_function
	 * @param method
	 * @param alpha
	 * @param gps
	 */
	public GSG_MFWT(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility,
			ArrayList<ArrayList<Integer>> possible_actions,int[] herd, int fine_or_bribe,int nb_location, ArrayList<int[][]> focal_element,
			float[] mass_function, String method, double[] alpha, int[] gps) {
		this.nb_attacker = nb_attacker;
		this.nb_defender = nb_defender;
		this.nb_player = this.nb_attacker + this.nb_defender;
		this.attacker_utility = attacker_utility;
		this.defender_utility = defender_utility;
		this.possible_actions = possible_actions;
		this.herd = herd;
		this.fine_or_bribe = fine_or_bribe;
		this.nb_location = nb_location;
		this.focal_element = focal_element;
		this.mass_function = mass_function;
		this.method = method;
		this.alpha = alpha;
		this.gps = gps;
	}
	/**
	 * construct a gsg for each possible word
	 */
	private void create_snf_gsg() {
		for (int[][] element : this.focal_element) {
			for (int[] omega : element) {
				float[] lambda = new float[nb_location];
				for (int i=0; i<omega.length; i++) {
					lambda[omega[i]] += herd[i];
				}
				GSG_SNF gsg = new GSG_SNF(this.nb_attacker, this.nb_defender, this.attacker_utility, this.defender_utility, lambda, this.possible_actions, this.fine_or_bribe);
				this.gsg_snf.add(gsg);
				this.possible_omega.add(omega);
			}
		}
	}
	
	private void create_noeuds() {
		for (int[] omega : this.possible_omega) {
			for (int i=0; i<this.nb_player; i++) {
				int[] temp_node = new int[2];
				temp_node[0] = i;
				temp_node[1] = omegaToTypes(omega,i);
				this.nodes.add(temp_node);
			}
		}
	}

	public void calcul_val() {
		create_hypergraphical_gsg();
	}
	
	private void create_hypergraphical_gsg() {
		
		create_snf_gsg();
		create_noeuds();
		
		for (int[][] focal_elt : this.focal_element) {
			Local_Game l = new Local_Game(this);
			l.calcul_val(focal_elt,this.nodes);
			this.hypergraphical_representation.add(l);
		}
	
	}

	public int omegaToTypes(int[] omega, int ind_player) {
		String res = "";
		if (ind_player < this.nb_attacker) {
			int herd_number = 0;
			for (int o : omega) {
				if ( o == see(ind_player)) {
					res += "T"+herd_number;
				}
				herd_number ++;
			}
			return res.hashCode();
		}
		else {
			for (int herd : gps) {
				res += "L"+omega[herd];
			}
			return res.hashCode();
		}
	}
	
	private int see(int ind_player) {
		return ind_player;
	}
	
	void afficher_jeux() {

	}
}
