package GSG;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

public class Local_Game {

	/**
	 * the list of player who plays in at least omega that belongs to focal_elt
	 */
	private ArrayList<Node> list_player = new ArrayList<Node>();
	/**
	 * for each omega that belongs to focal_elt, give a list of boolean to know if player's plays or not in omega
	 */
	private ArrayList<ArrayList<Boolean>> play_in_omega = new ArrayList<ArrayList<Boolean>>();
	/**
	 * dimension of the local_game
	 */
	private int local_dimension;
	/**
	 * the index of the focal element, useful to know the value of m[indexOf_focal_elt]
	 */
	private int indexOf_focal_elt;
	/**
	 * The parent game
	 */
	private Bel_GSG parent_game;
	/**
	 * list of possible_actions for player of this local game
	 */
	ArrayList<ArrayList<Integer>> possible_actions = new ArrayList<ArrayList<Integer>>();
	/**
	 * the focal element corresponding to this local game
	 */
	int[][] focal_elt;
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
	public Local_Game(Bel_GSG parent_game, int[][] focal_elt, int indexOf_focal_elt) {
		this.parent_game = parent_game;
		this.focal_elt = focal_elt;
		this.indexOf_focal_elt = indexOf_focal_elt;
		
		for (Node n : this.parent_game.nodes) {
			if (this.parent_game.typeIsTrue(n,this.focal_elt)) {
				this.addPlayer(n,this.parent_game.possible_actions.get(n.getIndexPlayer()));
			}
		}
		
		for (int[] omega : this.focal_elt) {
			ArrayList<Boolean> list_player_temp = new ArrayList<Boolean>();
			for (Node player : this.list_player) {
				if ( this.parent_game.omegaToTypes(omega, player.getIndexPlayer()).equals(player.getType()) ) {
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

	/**
	 * @param player
	 * @param actions
	 */
	private void addPlayer(Node player, ArrayList<Integer> actions) {
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
		
		switch(this.parent_game.method) {
		case "CEU" :
			return calcul_val_using_CEU(player,choix_des_joueurs,j);
		case "JEU" :
			return calcul_val_using_JEU(player,choix_des_joueurs,j);
		case "TBEU" :
			return calcul_val_using_TBEU(player,choix_des_joueurs,j);
		default : 
			System.out.println("Cette méthode n'existe pas ou n'est pas implémentée");
			break;
		}
		return 0;
	}

	private float calcul_val_using_TBEU(Node player, ArrayList<Integer> choix_des_joueurs, int j) {
		float val = 0;
		int nb_omega=0;
		int index_omega=0;
		for (int[] omega : this.focal_elt) {
			if ( this.play_in_omega.get(index_omega).get(j) ) {
				nb_omega++;
				int[] profil_selon_omega = computeProfile(index_omega,choix_des_joueurs);
				int indiceGSG = this.parent_game.getIndexOfGSG(omega);
				GSG_SNF gsg = this.parent_game.gsg_snf.get(indiceGSG);
				int indiceProfile = this.parent_game.getIndexOfProfile(profil_selon_omega,gsg);
				val += gsg.getUtilities().get(indiceProfile)[player.getIndexPlayer()];
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
	
	private float calcul_val_using_JEU(Node player, ArrayList<Integer> choix_des_joueurs, int j) {
		float val = 0;
		int index_omega=0;
		float min = Float.MAX_VALUE;
		float max = Float.MIN_VALUE;
		for (int[] omega : this.focal_elt) {
			if ( this.play_in_omega.get(index_omega).get(j) ) {
				int[] profil_selon_omega = computeProfile(index_omega,choix_des_joueurs);
				int indiceGSG = this.parent_game.getIndexOfGSG(omega);
				GSG_SNF gsg = this.parent_game.gsg_snf.get(indiceGSG);
				int indiceProfile = this.parent_game.getIndexOfProfile(profil_selon_omega,gsg);
				float toCompare = gsg.getUtilities().get(indiceProfile)[player.getIndexPlayer()];
				if ( toCompare < min ) {
					min = toCompare;
				}
				if ( toCompare > max ) {
					max = toCompare;
				}
			}
			index_omega++;
		}
		val = this.parent_game.alpha[player.getIndexPlayer()]*min+(1-this.parent_game.alpha[player.getIndexPlayer()])*max;
		return val;
	}
	
	private float calcul_val_using_CEU(Node player, ArrayList<Integer> choix_des_joueurs, int j) {
		float min = Float.MAX_VALUE;
		int index_omega=0;
		for (int[] omega : this.focal_elt) {
			if ( this.play_in_omega.get(index_omega).get(j) ) {
				int[] profil_selon_omega = computeProfile(index_omega,choix_des_joueurs);
				int indiceGSG = this.parent_game.getIndexOfGSG(omega);
				GSG_SNF gsg = this.parent_game.gsg_snf.get(indiceGSG);
				int indiceProfile = this.parent_game.getIndexOfProfile(profil_selon_omega,gsg);
				float toCompare = gsg.getUtilities().get(indiceProfile)[player.getIndexPlayer()];
				if ( toCompare < min ) {
					min = toCompare;
				}
			}
			index_omega++;
		}
		return min;
	}
	
	/**
	 * @param index_omega
	 * @param choix_des_joueurs
	 * @return a restricting profile of "choix_des_joueurs" considering only players who played in omega
	 */
	private int[] computeProfile(int index_omega, ArrayList<Integer> choix_des_joueurs) {
		int[] profile = new int[this.parent_game.nb_player];
		int index_player = 0;
		for (Node player : this.list_player) {
			if (this.play_in_omega.get(index_omega).get(index_player)) {
				profile[player.getIndexPlayer()] = choix_des_joueurs.get(index_player);
			}
			index_player++;
		}
		return profile;
	}
	
	/**
	 * print information to describe this game
	 */
	public void afficher_jeux() {
		System.out.println("This game concerned the following focal element : " );
		for (int[] omega : this.focal_elt) {
			for (int place : omega) {
				System.out.print(place + " ");
			}
			System.out.println();
		}
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
			
		  System.out.println("This game concerned the following focal element : " );
		  for (int[] omega : this.focal_elt) {
			  for (int place : omega) {
				  System.out.print(place + " ");
			  }
			  System.out.println();
		  }	
			  
		  File file = new File(filename);

		  if (!file.exists()) {
			  file.createNewFile();
		  }
		  
		  FileWriter fw = new FileWriter(file.getAbsoluteFile());
		  BufferedWriter bw = new BufferedWriter(fw);
		  
		  String content;
		  
		  content = "";
		  for (Node player : this.list_player) {
			  content+= "joueur numéro " + player.getIndexPlayer() + " type = "+ player.getType() + " ";
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
}
