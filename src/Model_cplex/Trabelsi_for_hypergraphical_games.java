package Model_cplex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Trabelsi_for_hypergraphical_games extends Model_for_hypergraphical_game {
	float[] max_utilities_par_joueur;
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
						int[] prof_temp = new int[this.nb_joueur-1];
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
		
		this.max_utilities_par_joueur = new float[this.nb_joueur];
		// TO DO compute max_utilities
	}

	@Override
	public void construct_model() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean equilibrium() {
		return this.solved;
	}

}
