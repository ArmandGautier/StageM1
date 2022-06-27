package decision.utility.oplus;

/**
 * Natural addition for integers
 * @author Pierre Pomeret-Coquot
 *
 */
public class IntegerOplus extends Oplus<Integer> {

	@Override
	public Integer oplus(Integer o1, Integer o2) {
		return o1 + o2;
	}

	@Override
	public Integer zero() {
		return 0;
	}

}
