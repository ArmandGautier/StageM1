package Methods;

import java.util.ArrayList;

public class AvoidPoaching extends MethodForDefender {
	
	MethodForDefender defMethod;

	/**
	 * @param defMethod
	 */
	public AvoidPoaching(MethodForDefender defMethod) {
		this.defMethod = defMethod;
	}

	@Override
	public float computeVal(ArrayList<Integer> playersAction, float[] lambda, int nb_attacker, int indexPlayer) {
		
		float val = 0;
		for (int i=0; i<nb_attacker; i++) {
			val -= this.defMethod.computeVal(playersAction, lambda, nb_attacker, i);
		}
		return val;
	}

}
