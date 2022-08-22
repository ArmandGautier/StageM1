package Methods;

import java.util.ArrayList;
import java.util.List;

public class Corrupt extends MethodForDefender {

	int bribe;

	/**
	 * @param fine
	 */
	public Corrupt(int bribe) {
		this.bribe = bribe;
	}

	@Override
	public float computeVal(ArrayList<Integer> playersAction, float[] lambda, int nb_attacker, int indexPlayer) {
		
		int playerAction = playersAction.get(indexPlayer);
		List<Integer> attackersAction = playersAction.subList(0, nb_attacker);
		
		int nb_att = 0;
		for (int act : attackersAction) {
			if ( act == playerAction) {
				nb_att++;
			}
		}
		return (nb_att*this.bribe);
	}
	
}
