package decision.game;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import decision.game.profile.ActionProfile;
import decision.game.profile.ActionProfile.ActionProfileIterator;
import decision.game.profile.Profile;

public abstract class CGame<U> {
	
	public abstract int nPlayers();
	
	public abstract Profile<Integer> nActions();
	
	public abstract Profile<U> utility(ActionProfile p);
	
	public Map<ActionProfile,Profile<U>> utilityMap() {
		Map<ActionProfile,Profile<U>> u = new HashMap<>();
		Iterator<ActionProfile> it = new ActionProfileIterator(this.nActions());
		while (it.hasNext()) {
			ActionProfile p = it.next();
			u.put(p, this.utility(p));
		}
		return u;
	}
	
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
