package Model_cplex;

import java.util.ArrayList;
import ilog.concert.*;
import ilog.cplex.*;

public class Model1 extends Model {
	
	double[] results_Vi;
	double[][] results_Xik;

	public Model1(ArrayList<int[]> profils, ArrayList<float[]> utilites) {
		super(profils, utilites);
	}

	public void construct_model() {
		try {
			
			IloCplex cplex = new IloCplex();
			
			// création des variables
			
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
			
			// déclaration de l'objectif
			
			IloLinearNumExpr objective = cplex.linearNumExpr();
			
			for (int i=0; i<this.nb_joueur; i++) {
				objective.addTerm(1, Vi[i]);
			}
			
			cplex.addMinimize(objective);
			
			// création des contraintes
			
			// Pour tout joueur la somme des Xik = 1
			
			for (int i=0; i<this.nb_joueur; i++) {
				cplex.addEq(1, cplex.sum(Xik[i]));
			}
			
			// contrainte sur Vi
			
			for (int i=0; i<this.nb_joueur; i++) {
				int p=0;
				for (int[] profil : this.profils) {
					ArrayList<Integer> list_indice_profil_similaire = recup_identique_profil(profil,i); 
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
						for (int k=0; k<this.nb_joueur; k++) {
							c.addTerm(1, Xik[k][this.actions_possible_par_joueur.get(k).indexOf(profil[k])]);
						}
						IloNumExpr constraint = cplex.diff(Vi[i],c);
						cplex.addGe(constraint, epsilon - this.nb_joueur); 
					}
					p++;
				}
			}
			
			cplex.setOut(null);
			
			if (cplex.solve()) {
				this.solved = true;
				this.obj = cplex.getObjValue();
				this.results_Vi = cplex.getValues(Vi);
				this.results_Xik = new double[this.nb_joueur][];
				for (int i=0; i<this.nb_joueur; i++) {
					this.results_Xik[i] = cplex.getValues(Xik[i]);
				}
			}
			
			cplex.close();
		} 
		catch (IloException exc) {
			exc.printStackTrace();
		}
	}

	private ArrayList<Integer> recup_identique_profil(int[] profil, int joueur) {
		ArrayList<Integer> profil_similaire = new ArrayList<Integer>();
		int i=0;
		for (int[] profil2 : this.profils) {
			boolean same = true;
			for (int j=0; j<nb_joueur; j++) {
				// si on regarde l'action d'un autre joueur que le joueur i
				if ( joueur != j) {
					same = profil[j] == profil2[j];
				}
				// si on regarde l'action du joueur i
				else {
					same = profil[j] != profil2[j];
				}
				if (! same) {
					break;
				}
			}
			if (same) {
				profil_similaire.add(i);
			}
			i++;
		}
		return profil_similaire;
	}
	
	public void print_results() {
		if (this.solved) {
			for (int i=0; i<this.nb_joueur; i++) {
				for (int k=0; k<this.actions_possible_par_joueur.get(i).size(); k++) {
					if (this.results_Xik[i][k] == 1) {
						System.out.println("Le joueur " + i + " a joué l'action " + this.actions_possible_par_joueur.get(i).get(k));
					}
				}
			}
			if (this.obj == 0) {
				System.out.println("Ce profil est un équilibre de nash");
			}
			else {
				System.out.println("Ce profil n'est pas un équilibre de nash");
			}
		}
		else {
			System.out.println("Ce modèle n'a pas été construit ou alors il n'a pas de solution");
		}
	}
}
