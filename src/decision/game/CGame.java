package decision.game;

import decision.game.profile.ActionProfile;
import decision.game.profile.Profile;

/**
 * Games of Complete Information
 * @author Pierre Pomeret-Coquot
 * @param <U> Type for utility values
 */
public interface CGame<U> {
	
	/**
	 * @return Number of players
	 */
	public abstract int nPlayers();
	
	/**
	 * @return Action profile where i-th component is the number of actions of Player i
	 */
	public abstract Profile<Integer> nActions();
	
	/**
	 * @param p ActionProfile to evaluate
	 * @return Utility profile where the i-th component is the utility of Player i
	 */
	public abstract Profile<U> utility(ActionProfile p);
	
	/**
	 * @return Cost of the representation = number of values stored in the utility "matrix"
	 */
	public int nValues();
	
	/**
	 * @param o The other object to compare with
	 * @return true iff o is a CGame with the same number of player, of action per player 
	 * with the same utility for each action profile (not enforced)
	 */
	@Override
	public boolean equals(Object o);
	
}
