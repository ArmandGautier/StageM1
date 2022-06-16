package GSG;

import java.util.ArrayList;

public class Local_Game {

	private ArrayList<int[]> list_player = new ArrayList<int[]>();
	private int local_dimension;
	private GSG_MFWT parent_game;
	ArrayList<ArrayList<Integer>> possible_actions = new ArrayList<ArrayList<Integer>>();
	
	/**
	 * a list of profile. 
	 */
	ArrayList<int[]> profiles = new ArrayList<int[]>();
	/**
	 * a list of utility. uti.get(i) gives utilities of every players in the profile act.get(i)
	 */
	ArrayList<float[]> utilities_value = new ArrayList<float[]>();

	public Local_Game(GSG_MFWT parent_game) {
		this.parent_game = parent_game;
	}

	private void addPlayer(int[] player, ArrayList<Integer> actions) {
		this.list_player.add(player);
		this.possible_actions.add(actions);
	}

	public void calcul_val(int[][] focal_elt, ArrayList<int[]> noeuds) {
		for (int[] n : noeuds) {
			if (typeIsTrue(n,focal_elt)) {
				boolean b = n[0] < this.parent_game.nb_attacker;
				this.addPlayer(n,this.parent_game.possible_actions.get(n[0]));
			}
		}
		
		this.local_dimension = 1; 
		for (ArrayList<Integer> list_actions : this.possible_actions) {
			this.local_dimension = this.local_dimension*list_actions.size();
		}
		
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
				float k = computeK(player);
				float val = computeVal(player,choix_des_joueurs,focal_elt);
				uti_tmp[j] = k * this.parent_game.mass_function[i] * val;
				c[j] = choix_des_joueurs.get(j);
				j++;
			}
			this.utilities_value.add(uti_tmp);
			this.profiles.add(c);
		}
	}
	
	private boolean typeIsTrue(int[] node, int[][] focal_elt) {
		for (int[] omega : focal_elt) {
			if ( this.parent_game.omegaToTypes(omega, node[0])==node[1] ) {
				return true;
			}
		}
		return false;
	}

	private float computeVal(int[] player, ArrayList<Integer> choix_des_joueurs, int[][] focal_elt) {
		
		switch(this.parent_game.method) {
		case "CEU" :
			return calcul_val_using_CEU(player,choix_des_joueurs,focal_elt);
		case "JEU" :
			return calcul_val_using_JEU(player,choix_des_joueurs,focal_elt);
		case "TBEU" :
			return calcul_val_using_TBEU(player,choix_des_joueurs,focal_elt);
		default : 
			System.out.println("Cette méthode n'existe pas ou n'est pas implémentée");
			break;
		}
		return 0;
	}

	private float calcul_val_using_TBEU(int[] player, ArrayList<Integer> choix_des_joueurs, int[][] focal_elt) {
		return 0;
	}
	
	private float calcul_val_using_JEU(int[] player, ArrayList<Integer> choix_des_joueurs, int[][] focal_elt) {
		// TODO Auto-generated method stub
		return 0;
	}

	private float calcul_val_using_CEU(int[] player, ArrayList<Integer> choix_des_joueurs, int[][] focal_elt) {
		// TODO Auto-generated method stub
		return 0;
	}

	private float computeK(int[] player ) {
		int k = 0;
		int i = 0;
		for (int[][] elt_focal : this.parent_game.focal_element) {
			if (typeIsTrue(player,elt_focal)) {
				k+=this.parent_game.mass_function[i];
			}
			i++;
		}
		return 1/k;
	}
}
