package Main;

import java.util.ArrayList;

import GSG.GSG_SNF;
import GSG.Generateur;
import Model_cplex.Daskalakis_model;
import Model_cplex.Trabelsi_model;
import Model_cplex.Trabelsi_without_objective;
import Test_model.Test_model;

public class Main3 {

	public static void main(String[] args) {
		Generateur g = new Generateur();
		int nb_player = 3;
		int nb_actions = 3;
		int nb_jeux = 50;
		String uti_att = "Team-poach and bribe";
		String uti_def = "captor";
		ArrayList<GSG_SNF> gsgs =  g.generate_GSG_SNF(nb_player, nb_actions, nb_jeux, uti_att, uti_def);
		for (GSG_SNF gsg : gsgs) {
			ArrayList<int[]> act = gsg.getProfiles();
			ArrayList<float[]> uti = gsg.getUtilities();
			Daskalakis_model daska = new Daskalakis_model(act,uti);
			Trabelsi_model trabel = new Trabelsi_model(act,uti);
			Trabelsi_without_objective trabel2 = new Trabelsi_without_objective(act,uti);
			daska.construct_model();
			trabel.construct_model();
			trabel2.construct_model();
			System.out.println(daska.equilibrium()+" "+trabel.equilibrium()+" "+trabel2.equilibrium());
		}
	}

}
