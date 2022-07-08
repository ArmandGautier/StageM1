package GSG;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

public class Local_Game_for_TBM_Transform {

	/**
	 * the list of player who plays in at least omega that belongs to focal_elt
	 */
	private ArrayList<Node> list_player = new ArrayList<Node>();
	/**
	 * the number of omega where player i plays
	 */
	private ArrayList<Integer> nb_omega_plays = new ArrayList<Integer>();
	/**
	 * dimension of the local_game
	 */
	private int local_dimension;
	/**
	 * the index of the focal element, useful to know the value of m[indexOf_focal_elt]
	 */
	private int indexOf_focal_elt;
	/**
	 * the focal element of omega
	 */
	private int[][] focal_elt;
	/**
	 * The parent game
	 */
	private Bel_GSG parent_game;
	/**
	 * list of possible_actions for player of this local game
	 */
	ArrayList<ArrayList<Integer>> possible_actions = new ArrayList<ArrayList<Integer>>();
	/**
	 * the omega corresponding to this local game
	 */
	int[] omega;
	/**
	 * a list of profile. 
	 */
	ArrayList<int[]> profiles = new ArrayList<int[]>();
	/**
	 * a list of utility. uti.get(i) gives utilities of every players in the profile act.get(i)
	 */
	ArrayList<float[]> utilities_value = new ArrayList<float[]>();

	
	/**
	 * @param parent_game
	 * @param focal_elt
	 * @param indexOf_focal_elt
	 */
	public Local_Game_for_TBM_Transform(Bel_GSG parent_game, int[] omega,int[][] focal_elt, int indexOf_focal_elt) {
		this.parent_game = parent_game;
		this.omega = omega;
		this.indexOf_focal_elt = indexOf_focal_elt;	
		this.focal_elt = focal_elt;
		
		for (Node n : this.parent_game.nodes) {
			if ( this.parent_game.omegaToTypes(omega, n.getIndexPlayer()).equals(n.getType()) ) {
				this.addPlayer(n,this.parent_game.possible_actions.get(n.getIndexPlayer()));
			}
		}
		
		this.local_dimension = 1; 
		for (ArrayList<Integer> list_actions : this.possible_actions) {
			this.local_dimension = this.local_dimension*list_actions.size();
		}
	}

	/**
	 * @param player
	 * @param actions
	 */
	protected void addPlayer(Node player, ArrayList<Integer> actions) {
		this.list_player.add(player);
		this.possible_actions.add(actions);
		this.nb_omega_plays.add(computeNbOmegaPlays(player));
	}

	private Integer computeNbOmegaPlays(Node player) { // doit pouvoir se mettre ailleurs pour ne pas avoir à le calculer pour chacun des omega
		Integer res = 0;
		for (int[] element : this.focal_elt) {
			if ( this.parent_game.omegaToTypes(element, player.getIndexPlayer()).equals(player.getType()) ) {
				res++;
			}
		}
		return res;
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
			for (Node player : this.list_player) {
				float k = this.parent_game.computeK(player);
				float val = computeVal(player,choix_des_joueurs,j);
				uti_tmp[j] = k * this.parent_game.mass_function[indexOf_focal_elt] * val;
				c[j] = choix_des_joueurs.get(j);
				j++;
			}
			this.utilities_value.add(uti_tmp);
			this.profiles.add(c);
		}
	}

	private float computeVal(Node player, ArrayList<Integer> choix_des_joueurs, int j) {
		
		float val = 0;

		int[] profil_selon_omega = computeProfile(choix_des_joueurs);
		int indiceGSG = this.parent_game.getIndexOfGSG(omega);
		GSG_SNF gsg = this.parent_game.gsg_snf.get(indiceGSG);
		int indiceProfile = this.parent_game.getIndexOfProfile(profil_selon_omega,gsg);
		val += gsg.getUtilities().get(indiceProfile)[player.getIndexPlayer()];

		if ( this.nb_omega_plays.get(j) > 0) {
			return val/this.nb_omega_plays.get(j);
		}
		else {
			return val;
		}
	}
	
	/**
	 * @param index_omega
	 * @param choix_des_joueurs
	 * @return a restricting profile of "choix_des_joueurs" considering only players who played in omega
	 */
	private int[] computeProfile(ArrayList<Integer> choix_des_joueurs) {
		int[] profile = new int[this.parent_game.nb_player];
		int index_player = 0;
		for (Node player : this.list_player) {
			profile[player.getIndexPlayer()] = choix_des_joueurs.get(index_player);
			index_player++;
		}
		return profile;
	}
	
	/**
	 * print information to describe this game
	 */
	public void afficher_jeux() {
		System.out.println("This game concerned the following omega : " );
		for (int place : omega) {
			System.out.print(place + " ");
		}
		System.out.println();
		ListIterator<int[]> it1 = this.profiles.listIterator();
		ListIterator<float[]> it2 = this.utilities_value.listIterator();
		while (it1.hasNext() && it2.hasNext()) {
			int[] tab1 = it1.next();
			float[] tab2 = it2.next();
			System.out.print("Profil : ");
			for (int i=0; i<tab1.length; i++) {
				System.out.print(tab1[i] + " ");
			}
			System.out.print(" Utilitées correspondantes : ");
			for (int j=0; j<tab2.length; j++) {
				System.out.print(tab2[j] + " ");
			}
			System.out.println();
		}
	}
	
	/**
	 * @param filename
	 * write the matrixes of the game in a file
	 */
	public void writeInFile(String filename) {
		try {
	
			  
		  File file = new File(filename);

		  if (!file.exists()) {
			  file.createNewFile();
		  }
		  
		  FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		  BufferedWriter bw = new BufferedWriter(fw);
		  
		  String content;
		  
		  content = "\n";
		  for (Node player : this.list_player) {
			  content+= "joueur numéro " + player.getIndexPlayer() + " type = "+ player.getType().toString() + " ";
		  }
		  bw.write(content);
		  bw.write("\n");
		  
		  ListIterator<int[]> it1 = this.profiles.listIterator();
		  ListIterator<float[]> it2 = this.utilities_value.listIterator();
		  
		  while (it1.hasNext() && it2.hasNext()) {
			  content = "";
			  int[] tab1 = it1.next();
			  float[] tab2 = it2.next();
			  for (int i=0; i<tab1.length; i++) {
				  content += tab1[i];
				  content += " ";
			  }
			  content += " : ";
			  for (int j=0; j<tab2.length; j++) {
				  content += tab2[j];
				  content += " ";
			  }
			  content += "\n";
			  bw.write(content);
		  }
		  bw.close();
		} catch (IOException e) {e.printStackTrace();}
	}
	
	/**
	 * @return Profiles
	 */
	public ArrayList<int[]> getProfiles() {
		return this.profiles;
	}

	/**
	 * @return Utilities value
	 */
	public ArrayList<float[]> getUtilities() {
		return this.utilities_value;
	}
	
	/**
	 * @return the list of player in this game, with integer's value
	 */
	public ArrayList<Integer> getListPlayer() {
		ArrayList<Integer> res = new ArrayList<Integer>();
		for (Node local_player : this.list_player) {
			int index = 0;
			for (Node player : this.parent_game.nodes) {
				if (local_player.equals(player)) {
					res.add(index);
				}
				index++;
			}
		}
		return res;
	}
	
	protected boolean playIn(Node player) {
		for (Node n : this.list_player) {
			if (player.equals(n)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param profile1
	 * @param profile2
	 * @return true if the two arrays are the same, false otherwise // a deplacer dans une classe avec toutes les fonctions utiles 
	 */
	private boolean same(int[] profile1, int[] profile2) {
		if (profile1.length != profile2.length) {
			return false;
		}
		for (int i=0; i<profile1.length; i++) {
			if (profile1[i] != profile2[i]) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @param profile
	 * @param gsg
	 * @return the index of the profile "profile" in the Local_GSG in SNF "gsg"
	 */
	private int getIndexOfProfile(int[] profile, Local_Game_for_TBM_Transform gsg) {
		int indice = 0;
		for (int[] p : gsg.getProfiles()) {
			if (same(profile,p)) {
				return indice;
			}
			indice++;
		}
		return -1;
	}
	
	// sert juste pour la verif
	public float getUtility(int[] profile, Node player) {
		int indexPlayer = 0;
		for ( Node n : this.list_player) {
			if ( player.equals(n)) {
				break;
			}
			else {
				indexPlayer++;
			}
		}
		int[] reduceProfile = getReduceProfile(profile);
		int indexUtility = getIndexOfProfile(reduceProfile, this);
		return this.utilities_value.get(indexUtility)[indexPlayer];
	}

	/**
	 * @param profile
	 * @return
	 */
	private int[] getReduceProfile(int[] profile) {
		int[] reduceProfile = new int[this.list_player.size()];
		int indexOfreduceProfile = 0;
		int indexOfprofile = 0;
		for (Node n : this.parent_game.nodes) {
			if (playIn(n)) {
				reduceProfile[indexOfreduceProfile] = profile[indexOfprofile];
				indexOfreduceProfile++;
			}
			indexOfprofile++;
		}
		return reduceProfile;
	}
}
