package decision.utility;

public class FloatCmp extends Cmp<Float> {

	@Override
	public boolean preceq(Float i, Float j) {
		return i <= j;
	}

}
