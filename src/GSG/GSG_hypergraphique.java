package GSG;

import java.util.ArrayList;

public class GSG_hypergraphique extends GSG {
	
	ArrayList<GSG_SNF> gsg_snf = new ArrayList<GSG_SNF>();
	ArrayList<ArrayList<Integer>> list_of_local_game_players = new ArrayList<ArrayList<Integer>>();
	
	public GSG_hypergraphique(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float lambda, int[] actions, ArrayList<ArrayList<Integer>> list_of_local_game_players) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, actions);
		this.list_of_local_game_players = list_of_local_game_players;
	}

	/**
	 * compute all the gsg in SNF who represent the hypergraphical representation of the gsg
	 */
	public void calcul_val() {
		for ( ArrayList<Integer> list_player : this.list_of_local_game_players) {
			int local_nb_attacker = 0;
			int local_nb_defender = 0;
			for (int j=0; j<list_player.size(); j++) {
				if (list_player.get(j) > this.nb_attacker) {
					local_nb_defender++;
				}
				else {
					local_nb_attacker++;
				}
			}
			
			ArrayList<ArrayList<Integer>> local_possible_actions = new ArrayList<ArrayList<Integer>>();
			for (int i=0; i<this.nb_player; i++) {
				if (list_player.contains(i)) {
					local_possible_actions.add(this.possible_actions.get(i));
				}
			}
			
			// TO DO : gérer les différentes utilitées
			
			GSG_SNF gsg = new GSG_SNF(local_nb_attacker,local_nb_defender,this.attacker_utility,this.defender_utility,this.lambda,local_possible_actions);
			this.gsg_snf.add(gsg);
		}
	}

	void afficher_jeux() {
		// TODO Auto-generated method stub
	}
}