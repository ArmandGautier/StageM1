package Main;

import java.util.Iterator;

import GSG.GSG;
import GSG.GSG_SNF;
import decision.game.CGame;
import decision.game.CGameSolver;
import decision.game.HGGame;
import decision.game.IGame;
import decision.game.MapSNFGame;
import decision.game.SNFGame;
import decision.game.factory.CoordinationGame;
import decision.game.factory.HGCoordinationGame;
import decision.game.factory.PQRExample;
import decision.game.profile.IActionProfile;
import decision.game.profile.IActionProfile.IActionProfileIterator;
import decision.game.profile.Profile;
import decision.utility.Cmp;
import decision.utility.FloatCmp;
import decision.utility.IntegerCmp;
	
/**
 * Test for decision packages
 * @author Pierre Pomeret-Coquot
 *
 */
public class MainTestsDecision {

	public static void main(String[] args) {
		
		
		// Tests on profiles
		Integer[] arr1 = {1,2,3};
		Integer[] arr2 = {1,2,3};
		Integer[] arr3 = {1,3,2};
		Profile<Integer> p1 = new Profile<>(arr1);
		Profile<Integer> p2 = new Profile<>(arr2);
		Profile<Integer> p3 = new Profile<>(arr3);
		System.out.println("Test of profile equality: p1 = " + p1 + ", p2 = " + p2 + ", p3 = " + p3);
		System.out.println("p1 == p2, p1.equals(p2), p2.equals(p1) =\t" + (p1 == p2) + ", " + p1.equals(p2) + ", " + p2.equals(p1));
		System.out.println("p1 == p3, p1.equals(p3), p3.equals(p1) =\t" + (p1 == p3) + ", " + p1.equals(p3) + ", " + p3.equals(p1));
		System.out.println("p2 == p3, p2.equals(p3), p3.equals(p2) =\t" + (p2 == p3) + ", " + p2.equals(p3) + ", " + p3.equals(p2));
		System.out.println("\n\n");

		
		
		
		// Initialize GameSolvers for Integer and Float types
		Cmp<Integer> intCmp = new IntegerCmp();
		CGameSolver<Integer> intSolver = new CGameSolver<>(intCmp);

		Cmp<Float> floatCmp = new FloatCmp();
		CGameSolver<Float> floatSolver = new CGameSolver<>(floatCmp);
		
		// IGameSolver<Integer> intISolver = new IGameSolver<>(intCmp);
		
		
		
		// Instantiate a SNF coordination game
		int nPlayers = 3;
		int nActions = 2;
		
		SNFGame<Integer> coordGame = new CoordinationGame(nPlayers,nActions);
		System.out.println(coordGame.nPlayers() + "-player " + coordGame.nActions() + "-action coordination game");
		System.out.println("All Nash equilibria:");
		intSolver.allNashEquilibria(coordGame, true);
		CGame<Integer> coordGame2 = new MapSNFGame<>(coordGame.utilityMap());
		//System.out.println(coordGame2.nPlayers() + "-player " + coordGame2.nActions() + "-action coordination game");
		//intSolver.allNashEquilibria(coordGame2, true);
		CGame<Integer> coordGame3 = new MapSNFGame<>(coordGame);
		//System.out.println(coordGame3.nPlayers() + "-player " + coordGame3.nActions() + "-action coordination game");
		//intSolver.allNashEquilibria(coordGame3, true);
		System.out.println("coordGame.equals(coordGame2), coordGame2.equals(coordGame) =\t" + coordGame.equals(coordGame2) + ", " + coordGame2.equals(coordGame));
		System.out.println("coordGame.equals(coordGame3), coordGame3.equals(coordGame) =\t" + coordGame.equals(coordGame3) + ", " + coordGame3.equals(coordGame));
		System.out.println("coordGame2.equals(coordGame3), coordGame3.equals(coordGame2) =\t" + coordGame2.equals(coordGame3) + ", " + coordGame3.equals(coordGame));

		System.out.println("\n\n");
		

		// Instantiate a HG coordination game
		HGGame<Integer> hgCoordGame = new HGCoordinationGame(nPlayers,nActions);
		System.out.println(hgCoordGame.nPlayers() + "-player " + coordGame.nActions() + "-action hypergraphical coordination game");
		System.out.println(hgCoordGame);
		System.out.println("All Nash equilibria:");
		intSolver.allNashEquilibria(hgCoordGame, true);
		System.out.println("coordGame.equals(hgCoordGame), hgCoordGame.equals(CoordGame)=\t" + coordGame.equals(hgCoordGame) + ", " + hgCoordGame.equals(coordGame));
		System.out.println("Cost of representation (SNF):\t" + coordGame.nValues());
		System.out.println("Cost of representation (HG):\t" + hgCoordGame.nValues());
		
		System.out.println("\n\n");

		
		// Instantiate a GSG
		int[] nPlayersGSG = {2,2};
		float l = 1;
		int[] t = {0,1};
		GSG gsg = new GSG_SNF(nPlayersGSG[0], nPlayersGSG[1], "Team-poach and bribe", "captor", l, t, 2);
		gsg.calcul_val();
		System.out.println("("+nPlayersGSG[0]+","+nPlayersGSG[1]+")-player " + gsg.nActions() + "-action green security game");
		//System.out.println(gsg);
		System.out.println("All Nash equilibria:");
		floatSolver.allNashEquilibria(gsg, true);
		
		System.out.println("\n\n");

		
		
		
		// Instantiate the PQR example
		IGame<Integer> pqrExample = new PQRExample();
		System.out.println("PQRExample: nPlayers=" + pqrExample.nPlayers() + ", nActions=" + pqrExample.nActions() + ", nWorlds=" + pqrExample.nWorlds() + ", nTypes=" + pqrExample.nTypes());
		System.out.println(pqrExample);
		
		Iterator<IActionProfile> it = new IActionProfileIterator(pqrExample.nActions(), pqrExample.nTypes());
		while (it.hasNext()) {
			IActionProfile p = it.next();
			System.out.println(p);
		}

	}

}
