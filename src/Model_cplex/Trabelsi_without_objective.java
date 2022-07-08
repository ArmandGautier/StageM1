package Model_cplex;

import java.util.ArrayList;
import ilog.concert.*;
import ilog.cplex.*;

public class Trabelsi_without_objective extends Model {
	float[] max_diff_utilities_par_joueur;
	float min = Float.MAX_VALUE;
	ArrayList<ArrayList<int[]>> list_A_i_par_joueur = new ArrayList<ArrayList<int[]>>();
	double[][] results_Uik;
	double[][] results_Xik;
	double nb_sol = 0;
	
	public double getNb_sol() {
		return this.nb_sol;
	}
	
	public Trabelsi_without_objective(ArrayList<int[]> profils, ArrayList<float[]> utilites) {
		super(profils, utilites);
		
		this.max_diff_utilities_par_joueur = new float[this.nb_joueur];
		for ( int i=0; i<this.nb_joueur; i++) {
			float min_i = getMin(this.utilites,i);
			this.max_diff_utilities_par_joueur[i] = getMax(this.utilites,i) - min_i;
			if ( min_i < min ) {
				min = min_i;
			}
		}
		
		for (int i=0; i<this.nb_joueur; i++) {
			ArrayList<int[]> temp = new ArrayList<int[]>();
			for (int[] profil : this.profils) {
				int[] prof_temp = new int[this.nb_joueur-1];
				int k=0;
				for (int j=0; j<profil.length; j++) {
					if (i!=j) {
						prof_temp[k]=profil[j];
						k++;
					}
				}
				if ( ! isIn(temp,prof_temp)) {
					temp.add(prof_temp);
				}
			}
			this.list_A_i_par_joueur.add(temp);
		}
	}
	
	/**
	 * Construct the model following the algorithm from Trabelsi (2020) in Games with incomplete information: a framework based on possibility theory.
	 * One adaptation has be made on constraint five for the moment
	 */

	public void construct_model() {
		try {
			
			IloCplex cplex = new IloCplex();
			cplex.setOut(null);
			
			// creation des variables
			
			double start1=System.currentTimeMillis();
			
			IloIntVar[][] Xik = new IloIntVar[this.nb_joueur][];
			IloNumVar[][] Uik = new IloNumVar[this.nb_joueur][];
			IloIntVar[][] Xa_i = new IloIntVar[this.nb_joueur][];
			
			// Xik and Uik
			
			for (int i=0; i<this.nb_joueur; i++) {
				int nb_variable = this.actions_possible_par_joueur.get(i).size();
				String[] s1 = new String[nb_variable];
				String[] s2 = new String[nb_variable];
				for (int j=0; j<nb_variable; j++) {
					s1[j] = "X"+i+this.actions_possible_par_joueur.get(i).get(j);
					s2[j] = "U"+i+this.actions_possible_par_joueur.get(i).get(j);
				}
				Xik[i] = cplex.boolVarArray(nb_variable,s1);
				Uik[i] = cplex.numVarArray(nb_variable, min, Double.MAX_VALUE,s2);
			}
			
			// Xa-i 
			
			for (int i=0; i<this.nb_joueur; i++) {
				int nb_variable = this.list_A_i_par_joueur.get(i).size();
				String[] s1 = new String[nb_variable];
				for (int j=0; j<nb_variable; j++) {
					s1[j] = "A_"+i+j;
				}
				Xa_i[i] = cplex.boolVarArray(nb_variable,s1); 
			}
			
			// creation des contraintes
			
			// Pour tout joueur la somme des Xik = 1
			
			for (int i=0; i<this.nb_joueur; i++) {
				cplex.addEq(1, cplex.sum(Xik[i]));
			}
			
			// Pour tout joueur la somme des Xa_i = 1
			
			for (int i=0; i<this.nb_joueur; i++) {
				cplex.addEq(1, cplex.sum(Xa_i[i]));
			}
			
			// Pour tout joueur si une action de ce joueur n'est pas selectionne alors aucune restriction d'action jointe contenant cette action ne peut etre selectionne
			
			for (int i=0; i<this.nb_joueur; i++) {
				int p=0;
				ArrayList<int[]> list_profil = this.list_A_i_par_joueur.get(i);
				for (int[] profil : list_profil) {
					int l=0;
					for (int j=0; j<this.nb_joueur; j++) {
						if (j!=i) {
							cplex.addLe(Xa_i[i][p],Xik[j][this.actions_possible_par_joueur.get(j).indexOf(profil[l])]);
							l++;
						}
					}
					p++;
				}
			}
			
			// definition de la valeur de l'utilite
			
			for (int i=0; i<this.nb_joueur; i++) {
				for (int k=0; k<this.actions_possible_par_joueur.get(i).size(); k++) {
					int p=0;
					IloLinearNumExpr c = cplex.linearNumExpr();
					ArrayList<int[]> list_profil_restreint = this.list_A_i_par_joueur.get(i);
					for (int[] profil_restreint : list_profil_restreint) {
						int[] profil = new int[this.nb_joueur];
						int l=0;
						for (int j=0; j<this.nb_joueur; j++) {
							if (i==j) {
								int Ai = this.actions_possible_par_joueur.get(i).get(k);
								profil[j] = Ai;
							}
							else {
								profil[j] = profil_restreint[l];
								l++;
							}
						}
						int ind = 0;
						for (int[] pro : this.profils) {
							if ( asSame(pro,profil) ) {
								c.addTerm(utilites.get(ind)[i], Xa_i[i][p]);
								break;
							}
							ind++;
						}
						p++;
					}
					cplex.addEq(Uik[i][k],c);
				}
			}

			// assures l'equilibre de nash ( constraint five )
			
			for (int i=0; i<this.nb_joueur; i++) {
				for (int k=0; k<this.actions_possible_par_joueur.get(i).size(); k++) {
					for (int k2=0; k2<this.actions_possible_par_joueur.get(i).size(); k2++) {
						if (k2 != k) {
							cplex.addGe(cplex.diff(Uik[i][k], Uik[i][k2]),cplex.diff(cplex.prod(this.max_diff_utilities_par_joueur[i], Xik[i][k]), this.max_diff_utilities_par_joueur[i]));
						}
					}
				}
			}
			double construction_time = System.currentTimeMillis()-start1;
			
			double start2=System.currentTimeMillis();
			this.solved = cplex.solve();
			/*this.solved = cplex.populate(); 
			this.nb_sol = cplex.getSolnPoolNsolns();*/
			this.solving_time = System.currentTimeMillis()-start2;
			this.construction_and_solving_time = this.solving_time+construction_time;
			
			if (this.solved) {
				this.obj = cplex.getObjValue();
				this.results_Uik = new double[this.nb_joueur][];
				this.results_Xik = new double[this.nb_joueur][];
				for (int i=0; i<this.nb_joueur; i++) {
					this.results_Xik[i] = cplex.getValues(Xik[i]);
					this.results_Uik[i] = cplex.getValues(Uik[i]);
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
	
	public void print_results() {
		if (this.solved) {
			for (int i=0; i<this.nb_joueur; i++) {
				for (int k=0; k<this.actions_possible_par_joueur.get(i).size(); k++) {
					if (this.results_Xik[i][k] == 1) {
						System.out.println("Le joueur " + i + " a joue l'action " + this.actions_possible_par_joueur.get(i).get(k) + ". Son utilite est de " + this.results_Uik[i][k] + ".");
					}
					else {
						System.out.println("Le joueur " + i + " n'a pas joue l'action " + this.actions_possible_par_joueur.get(i).get(k) + ". Son utilite aurait ete de " + this.results_Uik[i][k] + ".");
					}
				}
			}
		}
		else {
			System.out.println("Ce modele n'a pas ete construit ou alors il n'a pas d'equilibre de nash");
		}
	}

	public boolean equilibrium() {
		return this.solved;
	}
}

