package decision.utility;

import java.util.Iterator;
import java.util.Vector;

/**
 * Lexicographic comparator for T-profiles
 * @author Pierre Pomeret-Coquot
 * @param <T> Ordered type of profiles' components
 */
public class LexicoCmp<T> extends Cmp<Vector<T>>{
	
	private Cmp<T> cmp;
	
	/**
	 * Instantiate a lexicographic comparator
	 * @param cmp Comparator for profiles' components
	 */
	public LexicoCmp(Cmp<T> cmp) {
		this.cmp = cmp;
	}

	@Override
	public boolean preceq(Vector<T> o1, Vector<T> o2) {
		if (o1.size() != o2.size()) {
			return false;
		}
		Iterator<T> it1 = o1.iterator();
		Iterator<T> it2 = o2.iterator();
		while (it1.hasNext() && it2.hasNext()) {
			T t1 = it1.next();
			T t2 = it2.next();
			if (!this.cmp.preceq(t1, t2)) {
				return false;
			}
		}
		return true;
	}

}
