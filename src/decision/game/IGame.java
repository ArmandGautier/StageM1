package decision.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import decision.game.profile.ActionProfile;
import decision.game.profile.ActionProfile.ActionProfileIterator;
import decision.game.profile.Profile;

public abstract class IGame<U> {
	
	public abstract int nPlayers();
	
	public abstract Vector<Integer> nActions();
	
	public abstract int nStates();
	
	public abstract Profile<U> utility(ActionProfile p, int State);
	
	public List<Map<ActionProfile,Profile<U>>> utilityMap() {
		List<Map<ActionProfile,Profile<U>>> u = new ArrayList<>();
		for (int t = 0 ; t < this.nStates() ; t++) {
			Map<ActionProfile,Profile<U>> u_t = new HashMap<>();
			Iterator<ActionProfile> it = new ActionProfileIterator(this.nActions());
			while (it.hasNext()) {
				ActionProfile p = it.next();
				u_t.put(p, this.utility(p,t));
			}
			u.add(u_t);
		}
		return u;
	}
	
	public String toString() {
		String s = "";
		Iterator<ActionProfile> it;
		for (int t = 0 ; t < this.nStates() ; t++) {
			it = new ActionProfileIterator(this.nActions());
			while (it.hasNext()) {
				ActionProfile p = it.next();
				s = s + "u( " + p + ", " + t + " )\t= " + this.utility(p,t) + "\n";
			}
		}
		return s;
	}

}
