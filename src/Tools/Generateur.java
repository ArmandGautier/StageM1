package Tools;

import java.util.ArrayList;
import java.util.Random;

import Games.BEL_GSG;
import Games.GSG_SNF;
import Methods.MethodForAttacker;
import Methods.MethodForDefender;
import Transformations.Transformation;
import XEUs.XEU;

public class Generateur {
	
	/**
	 * @param nbPlayer
	 * @param nbActions
	 * @param attMethod
	 * @param defMethod
	 * @return a GSG in SNF with random value for the actions and a random repartition of the number of attacker/defender
	 */
	public GSG_SNF generate_GSG_SNF(int nbPlayer, int nbActions, MethodForAttacker attMethod, MethodForDefender defMethod) {
		ArrayList<ArrayList<Integer>> possibleActions = new ArrayList<ArrayList<Integer>>();
		Random random = new Random();
		for (int i=0; i<nbPlayer; i++) {
			possibleActions.add(generateAction(nbActions));
		}
		int b_inf = nbPlayer/4 + 1;
		int b_sup = nbPlayer-b_inf;
		int nb_braco = random.nextInt(b_sup+1-b_inf)+b_inf;
		int nb_gchasse = nbPlayer - nb_braco;
		float[] valActions = generateValActions(nbActions);
		GSG_SNF gsg = new GSG_SNF(nb_braco, nb_gchasse, valActions, possibleActions, attMethod, defMethod);
		gsg.computeUtilities();
		return gsg;
	}
	
	private ArrayList<Integer> generateAction(int nb_act) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		for (int i=0; i<nb_act; i++) {
			res.add(i);
		}
		return res;
	}

	private float[] generateValActions(int nb_act) {
		float[] res = new float[nb_act];
		Random random = new Random();
		for (int i=0; i<nb_act; i++) {
			res[i] = random.nextInt(10);
		}
		return res;
	}
	
	public Universe generateUniverseForBel_Gsg(int nb_location, int nb_herd, int nb_attacker, int nb_defender) {
		Universe universe = new Universe(nb_location,nb_herd,nb_attacker,nb_defender);
		return universe;
	}
	
	public BEL_GSG generate_BEL_GSG(Universe universe, MethodForAttacker attMethod, MethodForDefender defMethod, XEU xeu, Transformation transfo) {
		BEL_GSG game = new BEL_GSG(universe.getNb_attacker(), universe.getNb_defender(), attMethod, defMethod, universe.getPossible_actions(), universe.getHerd_value(), universe.getNb_location(), universe.getFocal_elements(), universe.getMass_function(), universe.getSee_function(), universe.getGps(), xeu, transfo);
		return game;
	}

}
