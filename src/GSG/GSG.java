package GSG;

import java.util.ArrayList;

public abstract class GSG {
	
	/**
	 * N players, K attackers and L defenders, the first K players are the attackers
	 */
	int nb_attacker;
	int nb_defender;
	int nb_player;
	/**
	 * can be "Poach or hide" or "Team-poach or hide" or "Poach and bribe"
	 * define the method use to compute the utility of attackers
	 */
	String attacker_utility;
	/**
	 * can be "Defend the location" or "captor" or "Bribemon : Gotta catch'em all!" or "Avoid poaching"
	 * define the method use to compute the utility of defenders
	 */
	String defender_utility;
	/**
	 * lambda[i] define the value corresponding to the action i
	 */
	float lambda[];
	/**
	 * ressource_accessible.get(i) is the list of possible action for player i
	 */
	ArrayList<ArrayList<Integer>> possible_actions = new ArrayList<ArrayList<Integer>>();
	/**
	 * x is a coefficient to play on value of fines and bribes
	 */
	int fine_or_bribe = 1;
	/**
	 * represent the number of possible profile
	 */
	int dimension;
	
	public GSG(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float lambda, int[] actions) {
		this.nb_attacker = nb_attacker;
		this.nb_defender = nb_defender;
		this.nb_player = nb_attacker+nb_defender;
		this.attacker_utility = attacker_utility;
		this.defender_utility = defender_utility;
		
		this.lambda = new float[actions.length];
		for (int i=0; i<this.lambda.length; i++) {
			this.lambda[i] = lambda;
		}
		
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for (int i : actions) {
			temp.add(i);
		}
		for (int i=0; i<this.nb_player; i++) {
			this.possible_actions.add(temp);
		}
	}
	public GSG(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float[] lambda, int[] actions) {
		this.nb_attacker = nb_attacker;
		this.nb_defender = nb_defender;
		this.nb_player = nb_attacker+nb_defender;
		this.attacker_utility = attacker_utility;
		this.defender_utility = defender_utility;
		this.lambda = lambda;
		
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for (int i : actions) {
			temp.add(i);
		}
		for (int i=0; i<this.nb_player; i++) {
			this.possible_actions.add(temp);
		}
	}
	public GSG(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float lambda, ArrayList<ArrayList<Integer>> possible_actions) {
		this.nb_attacker = nb_attacker;
		this.nb_defender = nb_defender;
		this.nb_player = nb_attacker+nb_defender;
		this.attacker_utility = attacker_utility;
		this.defender_utility = defender_utility;
		
		ArrayList<Integer> actions = new ArrayList<Integer>();
		for (ArrayList<Integer> list_actions : possible_actions) {
			for (int action : list_actions) {
				if (!actions.contains(action)) {
					actions.add(action);
				}
			}
		}
		
		this.lambda = new float[actions.size()];
		for (int i=0; i<this.lambda.length; i++) {
			this.lambda[i] = lambda;
		}
		
		this.possible_actions = possible_actions;
	}
	public GSG(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float[] lambda, ArrayList<ArrayList<Integer>> possible_actions) {
		this.nb_attacker = nb_attacker;
		this.nb_defender = nb_defender;
		this.nb_player = nb_attacker+nb_defender;
		this.attacker_utility = attacker_utility;
		this.defender_utility = defender_utility;
		this.lambda = lambda;
		this.possible_actions = possible_actions;
	}
	public GSG(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float lambda, int[] actions, int fine_or_bribe) {
		this.nb_attacker = nb_attacker;
		this.nb_defender = nb_defender;
		this.nb_player = nb_attacker+nb_defender;
		this.attacker_utility = attacker_utility;
		this.defender_utility = defender_utility;
		
		this.lambda = new float[actions.length];
		for (int i=0; i<this.lambda.length; i++) {
			this.lambda[i] = lambda;
		}
		
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for (int i : actions) {
			temp.add(i);
		}
		for (int i=0; i<this.nb_player; i++) {
			this.possible_actions.add(temp);
		}
		
		this.fine_or_bribe = fine_or_bribe;
	}
	public GSG(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float[] lambda, int[] actions, int fine_or_bribe) {
		this.nb_attacker = nb_attacker;
		this.nb_defender = nb_defender;
		this.nb_player = nb_attacker+nb_defender;
		this.attacker_utility = attacker_utility;
		this.defender_utility = defender_utility;
		this.lambda = lambda;
		
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for (int i : actions) {
			temp.add(i);
		}
		for (int i=0; i<this.nb_player; i++) {
			this.possible_actions.add(temp);
		}
		
		this.fine_or_bribe = fine_or_bribe;
	}
	public GSG(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float lambda, ArrayList<ArrayList<Integer>> possible_actions, int fine_or_bribe) {
		this.nb_attacker = nb_attacker;
		this.nb_defender = nb_defender;
		this.nb_player = nb_attacker+nb_defender;
		this.attacker_utility = attacker_utility;
		this.defender_utility = defender_utility;
		
		ArrayList<Integer> actions = new ArrayList<Integer>();
		for (ArrayList<Integer> list_actions : possible_actions) {
			for (int action : list_actions) {
				if (!actions.contains(action)) {
					actions.add(action);
				}
			}
		}
		
		this.lambda = new float[actions.size()];
		for (int i=0; i<this.lambda.length; i++) {
			this.lambda[i] = lambda;
		}
		
		this.possible_actions = possible_actions;
		this.fine_or_bribe = fine_or_bribe;
	}
	public GSG(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float[] lambda, ArrayList<ArrayList<Integer>> possible_actions, int fine_or_bribe) {
		this.nb_attacker = nb_attacker;
		this.nb_defender = nb_defender;
		this.nb_player = nb_attacker+nb_defender;
		this.attacker_utility = attacker_utility;
		this.defender_utility = defender_utility;
		this.lambda = lambda;
		this.possible_actions = possible_actions;
		this.fine_or_bribe = fine_or_bribe;
	}


	/**
	 * Compute value of utility and profiles
	 */
	abstract void calcul_val();
	/**
	 * Print informations to define the game
	 */
	abstract void afficher_jeux();
}
