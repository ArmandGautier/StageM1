package Model_cplex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Model_for_hypergraphical_game {
	Map<Integer,ArrayList<int[]>> profiles = new HashMap<>();
	Map<Integer,ArrayList<float[]>> utilites = new HashMap<>();
	int nb_joueur;
	int nb_jeux;
	ArrayList<ArrayList<Integer>> actions_possible_par_joueur = new ArrayList<ArrayList<Integer>>();
	ArrayList<ArrayList<Integer>> player_by_game = new ArrayList<ArrayList<Integer>>();
	boolean solved = false;
	double obj;
	double solving_time;
	double construction_and_solving_time;
	
	public Model_for_hypergraphical_game(Map<Integer,ArrayList<int[]>> profils, Map<Integer,ArrayList<float[]>> utilites, ArrayList<ArrayList<Integer>> player_by_game, int nb_joueur) {
		this.profiles = profils;
		this.utilites = utilites;
		this.nb_joueur = nb_joueur;
		this.nb_jeux = profiles.size();
		this.player_by_game = player_by_game;
		
		for (int i=0; i<this.nb_joueur; i++) {
			ArrayList<Integer> action_possible = new ArrayList<Integer>();
			for (Integer key : this.profiles.keySet()) {
				if (this.player_by_game.get(key).contains(i)) {
					int ind_i_in_localGame = this.player_by_game.get(key).indexOf(i);
					for (int[] profil : this.profiles.get(key) ) {
						if (! action_possible.contains(profil[ind_i_in_localGame])) {
							action_possible.add(profil[ind_i_in_localGame]);
						}
					}	
				}
			}
			this.actions_possible_par_joueur.add(action_possible);
		}
		
		for (ArrayList<Integer> list_act : this.actions_possible_par_joueur) {
			Collections.sort(list_act);
		}

	} 
	
	public abstract void construct_model();
	public abstract boolean equilibrium();
	/**
	 * @return the solving time in second
	 */
	public double get_solving_time() {
		return this.solving_time;
	}
	
	public double get_construction_and_solving_time() {
		return this.construction_and_solving_time;
	}
	
	public void readInFile(String filename) {
		
	}
	
	/**
	 * @param utilities
	 * @param local_index
	 * @return the minimal utility
	 */
	protected float getMin(ArrayList<float[]> utilities, int local_index) {
		float min = Float.MAX_VALUE;
		for (float[] utility : utilities) {
			if ( utility[local_index] < min ) {
				min = utility[local_index];
			}
		}
		return min;
	}
	
	/**
	 * @param utilities
	 * @param local_index
	 * @return the maximal utility
	 */
	protected float getMax(ArrayList<float[]> utilities, int local_index) {
		float max = Float.MIN_VALUE;
		for (float[] utility : utilities) {
			if ( utility[local_index] > max ) {
				max = utility[local_index];
			}
		}
		return max;
	}
}
