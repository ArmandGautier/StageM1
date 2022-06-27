package decision.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import decision.game.profile.ActionProfile;
import decision.game.profile.Profile;
import decision.game.profile.ActionProfile.ActionProfileIterator;
import decision.utility.Cmp;



public class CGameSolver<U> {
	
	private Cmp<U> cmp;

	public CGameSolver(Cmp<U> cmp) {
		this.cmp = cmp;
	}
	
	public boolean isBestResponse(CGame<U> g, ActionProfile p, int i) {
		Profile<U> u = g.utility(p);
		for (int a_i = 0 ; a_i < g.nActions().get(i) ; a_i++) {
			if (a_i != p.get(i)) {
				Profile<U> u2 = g.utility(p.moveTo(i, a_i));
				if (this.cmp.prec(u.get(i), u2.get(i))) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean isNashEquilibrium(CGame<U> g, ActionProfile p) {
		for (int i = 0 ; i < g.nPlayers() ; i++) {
			if (!this.isBestResponse(g, p, i)) {
				return false;
			}
		}
		return true;
	}
	
	public List<ActionProfile> allNashEquilibria(CGame<U> g, boolean verbose) {
		List<ActionProfile> allNashEq = new ArrayList<>();
		Vector<Integer> nActions = g.nActions();
		Iterator<ActionProfile> it = new ActionProfileIterator(nActions);
		while (it.hasNext()) {
			ActionProfile p = it.next();
			Profile<U> u = g.utility(p);
			boolean isNash = true;
			if (verbose) {
				System.out.print("u( " + p + " )\t= " + u);
			}
			for (int i = 0 ; i < g.nPlayers() ; i++) {
				for (int a_i = 0 ; a_i < nActions.get(i) ; a_i++) {
					if (a_i != p.get(i)) {
						ActionProfile p2 = p.moveTo(i, a_i);
						Profile<U> u2 = g.utility(p2);
						if (this.cmp.prec(u.get(i), u2.get(i))) {
							if (verbose) {
								System.out.println("  \t" + i + " better moves to " + p2);
							}
							isNash = false;
							break;
						}
					}
				}
				if (!isNash) {
					break;
				}
			}
			if (isNash) {
				allNashEq.add(p);
				if (verbose) {
					System.out.println("  \tNash equilibrium");
				}
			}
		}
		return allNashEq;
	}
	
	public List<ActionProfile> allNashEquilibria(CGame<U> g) {
		return this.allNashEquilibria(g, false);
	}
	
}
