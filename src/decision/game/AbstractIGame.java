package decision.game;

import java.util.Iterator;

import decision.game.profile.ActionProfile;
import decision.game.profile.ActionProfile.ActionProfileIterator;
import decision.game.profile.IActionProfile;
import decision.game.profile.Profile;

public abstract class AbstractIGame<U> implements IGame<U> {

	@Override
	public abstract int nPlayers();

	@Override
	public abstract Profile<Integer> nActions();

	@Override
	public abstract int nWorlds();

	@Override
	public abstract Profile<U> utility(ActionProfile p, int world);
	
	@Override
	public Profile<U> iUtility(IActionProfile iprofile, int world) {
		ActionProfile p = new ActionProfile();
		for (int i = 0 ; i < this.nPlayers() ; i++) {
			p.add(iprofile.get(i).get(this.signal(i, world)));
		}
		return this.utility(p, world);
	}

	@Override
	public abstract int nValues();

	/**
	 * G1.equals(G2) iff G1 and G2 are CGames with the same number of players, 
	 * of actions per player and with the same utility for each action profile
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof IGame<?>) {
			IGame<?> g = (IGame<?>) o;
			if (this.nWorlds() != g.nWorlds()) {
				//System.out.println("NotEqual: differ on nWorlds: " + this.nWorlds() + " != " + g.nWorlds());
				return false;
			}
			if (this.nPlayers() != g.nPlayers()) {
				//System.out.println("NotEqual: differ on nPlayers: " + this.nPlayers() + " != " + g.nPlayers());
				return false;
			}
			for (int i = 0 ; i < this.nPlayers() ; i++) {
				for (int w = 0 ; w < this.nWorlds() ; w++) {
					if (this.signal(i,w) != g.signal(i, w)) {
						return false;
					}
				}
			}
			if (!this.nActions().equals(g.nActions())) {
				//System.out.println("NotEqual: differ on nActions: " + this.nActions() + " != " + g.nActions());
				return false;
			}
			Iterator<ActionProfile> it = new ActionProfileIterator(this.nActions());
			while (it.hasNext()) {
				ActionProfile p = it.next();
				for (int w = 0 ; w < this.nWorlds() ; w++) {
					Profile<U> u1 = this.utility(p,w);
					Profile<?> u2 = g.utility(p,w);
					if (!u1.equals(u2)) {
						//System.out.println("NotEqual: differ on utility for " + p + ", " + w + ": " + u1 + " != " + u2);
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

}
