package Methods;

import java.util.ArrayList;
import java.util.List;

public class Captor extends MethodForDefender {
	
	int fine;

	/**
	 * @param fine
	 */
	public Captor(int fine) {
		this.fine = fine;
	}

	@Override
	public float computeVal(ArrayList<Integer> playersAction, float[] lambda, int nb_attacker, int indexPlayer) {
		
		int playerAction = playersAction.get(indexPlayer);
		List<Integer> attackersAction = playersAction.subList(0, nb_attacker);
		List<Integer> defendersAction = playersAction.subList(nb_attacker, playersAction.size());
		
		int nb_att = 0;
		int nb_def = 0;
		for (int act : attackersAction) {
			if ( act == playerAction) {
				nb_att++;
			}
		}
		for (int act : defendersAction) {
			if ( act == playerAction) {
				nb_def++;
			}
		}
		return (nb_att*this.fine)/nb_def;
	}

}
