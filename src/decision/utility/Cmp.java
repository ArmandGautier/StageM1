package decision.utility;

/**
 * Comparator for utility values
 * @author Pierre Pomeret-Coquot
 * @param <U> Utility domain
 */
public abstract class Cmp<U> {
	
	/**
	 * Precedence
	 * @param o1 First value
	 * @param o2 Second value
	 * @return true iff o1 precedes o2 (not strict)
	 */
	public abstract boolean preceq(U o1, U o2);
	
	/**
	 * Strict precedence
	 * @param o1 First value
	 * @param o2 Second value
	 * @return true iff o1 strictly precedes o2
	 */
	public boolean prec(U o1, U o2) {
		return this.preceq(o1, o2) && !this.preceq(o2, o1);
	}
	
	/**
	 * Succession
	 * @param o1 First value
	 * @param o2 Second value
	 * @return true iff o1 succeeds o2
	 */
	public boolean succeq(U o1, U o2) {
		return this.preceq(o2, o1);
	}
	
	/**
	 * Strict succession
	 * @param o1 First value
	 * @param o2 Second value
	 * @return true iff o1 strictly succeeds o2
	 */
	public boolean succ(U o1, U o2) {
		return this.preceq(o2, o1) && !this.preceq(o1, o2);
	}
	
	/**
	 * Similarity
	 * @param o1 First value
	 * @param o2 Second value
	 * @return true iff o1 and o2 are similar (both values precede the other)
	 */
	public boolean sim(U o1, U o2) {
		return this.preceq(o1, o2) && this.succeq(o1, o2);
	}
	
	/**
	 * incomparability
	 * @param o1 First value
	 * @param o2 Second value
	 * @return true iff o1 and o2 are incomparable (neither value precede the other)
	 */
	public boolean incomp(U o1, U o2) {
		return !this.preceq(o1, o2) && !this.preceq(o2, o1);
	}
	
	

}
