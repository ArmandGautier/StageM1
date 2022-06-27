package decision.utility;

public class IntegerCmp extends Cmp<Integer> {

	@Override
	public boolean preceq(Integer i, Integer j) {
		return i <= j;
	}

}
