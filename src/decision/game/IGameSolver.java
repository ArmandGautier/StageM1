package decision.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import decision.game.profile.ActionProfile;
import decision.game.profile.ActionProfile.ActionProfileIterator;
import decision.game.profile.IActionProfile;
import decision.game.profile.Profile;
import decision.utility.Cmp;



/**
 * Tool for studying and solving an IGame
 * @author Pierre Pomeret-Coquot
 * @param <U> Type for utility values
 */
public class IGameSolver<U> {
	
	private Cmp<U> cmp;

	/**
	 * Instantiate an IGameSolver
	 * @param cmp Comparator for U-typed utility values
	 */
	public IGameSolver(Cmp<U> cmp) {
		this.cmp = cmp;
	}
	
	/**
	 * Best response
	 * @param g IGame to solve
	 * @param p Action profile to consider
	 * @param i Player to consider
	 * @return true iff p_i is a best response to p_{-i} 
	 */
	/*
	public boolean isBestResponse(IGame<U> g, IActionProfile iprofile, int i) {
		Profile<U> u = g.utility(iprofile);
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
	*/
	/**
	 * Nash equilibrium
	 * @param g CGame to consider
	 * @param p Action profile to consider
	 * @return true iff p is a Nash equilibrium i.e. forall i, p_i is a best response to p_{-i}
	 */
	/*
	public boolean isNashEquilibrium(CGame<U> g, ActionProfile p) {
		for (int i = 0 ; i < g.nPlayers() ; i++) {
			if (!this.isBestResponse(g, p, i)) {
				return false;
			}
		}
		return true;
	}
	*/
	/**
	 * All pure Nash equilibria
	 * @param g CGame to consider
	 * @param verbose If true, prints all profiles and for each profile p, either a better response for a player (if any) or that p is a Nash equilibrium
	 * @return List of all pure Nash equilibria
	 */
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
	
	/**
	 * All pure Nash equilibria
	 * @param g CGame to consider
	 * @return list of all pure Nash equilibria
	 */
	public List<ActionProfile> allNashEquilibria(CGame<U> g) {
		return this.allNashEquilibria(g, false);
	}
	
}
