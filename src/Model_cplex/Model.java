package Model_cplex;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Model {
	ArrayList<int[]> profils = new ArrayList<int[]>();
	ArrayList<float[]> utilites = new ArrayList<float[]>();
	int[] actionsOfSolution;

	int nb_joueur;
	ArrayList<ArrayList<Integer>> actions_possible_par_joueur = new ArrayList<ArrayList<Integer>>();
	boolean solved = false;
	double obj;
	double solving_time;
	double construction_and_solving_time;
	
	public Model(ArrayList<int[]> profils, ArrayList<float[]> utilites) {
		this.profils = profils;
		this.utilites = utilites;
		this.nb_joueur = profils.get(0).length;
		this.actionsOfSolution = new int[this.nb_joueur];
		
		for (int i=0; i<this.nb_joueur; i++) {
			ArrayList<Integer> action_possible = new ArrayList<Integer>();
			for (int[] profil : this.profils) {
				if (! action_possible.contains(profil[i])) {
					action_possible.add(profil[i]);
				}
			}
			this.actions_possible_par_joueur.add(action_possible);
		}
		
		for (ArrayList<Integer> list_act : this.actions_possible_par_joueur) {
			Collections.sort(list_act);
		}

	} 
	
	public abstract void construct_model();
	public abstract void print_results();
	public abstract boolean equilibrium();
	
	/**
	 * @return the solving time in second
	 */
	public double get_solving_time() {
		return this.solving_time;
	}
	
	/**
	 * @return the time of construction and solving in second
	 */
	public double get_construction_and_solving_time() {
		return this.construction_and_solving_time;
	}
	
	/**
	 * @return the actionsOfSolution
	 */
	public int[] getActionsOfSolution() {
		return actionsOfSolution;
	}
	
	public void readInFile(String filename) {
		
	}
	
	/**
	 * @param utilities
	 * @param local_index
	 * @return the minimal utility
	 */
	protected float getMin(ArrayList<float[]> utilities, int index_player) {
		float min = Float.MAX_VALUE;
		for (float[] utility : utilities) {
			if ( utility[index_player] < min ) {
				min = utility[index_player];
			}
		}
		return min;
	}
	
	/**
	 * @param utilities
	 * @param local_index
	 * @return the maximal utility
	 */
	protected float getMax(ArrayList<float[]> utilities, int index_player) {
		float max = Float.MIN_VALUE;
		for (float[] utility : utilities) {
			if ( utility[index_player] > max ) {
				max = utility[index_player];
			}
		}
		return max;
	}
	
	/**
	 * @param profil1
	 * @param profil2
	 * @return true if profil1 is the same as profil2
	 */
	protected boolean  asSame(int[] profil1, int[] profil2) {
		if ( profil1.length != profil2.length) {
			return false;
		}
		for (int i=0; i<profil1.length; i++) {
			if (profil1[i] != profil2[i]) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @param temp
	 * @param prof_temp
	 * @return true if prof_temp belongs to temp, false otherwise
	 */
	protected boolean isIn(ArrayList<int[]> temp, int[] prof_temp) {
		for ( int[] t : temp) {
			if ( asSame(t,prof_temp)) {
				return true;
			}
		}
		return false;
	}
}

