package decision.game;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import decision.game.profile.ActionProfile;
import decision.game.profile.ActionProfile.ActionProfileIterator;
import decision.game.profile.Profile;

/**
 * Games of Complete Information
 * @author Pierre Pomeret-Coquot
 * @param <U> Type for utility values
 */
public abstract class CGame<U> {
	
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
	 * @return Map where keys are action profiles and values are utility profiles
	 */
	public Map<ActionProfile,Profile<U>> utilityMap() {
		Map<ActionProfile,Profile<U>> u = new HashMap<>();
		Iterator<ActionProfile> it = new ActionProfileIterator(this.nActions());
		while (it.hasNext()) {
			ActionProfile p = it.next();
			u.put(p, this.utility(p));
		}
		return u;
	}
	
	/**
	 * @return Cost of the representation = number of values stored in the utility "matrix"
	 */
	public int nValues() {
		int n = 1;
		for (int i = 0 ; i < this.nPlayers() ; i++) {
			n = n * this.nActions().get(i);
		}
		return n;
	}
	
	@Override
	public String toString() {
		String s = "";
		Iterator<ActionProfile> it = new ActionProfileIterator(this.nActions());
		while (it.hasNext()) {
			ActionProfile p = it.next();
			s = s + "u( " + p + " )\t= " + this.utility(p) + "\n";
		}
		return s;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof CGame<?>) {
			CGame<?> g = (CGame<?>) o;
			if (this.nPlayers() != g.nPlayers()) {
				//System.out.println("NotEqual: differ on nPlayers: " + this.nPlayers() + " != " + g.nPlayers());
				return false;
			}
			if (!this.nActions().equals(g.nActions())) {
				//System.out.println("NotEqual: differ on nActions: " + this.nActions() + " != " + g.nActions());
				return false;
			}
			Iterator<ActionProfile> it = new ActionProfileIterator(this.nActions());
			while (it.hasNext()) {
				ActionProfile p = it.next();
				Profile<U> u1 = this.utility(p);
				Profile<?> u2 = g.utility(p);
				if (!u1.equals(u2)) {
					//System.out.println("NotEqual: differ on utility for " + p + ": " + u1 + " != " + u2);
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	
	
	
	
	
}
