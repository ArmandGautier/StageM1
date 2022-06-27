package decision.utility.oplus;

/**
 * Natural addition for floats
 * @author Pierre Pomeret-Coquot
 *
 */
public class FloatOplus extends Oplus<Float> {

	@Override
	public Float oplus(Float o1, Float o2) {
		return o1 + o2;
	}

	@Override
	public Float zero() {
		return (float) 0.0;
	}

}
