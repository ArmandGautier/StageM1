package GSG;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import decision.game.profile.ActionProfile;
import decision.game.profile.Profile;

public class GSG_hypergraphique extends GSG {
	
	ArrayList<GSG_SNF> gsg_snf = new ArrayList<GSG_SNF>();
	ArrayList<ArrayList<Integer>> list_of_local_game_players = new ArrayList<ArrayList<Integer>>();
	
	public GSG_hypergraphique(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float lambda, int[] actions, ArrayList<ArrayList<Integer>> list_of_local_game_players) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, actions);
		this.list_of_local_game_players = list_of_local_game_players;
	}
	public GSG_hypergraphique(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float[] lambda, int[] actions, ArrayList<ArrayList<Integer>> list_of_local_game_players) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, actions);
		this.list_of_local_game_players = list_of_local_game_players;
	}
	public GSG_hypergraphique(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float lambda, ArrayList<ArrayList<Integer>> possible_actions, ArrayList<ArrayList<Integer>> list_of_local_game_players) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, possible_actions);
		this.list_of_local_game_players = list_of_local_game_players;
	}
	public GSG_hypergraphique(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float[] lambda, ArrayList<ArrayList<Integer>> possible_actions, ArrayList<ArrayList<Integer>> list_of_local_game_players) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, possible_actions);
		this.list_of_local_game_players = list_of_local_game_players;
	}
	public GSG_hypergraphique(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float lambda, int[] actions, ArrayList<ArrayList<Integer>> list_of_local_game_players, int fine_or_bribe) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, actions, fine_or_bribe);
		this.list_of_local_game_players = list_of_local_game_players;
	}
	public GSG_hypergraphique(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float[] lambda, int[] actions, ArrayList<ArrayList<Integer>> list_of_local_game_players, int fine_or_bribe) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, actions, fine_or_bribe);
		this.list_of_local_game_players = list_of_local_game_players;
	}
	public GSG_hypergraphique(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float lambda, ArrayList<ArrayList<Integer>> possible_actions, ArrayList<ArrayList<Integer>> list_of_local_game_players, int fine_or_bribe) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, possible_actions, fine_or_bribe);
		this.list_of_local_game_players = list_of_local_game_players;
	}
	public GSG_hypergraphique(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float[] lambda, ArrayList<ArrayList<Integer>> possible_actions, ArrayList<ArrayList<Integer>> list_of_local_game_players, int fine_or_bribe) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, possible_actions, fine_or_bribe);
		this.list_of_local_game_players = list_of_local_game_players;
	}

	/**
	 * compute all the gsg in SNF who represent the hypergraphical representation of the gsg
	 */
	public void calcul_val() {
		
		switch(this.attacker_utility) { // TO DO voir les compatibilit�s entre les uti braco/gc
		
		case "Poach or hide" : // passer au log
			
			break;
			
		case "Poach and bribe" :
			
			for (int i=0; i<this.lambda.length; i++) {
				this.lambda[i] = this.lambda[i]/this.nb_defender;
			}
			break;
			
		case "Team-Poach or hide" : // passer au log
			
			break;
			
		case "Team-Poach and bribe" : 
			
			// on cr�� un jeux entre les bracos pour le partage de la ressource
			
			ArrayList<ArrayList<Integer>> local_possible_actions = new ArrayList<ArrayList<Integer>>();
			for (int i=0; i<this.nb_attacker; i++) {
				local_possible_actions.add(this.possible_actions.get(i));
			}
			GSG_SNF gsg = new GSG_SNF(this.nb_attacker,0,this.attacker_utility,this.defender_utility,this.lambda,local_possible_actions,this.fine_or_bribe);
			gsg.calcul_val();
			this.dimension += gsg.dimension;
			this.gsg_snf.add(gsg);
			
			// on met les valeurs des ressources � z�ro et on laisse dans la suite les jeux bimatriciel entre braco et garde chasse, pour que les braco perdent fine_or_bribe quand un gc les attrapes
			
			for (int i=0; i<this.lambda.length; i++) {
				this.lambda[i] = 0;
			}
			
			break;
			
		default :
			
			System.out.println("Utilit� des braconniers non reconnu");
			return;
		}
		
		switch(this.defender_utility) {
		
		case "Defend the location" : // passer au log
			
			break;
			
		case "Bribemon : Gotta catch'em all!" : // rien � faire ici
			
			break;
			
		case "captor" : // un jeux par braco, participant aux jeux locaux donn� en param ? si oui rien � faire ici
			
			break;
			
		case "Avoid poaching" :
			
			break;
			
		default :
			
			System.out.println("Utilit� des gardes chasses non reconnu");
			return;
			
		}
		
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
			
			GSG_SNF gsg = new GSG_SNF(local_nb_attacker,local_nb_defender,this.attacker_utility,this.defender_utility,this.lambda,local_possible_actions,this.fine_or_bribe);
			gsg.calcul_val();
			this.dimension += gsg.dimension;
			this.gsg_snf.add(gsg);
		}
	}

	void afficher_jeux() {
		
	}
	
	
	@Override
	public Profile<Float> utility(ActionProfile p) {
		Iterator<GSG_SNF> itGames = this.gsg_snf.iterator();
		Iterator<ArrayList<Integer>> itPlayers = this.list_of_local_game_players.iterator();
		
		while (itGames.hasNext() && itPlayers.hasNext()) {
			GSG_SNF game = itGames.next();
			ArrayList<Integer> players = itPlayers.next();
			System.out.println(game);
			System.out.println(players);
		}
		return null;
	}
}