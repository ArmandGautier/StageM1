package Main;

import java.util.ArrayList;

import GSG.GSG_SNF;
import GSG.Generateur;
import Test_model.Test_model;

public class Main2 {

	public static void main(String[] args) {
		Generateur g = new Generateur();
		Test_model t = new Test_model();
		int nb_actions = 2;
		int nb_jeux = 50;
		String uti_att = "Team-poach or bribe";
		String uti_def = "captor";
		String filename = "dvsT2.csv";
		for (int i = 2; i <= 8; i++) {
			ArrayList<GSG_SNF> gsgs =  g.generate_GSG_SNF(i, nb_actions, nb_jeux, uti_att, uti_def);
			t.test_on_GSG_SNF(gsgs, filename, i, nb_actions);
		}
	}

}
