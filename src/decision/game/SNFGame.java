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
public abstract class SNFGame<U> extends AbstractCGame<U> {
	
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
	
}
