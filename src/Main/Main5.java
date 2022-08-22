package Main;

import java.util.ArrayList;

import GSG.GSG_SNF;
import Model_cplex.Daskalakis_model;
import Model_cplex.Trabelsi_model;

public class Main5 {
	
	public static void main(String[] args) {
	
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
		GSG_SNF gsg = new GSG_SNF(2, 2, "Team-poach and bribe", "captor", res, possibleActions);
		gsg.calcul_val();
		gsg.afficher_jeux();
		
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
