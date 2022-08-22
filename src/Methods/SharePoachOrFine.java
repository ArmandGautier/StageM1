package Methods;

import java.util.ArrayList;
import java.util.List;

public class SharePoachOrFine extends MethodForAttacker {
	
	int fine;

	/**
	 * @param fine
	 */
	public SharePoachOrFine(int fine) {
		this.fine = fine;
	}

	@Override
	public float computeVal(ArrayList<Integer> playersAction, float[] lambda, int nb_attacker, int indexPlayer) {
		
		int playerAction = playersAction.get(indexPlayer);
		List<Integer> attackersAction = playersAction.subList(0, nb_attacker);
		List<Integer> defendersAction = playersAction.subList(nb_attacker, playersAction.size());
		
		if (defendersAction.contains(playerAction)) {
			return - this.fine;
		}
		else {
			int nb_att = 0;
			for (int act : attackersAction) {
				if ( act == playerAction) {
					nb_att++;
				}
			}
			return lambda[playerAction]/nb_att;
		}
	}

}
