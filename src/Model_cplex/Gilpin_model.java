package Model_cplex;

import java.util.ArrayList;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

public class Gilpin_model extends Model {

	private double[][] results_Pik;
	private double[][] results_Uik;
	private double[] results_Ui;
	double[] max_diff_utilities_par_joueur;

	public Gilpin_model(ArrayList<int[]> profils, ArrayList<float[]> utilites) {
		super(profils, utilites);
		
		this.max_diff_utilities_par_joueur = new double[this.nb_joueur];
		double[] max_uti = new double[this.nb_joueur];
		double[] min_uti = new double[this.nb_joueur];
		for (int i=0; i<this.nb_joueur; i++) {
			max_uti[i] = Double.MIN_VALUE;
			min_uti[i] = Double.MAX_VALUE;
		}
		for (float[] u : utilites) {
			for (int i=0; i<u.length; i++) {
				if (u[i] > max_uti[i]) {
					max_uti[i] = u[i];
				}
				if (u[i] < min_uti[i]) {
					min_uti[i] = u[i];
				}
			}
		}
		for (int i=0; i<this.nb_joueur; i++) {
			this.max_diff_utilities_par_joueur[i] = max_uti[i] - min_uti[i];
		}
	}

	@Override
	public void construct_model() {
		try {
			IloCplex cplex = new IloCplex();
			cplex.setOut(null);
			
			// création des variables
			
			double start1=System.currentTimeMillis();
			
			IloIntVar[][] Bik = new IloIntVar[this.nb_joueur][];
			IloIntVar[][] Pik = new IloIntVar[this.nb_joueur][];
			IloNumVar[][] Uik = new IloNumVar[this.nb_joueur][];
			IloNumVar[][] Rik = new IloNumVar[this.nb_joueur][];
			IloNumVar[] Ui = new IloNumVar[this.nb_joueur];
			IloNumVar[] Ri = new IloNumVar[this.nb_joueur];
			
			for (int i=0; i<this.nb_joueur; i++) {
				int nb_variable = this.actions_possible_par_joueur.get(i).size();
				Bik[i] = cplex.boolVarArray(nb_variable);
				Pik[i] = cplex.boolVarArray(nb_variable);
				Rik[i] = cplex.numVarArray(nb_variable, 0, Double.MAX_VALUE);
				Uik[i] = cplex.numVarArray(nb_variable, 0, Double.MAX_VALUE);
				Ui[i] = cplex.numVar(0, Double.MAX_VALUE);
				Ri[i] = cplex.numVar(0, Double.MAX_VALUE);
			}
			
			// création des contraintes
			
			// Somme des Pik == 1
			
			for (int i=0; i<this.nb_joueur; i++) {
				cplex.addEq(1, cplex.sum(Pik[i]));
			}
			
			// contrainte bonne pour 2 joueurs pas plus 
			
			for (int i=0; i<this.nb_joueur; i++) {
				int nb_actions = this.actions_possible_par_joueur.get(i).size();
				int nb_actions_autre_joueur = this.actions_possible_par_joueur.get(1-i).size();
				for (int j=0; j<nb_actions; j++) {
					IloLinearNumExpr c = cplex.linearNumExpr();
					for (int j2=0; j2<nb_actions_autre_joueur; j2++) {
						int[] l = {this.actions_possible_par_joueur.get(i).get(j),this.actions_possible_par_joueur.get(1-i).get(j2)};
						int ind = 0;
						for (int[] profil : this.profils) {
							if (asSame(profil,l)) {
								c.addTerm(Pik[1-i][j2], this.utilites.get(ind)[i]);
								break;
							}
							ind++;
						}
					}
					cplex.addEq(Uik[i][j], c);
				}
			}
			
			// Ui >= Ui,k pour tous k
			
			for (int i=0; i<this.nb_joueur; i++) {
				int nb_actions = this.actions_possible_par_joueur.get(i).size();
				for (int j=0; j<nb_actions; j++) {
					cplex.addGe(Ui[i], Uik[i][j]);
				}
			}
			
			// Ri,k = Ui - Ui,k
			
			for (int i=0; i<this.nb_joueur; i++) {
				int nb_actions = this.actions_possible_par_joueur.get(i).size();
				for (int j=0; j<nb_actions; j++) {
					cplex.addEq(Rik[i][j], cplex.diff(Ui[i], Uik[i][j]));
				}
			}
			
			// Pik <= 1 - Bik
			
			for (int i=0; i<this.nb_joueur; i++) {
				int nb_actions = this.actions_possible_par_joueur.get(i).size();
				for (int j=0; j<nb_actions; j++) {
					cplex.addLe(Pik[i][j], cplex.diff(1, Bik[i][j]));
				}
			}
			
			// Rik <= Ui*Bik
			
			for (int i=0; i<this.nb_joueur; i++) {
				int nb_actions = this.actions_possible_par_joueur.get(i).size();
				for (int j=0; j<nb_actions; j++) {
					cplex.addLe(Rik[i][j], cplex.prod(this.max_diff_utilities_par_joueur[i], Bik[i][j]));
				}
			}
			double construction_time = System.currentTimeMillis()-start1;
			
			double start2=System.currentTimeMillis();
			this.solved = cplex.solve();
			this.solving_time = System.currentTimeMillis()-start2;
			this.construction_and_solving_time = this.solving_time+construction_time;
			
			if (this.solved) {
				this.obj = cplex.getObjValue();
				this.results_Uik = new double[this.nb_joueur][];
				this.results_Pik = new double[this.nb_joueur][];
				this.results_Ui = new double[this.nb_joueur];
				for (int i=0; i<this.nb_joueur; i++) {
					this.results_Pik[i] = cplex.getValues(Pik[i]);
					this.results_Uik[i] = cplex.getValues(Uik[i]);
					this.results_Ui = cplex.getValues(Ui);
				}
				for (int i=0; i<this.nb_joueur; i++) {
					for (int k=0; k<this.actions_possible_par_joueur.get(i).size(); k++) {
						if (this.results_Pik[i][k] == 1) {
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

	@Override
	public void print_results() {
		if (this.solved) {
			System.out.println(this.obj);
			for (int i=0; i<this.nb_joueur; i++) {
				System.out.println(results_Ui[i]);
				for (int k=0; k<this.actions_possible_par_joueur.get(i).size(); k++) {
					if (this.results_Pik[i][k] == 1) {
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

	@Override
	public boolean equilibrium() {
		return this.solved;
	}

}
