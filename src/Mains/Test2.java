package Mains;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

import Games.BEL_GSG;
import Methods.Captor;
import Methods.MethodForAttacker;
import Methods.MethodForDefender;
import Methods.SharePoachAndBribe;
import Tools.Node;
import Tools.Profile;
import Transformations.Conditionned_Transform;
import Transformations.Direct_Transform;
import Transformations.No_Transform;
import Transformations.Transformation;
import XEUs.CEU;
import XEUs.XEU;

public class Test2 {

	public static void main(String[] args) {
		int nb_attacker = 1;
		int nb_defender = 1;
		MethodForAttacker attMethod = new SharePoachAndBribe(1);
		MethodForDefender defMethod = new Captor(1);
		ArrayList<ArrayList<Integer>> possibleActions = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> act = new ArrayList<Integer>();
		act.add(0);
		act.add(1);
		possibleActions.add(act);
		possibleActions.add(act);
		possibleActions.add(act);
		int[] herd = {2};
		int nb_location = 3;
		ArrayList<int[][]> focal_element = new ArrayList<int[][]>();
		int[][] tab1 = {{1}};
		int[][] tab2 = {{0},{2}};
		focal_element.add(tab1);
		focal_element.add(tab2);
		float[] mass_function = {(float) 0.6,(float) 0.4};
		int[] seeFunction = {0};
		int[] gps = {0};
		XEU xeu = new CEU();
		Transformation Dtransfo = new Direct_Transform();
		Transformation Ctransfo = new Conditionned_Transform();
		Transformation Notransfo = new No_Transform();
		BEL_GSG game = new BEL_GSG(nb_attacker, nb_defender, attMethod, defMethod, possibleActions, herd, nb_location, focal_element, mass_function, seeFunction, gps, xeu, Dtransfo);
		game.computeUtilities();
		game.writeInFile("DtransfoTest2.txt");
		BEL_GSG game2 = new BEL_GSG(nb_attacker, nb_defender, attMethod, defMethod, possibleActions, herd, nb_location, focal_element, mass_function, seeFunction, gps, xeu, Ctransfo);
		game2.computeUtilities();
		game2.writeInFile("CtransfoTest2.txt");
		BEL_GSG game3 = new BEL_GSG(nb_attacker, nb_defender, attMethod, defMethod, possibleActions, herd, nb_location, focal_element, mass_function, seeFunction, gps, xeu, Notransfo);
		game3.computeUtilities();
		game3.writeInFile("NotransfoTest2.txt");
		
		TreeMap<Integer, TreeMap<Profile, float[]>> g = game3.getTransfo().getGame();
		TreeMap<Profile, float[]> m = g.get(0);
		Set<Profile> f = m.keySet();
		
		for (Node n : game3.getNodes()) {
			System.out.println(n.toString());
		}
		
		for ( Profile prof : f) {
			System.out.println(prof.toString());
			System.out.println();
			System.out.println();
			System.out.print(game.getUtility(prof, 0));
			System.out.println();
			System.out.print(game2.getUtility(prof, 0));
			System.out.println();
			System.out.print(game3.getUtility(prof, 0));
			System.out.println();
			System.out.println();
		}
	}

}
