package Methods;

import java.util.ArrayList;
import java.util.List;

public class PoachOrFine extends MethodForAttacker {

	int fine;

	/**
	 * @param fine
	 */
	public PoachOrFine(int fine) {
		this.fine = fine;
	}
	
	@Override
	public float computeVal(ArrayList<Integer> playersAction, float[] lambda, int nb_attacker, int indexPlayer) {
		
		int playerAction = playersAction.get(indexPlayer);
		List<Integer> defendersAction = playersAction.subList(nb_attacker, playersAction.size());
		
		if (defendersAction.contains(playerAction)) {
			return - this.fine;
		}
		else {
			return lambda[playerAction];
		}
	}

}
