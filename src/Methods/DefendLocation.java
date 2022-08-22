package Methods;

import java.util.ArrayList;
import java.util.List;

public class DefendLocation extends MethodForDefender {

	@Override
	public float computeVal(ArrayList<Integer> playersAction, float[] lambda, int nb_attacker, int indexPlayer) {
		
		int playerAction = playersAction.get(indexPlayer);
		List<Integer> attackersAction = playersAction.subList(0, nb_attacker);
		
		if (attackersAction.contains(playerAction)) {
			return 1;
		}
		else {
			return 0;
		}
	}

}
