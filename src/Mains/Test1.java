package Mains;

import java.util.ArrayList;

import Games.GSG_SNF;
import Methods.Captor;
import Methods.MethodForAttacker;
import Methods.MethodForDefender;
import Methods.SharePoachAndBribe;
import Model_cplex.Daskalakis_model;
import Model_cplex.Trabelsi_model;

public class Test1 {

	public static void main(String[] args) {
		
		/**
		 * To compare with main5 : OKOK
		 */
		
		float[] res = new float[2];
		ArrayList<ArrayList<Integer>> possibleActions = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> act = new ArrayList<Integer>();
		act.add(0);
		act.add(1);
		possibleActions.add(act);
		possibleActions.add(act);
		possibleActions.add(act);
		possibleActions.add(act);
		res[0] = 1;
		res[1] = 2;
		MethodForAttacker mAtt = new SharePoachAndBribe(1);
		MethodForDefender mDef = new Captor(1);
		GSG_SNF gsg = new GSG_SNF(2, 2, res, possibleActions, mAtt, mDef);
		gsg.computeUtilities();
		gsg.writeInFile("Test1.txt");
		
		ArrayList<int[]> acti = gsg.getProfiles();
		ArrayList<float[]> uti = gsg.getUtilities();
		Daskalakis_model daska = new Daskalakis_model(acti,uti);
		Trabelsi_model trabel = new Trabelsi_model(acti,uti);
		daska.construct_model();
		trabel.construct_model();
		System.out.println(daska.equilibrium()+" "+trabel.equilibrium());
		
		daska.print_results();
		trabel.print_results();

	}

}
