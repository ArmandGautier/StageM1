package GSG;

import java.util.ArrayList;
import java.util.Random;

public class Generateur {

	public ArrayList<GSG_SNF> generate_GSG_SNF(int nb_player, int nb_actions, int nb_jeux, String utilite_braco, String utilite_gc) {
		ArrayList<GSG_SNF> gsgs = new ArrayList<GSG_SNF>();
		int[] ressources = generate_ressource(nb_actions);
		Random random = new Random();
		for (int j=1; j<=nb_jeux; j++) {
			int b_inf = nb_player/4 + 1;
			int b_sup = nb_player-b_inf;
			int nb_braco = random.nextInt(b_sup+1-b_inf)+b_inf;
			int nb_gchasse = nb_player - nb_braco;
			int fine_or_bribe = random.nextInt(5);
			float[] val_ressources = generate_val_ressource(nb_actions);
			GSG_SNF gsg = new GSG_SNF(nb_braco, nb_gchasse, utilite_braco, utilite_gc, val_ressources, ressources, fine_or_bribe);
			gsg.calcul_val();
			gsgs.add(gsg);
		}
		return gsgs;
	}
	
	private int[] generate_ressource(int nb_act) {
		int[] res = new int[nb_act];
		for (int i=0; i<nb_act; i++) {
			res[i] = i;
		}
		return res;
	}

	private float[] generate_val_ressource(int nb_act) {
		float[] res = new float[nb_act];
		Random random = new Random();
		for (int i=0; i<nb_act; i++) {
			res[i] = random.nextInt(10);
		}
		return res;
	}
	
	public ArrayList<Universe> generateUniverseForBel_Gsg(int nb_location, int nb_herd, int nb_attacker, int nb_defender, int nb_jeux) {
		ArrayList<Universe> res = new ArrayList<Universe>();
		for (int j=0; j<nb_jeux; j++) {
			Universe game = new Universe(nb_location,nb_herd,nb_attacker,nb_defender);
			res.add(game);
		}
		return res;
	}
	
	// if method == JEU
	
	public ArrayList<Bel_GSG_SNF> generate_Bel_GSG_SNF(ArrayList<Universe> universes, String attacker_utility, String defender_utility, String method, float[] alpha) {
		ArrayList<Bel_GSG_SNF> res = new ArrayList<Bel_GSG_SNF>();
		Random random = new Random();
		for (Universe universe : universes) {
			int fine_or_bribe = random.nextInt(5);
			Bel_GSG_SNF game = new Bel_GSG_SNF(universe.getNb_attacker(), universe.getNb_defender(), attacker_utility, defender_utility, universe.getPossible_actions(), universe.getHerd_value(), fine_or_bribe, universe.getNb_location(), universe.getFocal_elements(), universe.getMass_function(), method, alpha, universe.getGps());
			game.calcul_val();
			res.add(game);
		}
		return res;
	}
	
	public ArrayList<Bel_GSG_Direct_Transform> generate_Bel_GSG_Direct_Transform(ArrayList<Universe> universes, String attacker_utility, String defender_utility, String method, float[] alpha) {
		ArrayList<Bel_GSG_Direct_Transform> res = new ArrayList<Bel_GSG_Direct_Transform>();
		Random random = new Random();
		for (Universe universe : universes) {
			int fine_or_bribe = random.nextInt(5);
			Bel_GSG_Direct_Transform game = new Bel_GSG_Direct_Transform(universe.getNb_attacker(), universe.getNb_defender(), attacker_utility, defender_utility, universe.getPossible_actions(), universe.getHerd_value(), fine_or_bribe, universe.getNb_location(), universe.getFocal_elements(), universe.getMass_function(), method, alpha, universe.getGps());
			game.calcul_val();
			res.add(game);
		}
		return res;
	}
	
	// if method != JEU
	
	public ArrayList<Bel_GSG_SNF> generate_Bel_GSG_SNF(ArrayList<Universe> universes, String attacker_utility, String defender_utility, String method) {
		ArrayList<Bel_GSG_SNF> res = new ArrayList<Bel_GSG_SNF>();
		Random random = new Random();
		for (Universe universe : universes) {
			int fine_or_bribe = random.nextInt(5);
			Bel_GSG_SNF game = new Bel_GSG_SNF(universe.getNb_attacker(), universe.getNb_defender(), attacker_utility, defender_utility, universe.getPossible_actions(), universe.getHerd_value(), fine_or_bribe, universe.getNb_location(), universe.getFocal_elements(), universe.getMass_function(), method, universe.getGps());
			game.calcul_val();
			res.add(game);
		}
		return res;
	}
	
	public ArrayList<Bel_GSG_Direct_Transform> generate_Bel_GSG_Direct_Transform(ArrayList<Universe> universes, String attacker_utility, String defender_utility, String method) {
		ArrayList<Bel_GSG_Direct_Transform> res = new ArrayList<Bel_GSG_Direct_Transform>();
		Random random = new Random();
		for (Universe universe : universes) {
			int fine_or_bribe = random.nextInt(5);
			Bel_GSG_Direct_Transform game = new Bel_GSG_Direct_Transform(universe.getNb_attacker(), universe.getNb_defender(), attacker_utility, defender_utility, universe.getPossible_actions(), universe.getHerd_value(), fine_or_bribe, universe.getNb_location(), universe.getFocal_elements(), universe.getMass_function(), method, universe.getGps());
			game.calcul_val();
			res.add(game);
		}
		return res;
	}
}
