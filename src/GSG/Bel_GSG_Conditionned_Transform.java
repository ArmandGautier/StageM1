package GSG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bel_GSG_Conditionned_Transform extends Bel_GSG {
	
	/**
	 * List of local game who are the hypergraphical representation
	 */
	protected ArrayList<Local_Game_for_cTransform> hypergraphical_representation = new ArrayList<Local_Game_for_cTransform>();
	
	Map<Integer, ArrayList<int[]>> profils = new HashMap<>();
	Map<Integer, ArrayList<float[]>> utilites = new HashMap<>();
	ArrayList<ArrayList<Integer>> player_by_game = new ArrayList<ArrayList<Integer>>();
	
	public Bel_GSG_Conditionned_Transform(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility,
			ArrayList<ArrayList<Integer>> possible_actions, int[] herd, int fine_or_bribe, int nb_location,
			ArrayList<int[][]> focal_element, float[] mass_function, String method, int[] seeFunction, int[] gps) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, possible_actions, herd, fine_or_bribe, nb_location,
				focal_element, mass_function, method, seeFunction, gps);
	}
	
	public Bel_GSG_Conditionned_Transform(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility,
			ArrayList<ArrayList<Integer>> possible_actions, int[] herd, int fine_or_bribe, int nb_location,
			ArrayList<int[][]> focal_element, float[] mass_function, String method, float[] alpha, int[] seeFunction, int[] gps) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, possible_actions, herd, fine_or_bribe, nb_location,
				focal_element, mass_function, method, alpha, seeFunction, gps);
	}

	@Override
	public void calcul_val() {
			
		create_snf_gsg();
		create_noeuds();
		
		for ( Node n : this.nodes) {
			int indexFocalElement = 0;
			for ( int[][] fElement : this.focal_element) {
				ArrayList<int[]> omegaPlaysForN = new ArrayList<int[]>();
				for (int[] omega : fElement) {
					if ( this.omegaToTypes(omega, n.getIndexPlayer()).equals(n.getType()) ) {
						omegaPlaysForN.add(omega);
					}
				}
				if (omegaPlaysForN.size() > 0) {
					if ( ! existGame(omegaPlaysForN,indexFocalElement)) {
						Local_Game_for_cTransform l = new Local_Game_for_cTransform(this,omegaPlaysForN,indexFocalElement);
						this.hypergraphical_representation.add(l);
					}
				}
				indexFocalElement++;
			}
		}
		int nbGame = 0;
		for (Local_Game_for_cTransform l : this.hypergraphical_representation) {
			l.calcul_val();
			this.profils.put(nbGame, l.getProfiles());
			this.utilites.put(nbGame, l.getUtilities());
			this.player_by_game.add(l.getListPlayer());
			nbGame++;
		}

	}

	private boolean existGame(ArrayList<int[]> listOmega, int indexFocalElement) {
		for (Local_Game_for_cTransform game : this.hypergraphical_representation) {
			if ( game.indexOf_focal_elt == indexFocalElement && same(game.listOmega,listOmega) ) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the profils
	 */
	public Map<Integer, ArrayList<int[]>> getProfils() {
		return profils;
	}

	/**
	 * @return the utilites
	 */
	public Map<Integer, ArrayList<float[]>> getUtilites() {
		return utilites;
	}

	/**
	 * @return the player_by_game
	 */
	public ArrayList<ArrayList<Integer>> getPlayer_by_game() {
		return player_by_game;
	}

	/**
	 * @return the hypergraphical_representation
	 */
	public ArrayList<Local_Game_for_cTransform> getHypergraphical_representation() {
		return this.hypergraphical_representation;
	}
	
	public void writeInFile(String filename) {
		for (Local_Game_for_cTransform game : this.hypergraphical_representation) {
			game.writeInFile(filename);
		}
	}
	
	// sert juste pour verif
	public float getUtility(int[] profile, Node player) {
		int indexPlayer = 0;
		for ( Node n : this.nodes) {
			if ( player.equals(n)) {
				break;
			}
			else {
				indexPlayer++;
			}
		}
		if (indexPlayer==this.nodes.size()) {
			System.out.println("Ce joueur n'existe pas");
			return 0;
		}
		
		float utility = 0;
		for (Local_Game_for_cTransform l : this.hypergraphical_representation) {
			if (l.playIn(player)) {
				utility += l.getUtility(profile, player);
			}
		}
		return utility;
	}

}
