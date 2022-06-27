/**
 * 
 */
package GSG;

import java.util.ArrayList;

/**
 * @author agautier
 *
 */
public abstract class Bel_GSG{
	
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
	/**
	 * a list of all the possible world
	 */
	ArrayList<int[]> possible_omega = new ArrayList<int[]>();
	/**
	 * represents all players of the game
	 */
	ArrayList<Node> nodes = new ArrayList<Node>();
	/**
	 *  a list of focal element, focal_element.get(i) give the focal element who take the value val_m[i]
	 */
	ArrayList<int[][]> focal_element = new ArrayList<int[][]>();
	/**
	 * a list of float to represent a mass function
	 */
	float[] mass_function;
	/**
	 * The method use to compute the XEU, can be JEU, CEU or TBEU
	 */
	String method;
	/**
	 * Value of alpha used in the method JEU
	 */
	float[] alpha;
	/**
	 * Value of herds
	 */
	int[] herd;
	/**
	 * number of location in the game
	 */
	int nb_location;
	/**
	 * number of herds who are chipped by defender
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
	public Bel_GSG(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility,
			ArrayList<ArrayList<Integer>> possible_actions,int[] herd, int fine_or_bribe,int nb_location, ArrayList<int[][]> focal_element,
			float[] mass_function, String method, float[] alpha, int[] gps) {
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
	public Bel_GSG(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility,
			ArrayList<ArrayList<Integer>> possible_actions,int[] herd, int fine_or_bribe,int nb_location, ArrayList<int[][]> focal_element,
			float[] mass_function, String method, int[] gps) {
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
		this.gps = gps;
	}
	
	
	/**
	 * construct a gsg for each possible word
	 */
	protected void create_snf_gsg() {
		for (int[][] element : this.focal_element) {
			for (int[] omega : element) {
				float[] lambda = new float[nb_location];
				for (int i=0; i<omega.length; i++) {
					lambda[omega[i]] += herd[i];
				}
				GSG_SNF gsg = new GSG_SNF(this.nb_attacker, this.nb_defender, this.attacker_utility, this.defender_utility, lambda, this.possible_actions, this.fine_or_bribe);
				gsg.calcul_val();
				this.gsg_snf.add(gsg);
				this.possible_omega.add(omega);
			}
		}
	}
	
	/**
	 * create all players, a player is a two-uplets (index_player,type_of_player)
	 */
	protected void create_noeuds() {
		for (int[] omega : this.possible_omega) {
			for (int i=0; i<this.nb_player; i++) {
				Node n = new Node(i,omegaToTypes(omega,i));
				if (!containsNode(n)) {
					this.nodes.add(n);
				}
			}
		}
	}
	
	/**
	 * @param n
	 * @return true if the node n is already in the list of nodes, false otherwise
	 */
	private boolean containsNode(Node n) {
		for (Node node : this.nodes) {
			if (node.getIndexPlayer() == n.getIndexPlayer() && node.getType() == n.getType()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param omega
	 * @param ind_player
	 * @return the type of player "ind_player" depending the world omega
	 */
	protected String omegaToTypes(int[] omega, int ind_player) {
		String res = "";
		if (ind_player < this.nb_attacker) {
			int herd_number = 0;
			for (int o : omega) {
				if ( o == see(ind_player)) {
					res += "T"+herd_number;
				}
				herd_number ++;
			}
		}
		else {
			for (int herd : gps) {
				res += "L"+omega[herd];
			}
		}
		if ( res == "" ) {
			return "ensemble vide";
		}
		else {
			return res;
		}
	}
	
	/**
	 * @param ind_player
	 * @return herds that player "ind_player" can see
	 */
	
	protected int see(int ind_player) {
		return ind_player; // à améliorer
	}
	
	/**
	 * @param player
	 * @return
	 */
	protected float computeK(Node player ) {
		float k = 0;
		int i = 0;
		for (int[][] elt_focal : this.focal_element) {
			if (typeIsTrue(player,elt_focal)) {
				k+=this.mass_function[i];
			}
			i++;
		}
		return 1/k;
	}
	
	/**
	 * @param node
	 * @param focal_elt
	 * @return true if there is unless one element in "focal_elt" in which the type of the node exist
	 */
	protected boolean typeIsTrue(Node n, int[][] focal_elt) {
		for (int[] omega : focal_elt) {
			if ( this.omegaToTypes(omega, n.getIndexPlayer()).equals(n.getType()) ) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param omega
	 * @return the index of the GSG on SNF corresponding to the state of world omega
	 */
	protected int getIndexOfGSG(int[] omega) {
		int indice = 0;
		for (int[] o : this.possible_omega) {
			if (same(omega,o)) {
				return indice;
			}
			indice++;
		}
		return -1;
	}
	
	/**
	 * @param profile
	 * @param gsg
	 * @return the index of the profile "profile" in the GSG in SNF "gsg"
	 */
	protected int getIndexOfProfile(int[] profile, GSG_SNF gsg) {
		int indice = 0;
		for (int[] p : gsg.getProfiles()) {
			if (same(profile,p)) {
				return indice;
			}
			indice++;
		}
		return -1;
	}
	
	/**
	 * @param omega1
	 * @param omega2
	 * @return true if the two arrays are the same, false otherwise
	 */
	private boolean same(int[] omega1, int[] omega2) {
		if (omega1.length != omega2.length) {
			return false;
		}
		for (int i=0; i<omega1.length; i++) {
			if (omega1[i] != omega2[i]) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Compute value of utility and profiles
	 */
	public abstract void calcul_val();
	/**
	 * Print informations to define the game
	 */
	public abstract void writeInFile(String filename);
}
