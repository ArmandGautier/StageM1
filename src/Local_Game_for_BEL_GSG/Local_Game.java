package Local_Game_for_BEL_GSG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Games.BEL_GSG;
import Tools.Node;
import Tools.Profile;

public abstract class Local_Game {
	
	/**
	 * the list of player who plays in at least omega that belongs to focal_elt
	 */
	protected ArrayList<Node> listPlayer = new ArrayList<Node>();
	/**
	 * for each omega that belongs to focal_elt, give a list of boolean to know if player's plays or not in omega
	 */
	protected ArrayList<ArrayList<Boolean>> play_in_omega = new ArrayList<ArrayList<Boolean>>();
	/**
	 * dimension of the local_game
	 */
	protected int local_dimension;
	/**
	 * the index of the focal_elt corresponding to this local game
	 */
	protected int indexOfFocalElt;
	/**
	 * list of possible_actions for player of this local game
	 */
	protected ArrayList<ArrayList<Integer>> possibleActions = new ArrayList<ArrayList<Integer>>();
	/**
	 * representation of the game with profiles and utility value
	 */
	protected Map<Profile,float[]> matrixOfGame = new HashMap<>();
	
	/**
	 * @param focal_elt
	 * @param indexOf_focal_elt
	 */
	public Local_Game(int indexOfFocalElt) {
		this.indexOfFocalElt = indexOfFocalElt;
		this.local_dimension = 1;
	}
	
	/**
	 * @param player
	 * @param actions
	 */
	public void addPlayer(Node player, ArrayList<Integer> actions) {
		this.listPlayer.add(player);
		this.possibleActions.add(actions);
	}
	
	/**
	 * @param play_in_omega the play_in_omega to set
	 */
	public void setPlay_in_omega(ArrayList<ArrayList<Boolean>> play_in_omega) {
		this.play_in_omega = play_in_omega;
	}

	/**
	 * @return the list_player
	 */
	public ArrayList<Node> getListPlayer() {
		return listPlayer;
		
	}
	
	/**
	 * @return the matrixOfGame
	 */
	public Map<Profile, float[]> getMatrixOfGame() {
		return matrixOfGame;
	}
	
	/**
	 * @return the play_in_omega
	 */
	public ArrayList<ArrayList<Boolean>> getPlay_in_omega() {
		return play_in_omega;
	}

	/**
	 * @return the indexOfFocalElt
	 */
	public int getIndexOfFocalElt() {
		return indexOfFocalElt;
	}
	
	/**
	 * @param player
	 * @return true if the player play in this local game, else otherwise
	 */
	public boolean playIn(Node player) {
		for (Node n : this.listPlayer) {
			if (player.equals(n)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param parentGame
	 * compute utilies of the local game
	 */
	public abstract void computeUtilities(BEL_GSG parentGame);
	
	/**
	 * @return the list of omegas concerned by this local game
	 */
	public abstract int[][] getOmegas();

}
