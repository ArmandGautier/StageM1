package Model_cplex;

import java.util.ArrayList;
import ilog.concert.*;
import ilog.cplex.*;

public class Daskalakis_model extends Model {
	
	double[] results_Vi;
	double[][] results_Xik;

	public Daskalakis_model(ArrayList<int[]> profils, ArrayList<float[]> utilites) {
		super(profils, utilites);
	}

	public void construct_model() {
		try {
			
			IloCplex cplex = new IloCplex();
			cplex.setOut(null);
			
			double start1=System.currentTimeMillis();
			
			// creation des variables
			
			IloIntVar[][] Xik = new IloIntVar[this.nb_joueur][];
			IloNumVar[] Vi = new IloNumVar[this.nb_joueur];
			
			// Xik
			
			for (int i=0; i<this.nb_joueur; i++) {
				int nb_variable = this.actions_possible_par_joueur.get(i).size();
				String[] s1 = new String[nb_variable];
				for (int j=0; j<nb_variable; j++) {
					s1[j] = "X"+i+this.actions_possible_par_joueur.get(i).get(j);
				}
				Xik[i] = cplex.boolVarArray(nb_variable,s1);
			}
			
			// Vi
			
			for (int i=0; i<this.nb_joueur; i++) {
				Vi[i] = cplex.numVar(0, Double.MAX_VALUE, "V"+i);
			}
			
			// declaration de l'objectif
			
			IloLinearNumExpr objective = cplex.linearNumExpr();
			
			for (int i=0; i<this.nb_joueur; i++) {
				objective.addTerm(1, Vi[i]);
			}
			
			cplex.addMinimize(objective);
			
			// creation des contraintes
			
			// Pour tout joueur la somme des Xik = 1
			
			for (int i=0; i<this.nb_joueur; i++) {
				cplex.addEq(1, cplex.sum(Xik[i]));
			}
			
			// contrainte sur Vi
			
			for (int i=0; i<this.nb_joueur; i++) {
				int p=0;
				for (int[] profil : this.profils) {
					ArrayList<Integer> list_indice_profil_similaire = getSameProfile(profil,i); 
					boolean best_response = true;
						for (int profil_similaire : list_indice_profil_similaire) {
							if (!(utilites.get(p)[i] >= utilites.get(profil_similaire)[i])) {
								best_response = false;
								break;
							}
						}
					if (! best_response) {
						double epsilon = 0.1;
						IloLinearNumExpr c = cplex.linearNumExpr();
						for (int j=0; j<this.nb_joueur; j++) {
							c.addTerm(1, Xik[j][this.actions_possible_par_joueur.get(j).indexOf(profil[j])]);
						}
						IloNumExpr constraint = cplex.diff(Vi[i],c);
						cplex.addGe(constraint, epsilon - this.nb_joueur); 
					}
					p++;
				}
			}
			double construction_time = System.currentTimeMillis()-start1;
			
			double start2=System.currentTimeMillis();
			this.solved = cplex.solve();
			this.solving_time = System.currentTimeMillis()-start2;
			this.construction_and_solving_time = this.solving_time+construction_time;
			
			if (this.solved) {
				this.obj = cplex.getObjValue();
				this.results_Vi = cplex.getValues(Vi);
				this.results_Xik = new double[this.nb_joueur][];
				for (int i=0; i<this.nb_joueur; i++) {
					this.results_Xik[i] = cplex.getValues(Xik[i]);
				}
				for (int i=0; i<this.nb_joueur; i++) {
					for (int k=0; k<this.actions_possible_par_joueur.get(i).size(); k++) {
						if (this.results_Xik[i][k] == 1) {
							this.actionsOfSolution[i] = this.actions_possible_par_joueur.get(i).get(k);
							break;
						}
					}
				}
			}
			
			cplex.close();
		} 
		catch (IloException exc) {
			exc.printStackTrace();
		}
	}
	
	/**
	 * @param profil
	 * @param joueur
	 * @return a list of index of profiles who are the same as "profil" excepted the action of player "joueur" that must be different
	 */
	private ArrayList<Integer> getSameProfile(int[] profil, int joueur) {
		ArrayList<Integer> sameProfile = new ArrayList<Integer>();
		int profileIndex=0;
		for (int[] p : this.profils) {
			boolean same = true;
			for (int j=0; j<this.nb_joueur; j++) {
				// si on regarde l'action d'un autre joueur que le joueur i
				if ( joueur != j) {
					same = profil[j] == p[j];
				}
				// si on regarde l'action du joueur i
				else {
					same = profil[j] != p[j];
				}
				if (! same) {
					break;
				}
			}
			if (same) {
				sameProfile.add(profileIndex);
			}
			profileIndex++;
		}
		return sameProfile;
	}
	
	public void print_results() {
		if (this.solved) {
			for (int i=0; i<this.nb_joueur; i++) {
				for (int k=0; k<this.actions_possible_par_joueur.get(i).size(); k++) {
					if (this.results_Xik[i][k] == 1) {
						System.out.println("Le joueur " + i + " a joue l'action " + this.actions_possible_par_joueur.get(i).get(k));
					}
				}
			}
			if (this.obj == 0) {
				System.out.println("Ce profil est un equilibre de nash");
			}
			else {
				System.out.println("Ce profil n'est pas un equilibre de nash");
			}
		}
		else {
			System.out.println("Ce modele n'a pas ete construit ou alors il n'a pas de solution");
		}
	}
	
	public boolean equilibrium() {
		return (this.obj == 0);
	}
}
