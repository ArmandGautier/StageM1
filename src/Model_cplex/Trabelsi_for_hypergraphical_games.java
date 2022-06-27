package Model_cplex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

public class Trabelsi_for_hypergraphical_games extends Model_for_hypergraphical_game {
	float[] max_diff_utilities_par_joueur;
	Map<Integer,ArrayList<ArrayList<int[]>>> list_A_i_par_joueur = new HashMap<>();
	double[][] results_Uik;
	double[][] results_Xik;
	double nb_sol = 0;

	public Trabelsi_for_hypergraphical_games(Map<Integer, ArrayList<int[]>> profils, Map<Integer, ArrayList<float[]>> utilites, ArrayList<ArrayList<Integer>> player_by_game) {
		super(profils, utilites, player_by_game);
		
		for (Integer key : this.profiles.keySet()) {
			ArrayList<ArrayList<int[]>> temp1 = new ArrayList<ArrayList<int[]>>();
			for (int i=0; i<this.nb_joueur; i++) {
				ArrayList<int[]> temp2 = new ArrayList<int[]>();
				if (this.player_by_game.get(key).contains(i)) {
					for (int[] profil : this.profiles.get(key) ) {
						int[] prof_temp = new int[profil.length-1];
						int k=0;
						for (int j=0; j<profil.length; j++) {
							if (i!=j) {
								prof_temp[k]=profil[j];
								k++;
							}
						}
						if ( ! temp2.contains(prof_temp)) {
							temp2.add(prof_temp);
						}
					}
					temp1.add(temp2);
				}
			}
			this.list_A_i_par_joueur.put(key, temp1);
		}
		
		// donne un majorant de la différence entre l'uti max et l'uti min d'un joueur
		this.max_diff_utilities_par_joueur = new float[this.nb_joueur];
		for (Integer key : this.profiles.keySet()) {
			int local_index = 0;
			for (int i=0; i<this.nb_joueur; i++) { 
				if (this.player_by_game.get(key).contains(i)) {
					local_index = this.player_by_game.indexOf(i);
					float local_max = 0;
					float local_min = 0;
					local_max = getMax(this.utilites.get(key),local_index);
					local_min = getMin(this.utilites.get(key),local_index);
					this.max_diff_utilities_par_joueur[i] += (local_max-local_min);
				}
			}
		}
	}

	/**
	 * @param utilities
	 * @param local_index
	 * @return the minimal utility
	 */
	private float getMin(ArrayList<float[]> utilities, int local_index) {
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
	private float getMax(ArrayList<float[]> utilities, int local_index) {
		float max = Float.MIN_VALUE;
		for (float[] utility : utilities) {
			if ( utility[local_index] > max ) {
				max = utility[local_index];
			}
		}
		return max;
	}

	public void construct_model() { 
		try {
			
			IloCplex cplex = new IloCplex();
			cplex.setOut(null);
			
			// création des variables
			
			double start1=System.currentTimeMillis();
			
			IloIntVar[][] Xik = new IloIntVar[this.nb_joueur][];
			IloNumVar[][] Uik = new IloNumVar[this.nb_joueur][];
			IloIntVar[][][] Xa_i = new IloIntVar[this.nb_joueur][this.nb_jeux][];
			
			// Xik and Uik
			
			for (int i=0; i<this.nb_joueur; i++) {
				int nb_variable = this.actions_possible_par_joueur.get(i).size();
				Xik[i] = cplex.boolVarArray(nb_variable);
				Uik[i] = cplex.numVarArray(nb_variable, 0, Double.MAX_VALUE);
			}
			
			// Xa-i 
			
			for (int i=0; i<this.nb_joueur; i++) {
				for (Integer key : this.profiles.keySet()) {
					if (this.player_by_game.get(key).contains(i)) {
						int ind_i_in_localGame = this.player_by_game.get(key).indexOf(i);
						int nb_variable = this.list_A_i_par_joueur.get(key).get(ind_i_in_localGame).size();
						Xa_i[i][key] = cplex.boolVarArray(nb_variable); 
					}
				}
			}
			
			// création des contraintes
			
			// Pour tout joueur la somme des Xik = 1
			
			for (int i=0; i<this.nb_joueur; i++) {
				cplex.addEq(1, cplex.sum(Xik[i]));
			}
			
			// Pour tout joueur, pour chaque jeu local, la somme des Xa_i = 1
			
			for (int i=0; i<this.nb_joueur; i++) {
				for (Integer key : this.profiles.keySet()) {
					if (this.player_by_game.get(key).contains(i)) {
						cplex.addEq(1, cplex.sum(Xa_i[i][key]));
					}
				}
			}
			
			// Pour tout joueur si une action de ce joueur n'est pas sélectionné alors aucune restriction d'action jointe contenant cette action ne peut être sélectionné
			
			for (int i=0; i<this.nb_joueur; i++) {
				for (Integer key : this.profiles.keySet()) {
					if (this.player_by_game.get(key).contains(i)) {
						int ind_i_in_localGame = this.player_by_game.get(key).indexOf(i);
						int p=0;
						ArrayList<int[]> list_local_Ai = this.list_A_i_par_joueur.get(key).get(ind_i_in_localGame);
						for (int[] profil : list_local_Ai) {
							int l = 0;
							for (int j=0; j<this.player_by_game.get(key).size(); j++) {
								int ind_j = this.player_by_game.get(key).get(j);
								if ( ind_j != i ) {
									cplex.addLe(Xa_i[i][key][p],Xik[ind_j][this.actions_possible_par_joueur.get(ind_j).indexOf(profil[l])]);
									l++; // sert à parcourir le profil restreint
								}
							}
							p++;
						}
					}
				}
			}
			
			// définition de la valeur de l'utilité
			
			for (int i=0; i<this.nb_joueur; i++) {
				for (int k=0; k<this.actions_possible_par_joueur.get(i).size(); k++) {
					IloLinearNumExpr c = cplex.linearNumExpr();
					for (Integer key : this.profiles.keySet()) { 
						if (this.player_by_game.get(key).contains(i)) {
							int ind_i_in_localGame = this.player_by_game.get(key).indexOf(i);
							int p=0;
							ArrayList<int[]> list_profil_restreint = this.list_A_i_par_joueur.get(key).get(ind_i_in_localGame);
							for (int[] profil_restreint : list_profil_restreint) {
								int[] profil = new int[this.nb_joueur];
								int l=0;
								// On reconstruit le profil comlet ( en rajoutant la k-ième action du joueur i )
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
								for (int[] pro : this.profiles.get(key)) {
									if ( asSame(pro,profil) ) {
										c.addTerm(this.utilites.get(key).get(ind)[ind_i_in_localGame], Xa_i[i][key][p]);
										break;
									}
									ind++;
								}
								p++;
							}
						}
						
					}
					cplex.addEq(Uik[i][k],c);
				}
			}

			// assures l'équilibre de nash ( constraint five )
			
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
				//cplex.writeSolutions("m2bis");
				
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

	@Override
	public boolean equilibrium() {
		return this.solved;
	}

}
