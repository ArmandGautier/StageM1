package GSG;

import java.util.ArrayList;

public class Local_Game {

	private ArrayList<int[]> list_player = new ArrayList<int[]>();
	private ArrayList<ArrayList<Boolean>> play_in_omega = new ArrayList<ArrayList<Boolean>>();
	private int local_dimension;
	private GSG_MFWT parent_game;
	ArrayList<ArrayList<Integer>> possible_actions = new ArrayList<ArrayList<Integer>>();
	int[][] focal_elt;
	
	/**
	 * a list of profile. 
	 */
	ArrayList<int[]> profiles = new ArrayList<int[]>();
	/**
	 * a list of utility. uti.get(i) gives utilities of every players in the profile act.get(i)
	 */
	ArrayList<float[]> utilities_value = new ArrayList<float[]>();

	public Local_Game(GSG_MFWT parent_game, int[][] focal_elt) {
		this.parent_game = parent_game;
		this.focal_elt = focal_elt;
		
		for (int[] n : this.parent_game.nodes) {
			if (this.parent_game.typeIsTrue(n,this.focal_elt)) {
				this.addPlayer(n,this.parent_game.possible_actions.get(n[0]));
			}
		}
		
		for (int[] omega : this.focal_elt) {
			ArrayList<Boolean> list_player_temp = new ArrayList<Boolean>();
			for (int[] player : this.list_player) {
				if ( this.parent_game.omegaToTypes(omega, player[0])==player[1] ) {
					list_player_temp.add(true);
				}
				else {
					list_player_temp.add(false);
				}
			}
			this.play_in_omega.add(list_player_temp);
		}
		
		this.local_dimension = 1; 
		for (ArrayList<Integer> list_actions : this.possible_actions) {
			this.local_dimension = this.local_dimension*list_actions.size();
		}
	}

	private void addPlayer(int[] player, ArrayList<Integer> actions) {
		this.list_player.add(player);
		this.possible_actions.add(actions);
	}

	public void calcul_val() {
		
		ArrayList<Integer> choix_des_joueurs = new ArrayList<Integer>();
	    for (int k=0; k < this.list_player.size(); k++) {
	    	choix_des_joueurs.add(this.possible_actions.get(k).get(0));
	    }
	    
		for (int i=0; i<this.local_dimension; i++) {
			
			int changement_action_pour_k = this.local_dimension/this.possible_actions.get(0).size();
			
			if (i != 0) {
			    for (int k=0; k < this.list_player.size(); k++) {
			    	
					if (Math.floorMod(i,changement_action_pour_k) == 0) {
						int a = choix_des_joueurs.get(k);
						int ind_actuel = this.possible_actions.get(k).indexOf(a);
						choix_des_joueurs.set(k, this.possible_actions.get(k).get(ind_actuel+1));
					    for (int l=k+1; l < this.list_player.size(); l++) {
					    	choix_des_joueurs.set(l,0);
					    }
					    break;
					}
					
					else {
						changement_action_pour_k = changement_action_pour_k/this.possible_actions.get(k).size();
					}
			    }
			}
			
			int j = 0;
			float[] uti_tmp = new float[this.list_player.size()];
			int[] c = new int[this.list_player.size()];
			for (int[] player : this.list_player) {
				float k = this.parent_game.computeK(player);
				float val = computeVal(player,choix_des_joueurs);
				uti_tmp[j] = k * this.parent_game.mass_function[i] * val;
				c[j] = choix_des_joueurs.get(j);
				j++;
			}
			this.utilities_value.add(uti_tmp);
			this.profiles.add(c);
		}
	}

	private float computeVal(int[] player, ArrayList<Integer> choix_des_joueurs) {
		
		switch(this.parent_game.method) {
		case "CEU" :
			return calcul_val_using_CEU(player,choix_des_joueurs);
		case "JEU" :
			return calcul_val_using_JEU(player,choix_des_joueurs);
		case "TBEU" :
			return calcul_val_using_TBEU(player,choix_des_joueurs);
		default : 
			System.out.println("Cette méthode n'existe pas ou n'est pas implémentée");
			break;
		}
		return 0;
	}

	private float calcul_val_using_TBEU(int[] player, ArrayList<Integer> choix_des_joueurs) {
		float val = 0;
		int nb_omega=0;
		int index_omega=0;
		for (int[] omega : this.focal_elt) {
			if ( this.parent_game.omegaToTypes(omega, player[0])==player[1] ) {
				nb_omega++;
				int[] profil_selon_omega = computeProfile(index_omega,choix_des_joueurs);
				int indiceGSG = this.parent_game.getIndexOfGSG(omega);
				GSG_SNF gsg = this.parent_game.gsg_snf.get(indiceGSG);
				int indiceProfile = this.parent_game.getIndexOfProfile(profil_selon_omega,gsg);
				val += gsg.getUtilities().get(indiceProfile)[player[0]];
			}
			index_omega++;
		}
		if (nb_omega > 0) {
			return val/nb_omega;
		}
		else {
			return val;
		}
	}
	
	private float calcul_val_using_JEU(int[] player, ArrayList<Integer> choix_des_joueurs) {
		// TODO Auto-generated method stub
		return 0;
	}

	private float calcul_val_using_CEU(int[] player, ArrayList<Integer> choix_des_joueurs) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private int[] computeProfile(int index_omega, ArrayList<Integer> choix_des_joueurs) {
		int[] profile = new int[this.parent_game.nb_player];
		int index_player = 0;
		for (int[] player : this.list_player) {
			if (this.play_in_omega.get(index_omega).get(index_player)) {
				profile[player[0]] = choix_des_joueurs.get(index_player);
			}
			index_player++;
		}
		return profile;
	}
}
