package Main;

import GSG.GSG;
import GSG.GSG_SNF;
import decision.game.CGame;
import decision.game.CGameSolver;
import decision.game.HGGame;
import decision.game.profile.Profile;
import decision.utility.Cmp;
import decision.utility.FloatCmp;
import decision.utility.IntegerCmp;
import game.factory.CoordinationGame;
import game.factory.HGCoordinationGame;

public class MainTests {

	public static void main(String[] args) {
		
		
		// Tests on profiles
		Integer[] arr1 = {1,2,3};
		Integer[] arr2 = {1,2,3};
		Integer[] arr3 = {1,3,2};
		Profile<Integer> p1 = new Profile<>(arr1);
		Profile<Integer> p2 = new Profile<>(arr2);
		Profile<Integer> p3 = new Profile<>(arr3);
		System.out.println("p1 == p2:\t" + (p1 == p2) + ", " + p1.equals(p2) + ", " + p2.equals(p1));
		System.out.println("p1 == p3:\t" + (p1 == p3) + ", " + p1.equals(p3) + ", " + p3.equals(p1));
		System.out.println("p2 == p3:\t" + (p2 == p3) + ", " + p2.equals(p3) + ", " + p3.equals(p2));

		
		
		
		// Initialize comparators for Integer and Float types
		Cmp<Integer> intCmp = new IntegerCmp();
		CGameSolver<Integer> intSolver = new CGameSolver<>(intCmp);

		Cmp<Float> floatCmp = new FloatCmp();
		CGameSolver<Float> floatSolver = new CGameSolver<>(floatCmp);
		
		
		
		// Instanciate a coordination game in SNF
		int nPlayers = 3;
		int nActions = 2;
		
		CGame<Integer> coordGame = new CoordinationGame(nPlayers,nActions);
		System.out.println(coordGame.nPlayers() + "-player " + coordGame.nActions() + "-action coordination game");
		System.out.println("All Nash equilibria:");
		intSolver.allNashEquilibria(coordGame, true);

				
		System.out.println("\n\n");
		
		HGGame<Integer> hgCoordGame = new HGCoordinationGame(nPlayers,nActions);
		System.out.println(hgCoordGame.nPlayers() + "-player " + coordGame.nActions() + "-action hypergraphical coordination game");
		System.out.println(hgCoordGame);
		System.out.println("All Nash equilibria:");
		intSolver.allNashEquilibria(hgCoordGame, true);
		System.out.println("coordGame == hgCoordGame:\t" + coordGame.equals(hgCoordGame) + ", " + hgCoordGame.equals(coordGame));
		System.out.println("Cost of representation (SNF):\t" + coordGame.nValues());
		System.out.println("Cost of representation (HG):\t" + hgCoordGame.nValues());

		
		
		
		System.out.println("\n\n");

		int[] nPlayersGSG = {2,2};
		float l = 1;
		int[] t = {0,1};
		GSG gsg = new GSG_SNF(nPlayersGSG[0], nPlayersGSG[1], "Team-poach and bribe", "captor", l, t, 2);
		gsg.calcul_val();
		System.out.println("("+nPlayersGSG[0]+","+nPlayersGSG[1]+")-player " + gsg.nActions() + "-action green security game");
		//System.out.println(gsg);
		System.out.println("All Nash equilibria:");
		floatSolver.allNashEquilibria(gsg, true);
		
		
	}

}
