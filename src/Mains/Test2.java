package Mains;

import java.util.ArrayList;

import Games.BEL_GSG;
import Methods.Captor;
import Methods.MethodForAttacker;
import Methods.MethodForDefender;
import Methods.SharePoachAndBribe;
import Transformations.Conditionned_Transform;
import Transformations.Direct_Transform;
import Transformations.Transformation;
import XEUs.CEU;
import XEUs.XEU;

public class Test2 {

	public static void main(String[] args) {
		int nb_attacker = 2;
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
		float[] mass_function = {(float) 0.3,(float) 0.2};
		int[] seeFunction = {0,1};
		int[] gps = {0};
		XEU xeu = new CEU();
		Transformation Dtransfo = new Direct_Transform();
		Transformation Ctransfo = new Conditionned_Transform();
		BEL_GSG game = new BEL_GSG(nb_attacker, nb_defender, attMethod, defMethod, possibleActions, herd, nb_location, focal_element, mass_function, seeFunction, gps, xeu, Dtransfo);
		game.computeUtilities();
		game.writeInFile("DtransfoTest2.txt");
		game.setTransfo(Ctransfo);
		game.computeUtilities();
		game.writeInFile("CtransfoTest2.txt");
	}

}
