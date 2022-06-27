package decision.utility;

/**
 * Natural comparator for integers
 * @author Pierre Pomeret-Coquot
 *
 */
public class IntegerCmp extends Cmp<Integer> {

	@Override
	public boolean preceq(Integer i, Integer j) {
		return i <= j;
	}

}
