package decision.utility.oplus;

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
