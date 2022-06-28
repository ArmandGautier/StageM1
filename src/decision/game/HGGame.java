package decision.game;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import decision.game.profile.ActionProfile;
import decision.game.profile.Profile;
import decision.game.profile.ActionProfile.ActionProfileIterator;
import decision.utility.oplus.Oplus;

/**
 * Hypergraphical Game
 * @author Pierre Pomeret-Coquot
 * @param <U> Type for utility values
 */
public abstract class HGGame<U> implements CGame<U> {
	
	/**
	 * @return Aggregation operator (to aggregate local utility values in a single global utility value
	 */
	public abstract Oplus<U> oplus();
		
	/**
	 * @return List of all local games
	 */
	public abstract List<LocalGame<U>> localGames();

	@Override
	public int nPlayers() {
		int n = 0;
		for (LocalGame<U> g : this.localGames()) {
			for (int i : g.playersID().values()) {
				if (i > n) {
					n = i;
				}
			}
		}
		return n + 1;
	}

	@Override
	public Profile<Integer> nActions() {
		Profile<Integer> nActions = new Profile<>();
		for (int i = 0 ; i < this.nPlayers() ; i++) {
			nActions.add(0);
		}
		for (LocalGame<U> g : this.localGames()) {
			for (Entry<Integer,Integer> e : g.playersID().entrySet()) {
				nActions.set(e.getValue(),g.nActions().get(e.getKey()));
			}
		}
		return nActions;
	}

	@Override
	public Profile<U> utility(ActionProfile p) {
		Profile<U> u = new Profile<>();
		// Initialize utility profile to (0, 0, ...)
		for (int i = 0 ; i < this.nPlayers() ; i++) {
			u.add(this.oplus().zero());
		}
		// Add local utility for each local game
		for (LocalGame<U> g : this.localGames()) {
			// Restrict the profile p to the local players only
			ActionProfile localP = new ActionProfile();
			for (int i = 0 ; i < g.nPlayers() ; i++) {
				localP.add(p.get(g.playerID(i)));
			}
			Profile<U> localU = g.utility(localP);
			for (Entry<Integer,Integer> e : g.playersID().entrySet()) {
				u.set(e.getValue(), this.oplus().oplus(u.get(e.getValue()),localU.get(e.getKey())));
			}
		}
		return u;
	}
	
	@Override
	public int nValues() {
		int n = 0;
		for (LocalGame<U> lg : this.localGames()) {
			n += lg.nValues();
		}
		return n;
	}
	
	@Override
	public String toString() {
		String s = "";
		for (LocalGame<U> lg : this.localGames()) {
			s = s + lg;
		}
		return s;
	}

	/**
	 * G1.equals(G2) iff G1 and G2 are CGames with the same number of players, 
	 * of actions per player and with the same utility for each action profile
	 */
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
