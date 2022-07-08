package Main;

import java.util.ArrayList;

import GSG.GSG_SNF;
import GSG.Generateur;
import Test_model.Test_model;

public class Main2 {

	public static void main(String[] args) {
		Generateur g = new Generateur();
		Test_model t = new Test_model();
		int nb_player = 2;
		int nb_jeux = 200;
		String uti_att = "Team-poach and bribe";
		String uti_def = "captor";
		String filename = "Test pour 2 players TS&B_Captor 5.txt";
		for (int nb_actions = 2; nb_actions <= 10; nb_actions++) {
			ArrayList<GSG_SNF> gsgs =  g.generate_GSG_SNF(nb_player, nb_actions, nb_jeux, uti_att, uti_def);
			t.test_on_GSG_SNF(gsgs, filename, nb_player, nb_actions);
		}
	}

}
