package decision.utility;

/**
 * Natural comparator for floats
 * @author Pierre Pomeret-Coquot
 *
 */
public class FloatCmp extends Cmp<Float> {

	@Override
	public boolean preceq(Float i, Float j) {
		return i <= j;
	}

}
