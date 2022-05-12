package Model_cplex;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Model {
	ArrayList<int[]> profils = new ArrayList<int[]>();
	ArrayList<float[]> utilites = new ArrayList<float[]>();
	int nb_joueur;
	ArrayList<ArrayList<Integer>> actions_possible_par_joueur = new ArrayList<ArrayList<Integer>>();
	boolean solved = false;
	double obj;
	
	public Model(ArrayList<int[]> profils, ArrayList<float[]> utilites) {
		this.profils = profils;
		this.utilites = utilites;
		this.nb_joueur = profils.get(0).length;
		
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
	
	abstract void construct_model();
	abstract void print_results();
}

