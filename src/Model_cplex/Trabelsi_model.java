package Model_cplex;

import java.util.ArrayList;
import ilog.concert.*;
import ilog.cplex.*;

public class Trabelsi_model extends Model {
	float[] max_utilities_par_joueur;
	ArrayList<ArrayList<int[]>> list_A_i_par_joueur = new ArrayList<ArrayList<int[]>>();
	double[][] results_Uik;
	double[][] results_Xik;
	
	public Trabelsi_model(ArrayList<int[]> profils, ArrayList<float[]> utilites) {
		super(profils, utilites);
		
		this.max_utilities_par_joueur = new float[this.nb_joueur];
		for (float[] u : utilites) {
			for (int i=0; i<u.length; i++) {
				if (u[i] > this.max_utilities_par_joueur[i]) {
					this.max_utilities_par_joueur[i] = u[i];
				}
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
				if ( ! temp.contains(prof_temp)) {
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
			
			// création des variables
			
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
				Uik[i] = cplex.numVarArray(nb_variable, 0, Double.MAX_VALUE,s2);
			}
			
			// Xa-i 
			
			for (int i=0; i<this.nb_joueur; i++) {
				int nb_variable = this.list_A_i_par_joueur.get(i).size();
				String[] s1 = new String[nb_variable];
				for (int j=0; j<nb_variable; j++) {
					s1[j] = "A-i"+j;
				}
				Xa_i[i] = cplex.boolVarArray(nb_variable,s1); 
			}
			
			// déclaration de l'objectif
			
			IloLinearNumExpr objective = cplex.linearNumExpr();
			
			for (int i=0; i<this.nb_joueur; i++) {
				for (int k=0; k<this.actions_possible_par_joueur.get(i).size(); k++) {
					objective.addTerm(1, Uik[i][k]);
				}
			}
			
			cplex.addMaximize(objective);
			
			// création des contraintes
			
			// Pour tout joueur la somme des Xik = 1
			
			for (int i=0; i<this.nb_joueur; i++) {
				cplex.addEq(1, cplex.sum(Xik[i]));
			}
			
			// Pour tout joueur la somme des Xa_i = 1
			
			for (int i=0; i<this.nb_joueur; i++) {
				cplex.addEq(1, cplex.sum(Xa_i[i]));
			}
			
			// Pour tout joueur si une action de ce joueur n'est pas sélectionné alors aucune restriction d'action jointe contenant cette action ne peut être sélectionné
			
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
			
			// définition de la valeur de l'utilité
			
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

			// assures l'équilibre de nash ( constraint five )
			
			for (int i=0; i<this.nb_joueur; i++) {
				for (int k=0; k<this.actions_possible_par_joueur.get(i).size(); k++) {
					for (int k2=0; k2<this.actions_possible_par_joueur.get(i).size(); k2++) {
						if (k2 != k) {
							cplex.addGe(cplex.diff(Uik[i][k], Uik[i][k2]),cplex.diff(cplex.prod(this.max_utilities_par_joueur[i], Xik[i][k]), this.max_utilities_par_joueur[i]));
						}
					}
				}
			}
			
			double construction_time = System.currentTimeMillis()-start1;
			
			double start2=System.currentTimeMillis();
			this.solved = cplex.solve();
			this.solving_time = System.currentTimeMillis()-start2;
			this.construction_and_solving_time = this.solving_time+construction_time;
			
			if (this.solved) {
				this.obj = cplex.getObjValue();
				/*this.results_Uik = new double[this.nb_joueur][];
				this.results_Xik = new double[this.nb_joueur][];
				for (int i=0; i<this.nb_joueur; i++) {
					this.results_Xik[i] = cplex.getValues(Xik[i]);
					this.results_Uik[i] = cplex.getValues(Uik[i]);
				}*/
				//cplex.writeSolutions("m2");
			}
			
			cplex.close();
		} 
		catch (IloException exc) {
			exc.printStackTrace();
		}
	}
	
	/**
	 * @param profil1
	 * @param profil2
	 * @return true if profil1 is the same as profil2
	 */
	private boolean  asSame(int[] profil1, int[] profil2) {
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
	
	public void print_results() {
		if (this.solved) {
			for (int i=0; i<this.nb_joueur; i++) {
				for (int k=0; k<this.actions_possible_par_joueur.get(i).size(); k++) {
					if (this.results_Xik[i][k] == 1) {
						System.out.println("Le joueur " + i + " a joué l'action " + this.actions_possible_par_joueur.get(i).get(k) + ". Son utilité est de " + this.results_Uik[i][k] + ".");
					}
					else {
						System.out.println("Le joueur " + i + " n'a pas joué l'action " + this.actions_possible_par_joueur.get(i).get(k) + ". Son utilité aurait été de " + this.results_Uik[i][k] + ".");
					}
				}
			}
		}
		else {
			System.out.println("Ce modèle n'a pas été construit ou alors il n'a pas d'équilibre de nash");
		}
	}

	public boolean equilibrium() {
		return this.solved;
	}
}

