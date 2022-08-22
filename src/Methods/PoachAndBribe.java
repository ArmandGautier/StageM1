package Methods;

import java.util.ArrayList;
import java.util.List;

public class PoachAndBribe extends MethodForAttacker {
	
	int bribe;

	/**
	 * @param bribe
	 */
	public PoachAndBribe(int bribe) {
		this.bribe = bribe;
	}

	@Override
	public float computeVal(ArrayList<Integer> playersAction, float[] lambda, int nb_attacker, int indexPlayer) {
		
		int playerAction = playersAction.get(indexPlayer);
		List<Integer> defendersAction = playersAction.subList(nb_attacker, playersAction.size());
		
		int nb_def = 0;
		for (int act : defendersAction) {
			if ( act == playerAction) {
				nb_def++;
			}
		}
		return lambda[playerAction] - this.bribe*nb_def;
	}

}
