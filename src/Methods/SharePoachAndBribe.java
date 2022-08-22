package Methods;

import java.util.ArrayList;
import java.util.List;

public class SharePoachAndBribe extends MethodForAttacker {
	
	int bribe;

	/**
	 * @param bribe
	 */
	public SharePoachAndBribe(int bribe) {
		this.bribe = bribe;
	}

	@Override
	public float computeVal(ArrayList<Integer> playersAction, float[] lambda, int nb_attacker, int indexPlayer) {
		
		int playerAction = playersAction.get(indexPlayer);
		List<Integer> attackersAction = playersAction.subList(0, nb_attacker);
		List<Integer> defendersAction = playersAction.subList(nb_attacker, playersAction.size());
		
		int nb_def = 0;
		for (int act : defendersAction) {
			if ( act == playerAction) {
				nb_def++;
			}
		}
		
		int nb_att = 0;
		for (int act : attackersAction) {
			if ( act == playerAction) {
				nb_att++;
			}
		}
		return lambda[playerAction]/nb_att - nb_def*this.bribe;
	}

}
