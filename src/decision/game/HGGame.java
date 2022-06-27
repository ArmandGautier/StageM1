package decision.game;

import java.util.List;
import java.util.Map.Entry;
import decision.game.profile.ActionProfile;
import decision.game.profile.Profile;
import decision.utility.oplus.Oplus;

public abstract class HGGame<U> extends CGame<U> {
	
	public abstract Oplus<U> oplus();
		
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

}
