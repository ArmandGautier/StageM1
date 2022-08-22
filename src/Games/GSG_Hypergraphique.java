package Games;

import java.util.ArrayList;

import Methods.MethodForAttacker;
import Methods.MethodForDefender;
import Tools.Profile;

public class GSG_Hypergraphique extends GSG {

	public GSG_Hypergraphique(int nb_attacker, int nb_defender, ArrayList<ArrayList<Integer>> possibleActions, MethodForAttacker attMethod, MethodForDefender defMethod) {
		super(nb_attacker, nb_defender, possibleActions, attMethod, defMethod);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void computeUtilities() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getUtility(Profile profile, int joueur) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeInFile(String filename) {
		// TODO Auto-generated method stub
		
	}

}
