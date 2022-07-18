package decision.game;

import decision.game.profile.ActionProfile;
import decision.game.profile.IActionProfile;
import decision.game.profile.Profile;

/**
 * Games of Incomplete Information
 * @author Pierre Pomeret-Coquot
 * @param <U> Type for utility values
 */
public interface IGame<U> {
	
	/**
	 * @return Number of players
	 */
	public int nPlayers();
	
	/**
	 * @return Action profile where i-th component is the number of actions of Player i
	 */
	public Profile<Integer> nActions();
	
	/**
	 * @return Number of ``states of the world''
	 */
	public int nWorlds();
	
	/**
	 * @return Profile where i-th component is the number of types of player i
	 */
	public Profile<Integer> nTypes();
	
	/**
	 * The signal that a player receive in a given state of the world (= Harsanyi's type)
	 * @param player Player
	 * @param world State of the world
	 * @return Signal that player receive in the given state of the world
	 */
	public int signal(int player, int world);
	
	/**
	 * @param p ActionProfile to evaluate
	 * @param world State of the world
	 * @return Utility profile where the i-th component is the utility of Player i
	 */
	public abstract Profile<U> utility(ActionProfile profile, int world);
	
	/**
	 * @param p IActionProfile to evaluate
	 * @param world State of the world
	 * @return Utility profile where the i-th component is the utility of Player i
	 */
	public abstract Profile<U> iUtility(IActionProfile iprofile, int world);
	
	/**
	 * @return Cost of the representation = number of values stored in the utility "matrix"
	 */
	public int nValues();
	
	/**
	 * @param o The other object to compare with
	 * @return true iff o is a IGame with the same number of player, of action per player, of worlds 
	 * and with the same utility for each action profile (not enforced)
	 */
	@Override
	public boolean equals(Object o);
	
}