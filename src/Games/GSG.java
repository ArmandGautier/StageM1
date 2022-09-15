package Games;

import java.util.ArrayList;

import Methods.MethodForAttacker;
import Methods.MethodForDefender;
import Tools.Profile;

public abstract class GSG extends Game {
	
	/**
	 * the number of attacker in the game
	 */
	protected int nb_attacker;
	
	/**
	 * the number of defender in the game
	 */
	protected int nb_defender;
	
	/**
	 * the number of player in the game
	 */
	protected int nb_player;
	
	/**
	 * a list of possible actions for every player
	 */
	protected ArrayList<ArrayList<Integer>> possibleActions;
	
	/**
	 * Method to compute utility for attackers
	 */
	protected MethodForAttacker attMethod;
	
	/**
	 * Method to compute utility for defenders
	 */
	protected MethodForDefender defMethod;

	
	/**
	 * @param nb_attacker
	 * @param nb_defender
	 * @param lambda
	 * @param possibleActions
	 * @param attMethod
	 * @param defMethod
	 */
	public GSG(int nb_attacker, int nb_defender, ArrayList<ArrayList<Integer>> possibleActions, MethodForAttacker attMethod, MethodForDefender defMethod) {
		this.nb_attacker = nb_attacker;
		this.nb_defender = nb_defender;
		this.nb_player = nb_attacker+nb_defender;
		this.possibleActions = possibleActions;
		this.attMethod = attMethod;
		this.defMethod = defMethod;
	}

	/**
	 * @return the nb_attacker
	 */
	public int getNb_attacker() {
		return nb_attacker;
	}

	/**
	 * @return the nb_defender
	 */
	public int getNb_defender() {
		return nb_defender;
	}

	/**
	 * @return the nb_player
	 */
	public int getNb_player() {
		return nb_player;
	}

	/**
	 * @return the possible_actions
	 */
	public ArrayList<ArrayList<Integer>> getPossibleActions() {
		return possibleActions;
	}
	
	/**
	 * compute utilities of the game
	 */
	public abstract void computeUtilities();
	
	/**
	 * @param profile
	 * @param joueur
	 * @return the utility of a given player for a given Profile
	 */
	public abstract float getUtility(Profile profile, int joueur);
	
	/**
	 * @param filename
	 * write profiles and corresponding utilities in the file "filename"
	 */
	public abstract void writeInFile(String filename);
	
}
