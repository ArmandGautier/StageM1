package GSG;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

public class Bel_GSG_SNF extends Bel_GSG {
	
	int dimension = 1;
	private ArrayList<ArrayList<Boolean>> play_in_omega = new ArrayList<ArrayList<Boolean>>();
	/**
	 * a list of profile. 
	 */
	ArrayList<int[]> profiles = new ArrayList<int[]>();
	/**
	 * a list of utility. uti.get(i) gives utilities of every players in the profile act.get(i)
	 */
	ArrayList<float[]> utilities_value = new ArrayList<float[]>();
	
	public Bel_GSG_SNF(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility,
			ArrayList<ArrayList<Integer>> possible_actions, int[] herd, int fine_or_bribe, int nb_location,
			ArrayList<int[][]> focal_element, float[] mass_function, String method, float[] alpha, int[] seeFunction, int[] gps) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, possible_actions, herd, fine_or_bribe, nb_location,
				focal_element, mass_function, method, alpha, seeFunction, gps);
	}
	
	public Bel_GSG_SNF(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility,
			ArrayList<ArrayList<Integer>> possible_actions, int[] herd, int fine_or_bribe, int nb_location,
			ArrayList<int[][]> focal_element, float[] mass_function, String method, int[] seeFunction, int[] gps) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, possible_actions, herd, fine_or_bribe, nb_location,
				focal_element, mass_function, method, seeFunction, gps);
	}
	
	public void calcul_val() {
		
		create_snf_gsg();
		create_noeuds();
		
		for (Node joueur : this.nodes) {
			this.dimension = this.dimension * this.possible_actions.get(joueur.getIndexPlayer()).size();
		}
		
		for (int[][] elt : this.focal_element) {
			for (int[] omega : elt) {
				ArrayList<Boolean> list_player_temp = new ArrayList<Boolean>();
				for (Node player : this.nodes) {
					if ( this.omegaToTypes(omega, player.getIndexPlayer()).equals(player.getType()) ) {
						list_player_temp.add(true);
					}
					else {
						list_player_temp.add(false);
					}
				}
				this.play_in_omega.add(list_player_temp);
			}
		}
		
		ArrayList<Integer> choix_des_joueurs = new ArrayList<Integer>();
	    for (Node n : this.nodes) {
	    	choix_des_joueurs.add(this.possible_actions.get(n.getIndexPlayer()).get(0));
	    }
		
		for (int i=0; i<this.dimension; i++) {
			
			int changement_action_pour_k = this.dimension/this.possible_actions.get(0).size();
			
			if (i != 0) {
				int k=0;
			    for (Node n : this.nodes) {
			    	
					if (Math.floorMod(i,changement_action_pour_k) == 0) {
						int a = choix_des_joueurs.get(k);
						int ind_actuel = this.possible_actions.get(n.getIndexPlayer()).indexOf(a);
						choix_des_joueurs.set(k, this.possible_actions.get(n.getIndexPlayer()).get(ind_actuel+1));
					    for (int l=k+1; l < this.nodes.size(); l++) {
					    	choix_des_joueurs.set(l,this.possible_actions.get(this.nodes.get(l).getIndexPlayer()).get(0));
					    }
					    break;
					}
					
					else {
						changement_action_pour_k = changement_action_pour_k/this.possible_actions.get(n.getIndexPlayer()).size();
					}
					k++;
			    }
			}
			
			int j = 0;
			float[] uti_tmp = new float[this.nodes.size()];
			int[] c = new int[this.nodes.size()];
			for (Node player : this.nodes) {
				float val = computeVal(player,choix_des_joueurs,j);
				uti_tmp[j] = val;
				c[j] = choix_des_joueurs.get(j);
				j++;
			}
			this.utilities_value.add(uti_tmp);
			this.profiles.add(c);
		}
	}
	
	private float computeVal(Node player, ArrayList<Integer> choix_des_joueurs, int j) {
		
		switch(this.method) {
		case "CEU" :
			return calcul_val_using_CEU(player,choix_des_joueurs, j);
		case "JEU" :
			return calcul_val_using_JEU(player,choix_des_joueurs, j);
		case "TBEU" :
			return calcul_val_using_TBEU(player,choix_des_joueurs, j);
		default : 
			System.out.println("Cette méthode n'existe pas ou n'est pas implémentée");
			break;
		}
		return 0;
	}

	private float calcul_val_using_TBEU(Node player, ArrayList<Integer> choix_des_joueurs, int j) {
		float val=0;
		float k = computeK(player);
		int index_omega=0;
		int index_focal_element=0;
		for (int[][] focal_elt : this.focal_element) {
			if (typeIsTrue(player,focal_elt)) {
				float temp_val = 0;
				float nb_omega = 0;
				for (int[] omega : focal_elt) {
					if ( this.play_in_omega.get(index_omega).get(j) ) {
						nb_omega++;
						int[] profil_selon_omega = computeProfile(index_omega,choix_des_joueurs);
						int indiceGSG = this.getIndexOfGSG(omega);
						GSG_SNF gsg = this.gsg_snf.get(indiceGSG);
						int indiceProfile = this.getIndexOfProfile(profil_selon_omega,gsg);
						temp_val += gsg.getUtilities().get(indiceProfile)[player.getIndexPlayer()];
					}
					index_omega++;
				}
				if (nb_omega > 0) {
					temp_val = temp_val/nb_omega;
				}
				val += k * this.mass_function[index_focal_element] * temp_val;
			}
			else {
				index_omega += focal_elt.length;
			}
			index_focal_element++;
		}
		return val;
	}
	
	private float calcul_val_using_JEU(Node player, ArrayList<Integer> choix_des_joueurs, int j) {
		float val=0;
		float k = computeK(player);
		int index_omega=0;
		int index_focal_element=0;
		for (int[][] focal_elt : this.focal_element) {
			if (typeIsTrue(player,focal_elt)) {
				float min = Float.MAX_VALUE;
				float max = Float.MIN_VALUE;
				for (int[] omega : focal_elt) {
					if ( this.play_in_omega.get(index_omega).get(j) ) {
						int[] profil_selon_omega = computeProfile(index_omega,choix_des_joueurs);
						int indiceGSG = this.getIndexOfGSG(omega);
						GSG_SNF gsg = this.gsg_snf.get(indiceGSG);
						int indiceProfile = this.getIndexOfProfile(profil_selon_omega,gsg);
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
				val += k * this.mass_function[index_focal_element] * this.alpha[player.getIndexPlayer()]*min+(1-this.alpha[player.getIndexPlayer()])*max;
			}
			else {
				index_omega += focal_elt.length;
			}
			index_focal_element++;
		}
		return val;
	}

	private float calcul_val_using_CEU(Node player, ArrayList<Integer> choix_des_joueurs, int j) {
		float val=0;
		float k = computeK(player);
		int index_omega=0;
		int index_focal_element=0;
		for (int[][] focal_elt : this.focal_element) {
			if (typeIsTrue(player,focal_elt)) {
				float min = Float.MAX_VALUE;
				for (int[] omega : focal_elt) {
					if ( this.play_in_omega.get(index_omega).get(j) ) {
						int[] profil_selon_omega = computeProfile(index_omega,choix_des_joueurs);
						int indiceGSG = this.getIndexOfGSG(omega);
						GSG_SNF gsg = this.gsg_snf.get(indiceGSG);
						int indiceProfile = this.getIndexOfProfile(profil_selon_omega,gsg);
						float toCompare = gsg.getUtilities().get(indiceProfile)[player.getIndexPlayer()];
						if ( toCompare < min ) {
							min = toCompare;
						}
					}
					index_omega++;
				}
				val += k * this.mass_function[index_focal_element] * min;
			}
			else {
				index_omega += focal_elt.length;
			}
			index_focal_element++;
		}
		return val;
	}
	
	private int[] computeProfile(int index_omega, ArrayList<Integer> choix_des_joueurs) {
		int[] profile = new int[this.nb_player];
		int index_player = 0;
		for (Node player : this.nodes) {
			if (this.play_in_omega.get(index_omega).get(index_player)) {
				profile[player.getIndexPlayer()] = choix_des_joueurs.get(index_player);
			}
			index_player++;
		}
		return profile;
	}
	
	public void writeInFile(String filename) {
		try {
			  
		  File file = new File(filename);

		  if (!file.exists()) {
			  file.createNewFile();
		  }
		  
		  FileWriter fw = new FileWriter(file.getAbsoluteFile());
		  BufferedWriter bw = new BufferedWriter(fw);
		  
		  String content;
		  
		  content = "";
		  for (Node player : this.nodes) {
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
	 * @param profile
	 * @param gsg
	 * @return the index of the profile "profile" in the Bel_GSG in SNF "gsg"
	 */
	private int getIndexOfProfile(int[] profile, Bel_GSG_SNF gsg) {
		int indice = 0;
		for (int[] p : gsg.getProfiles()) {
			if (same(profile,p)) {
				return indice;
			}
			indice++;
		}
		return -1;
	}
	
	// sert juste pour verif
	public float getUtility(int[] profile, Node player) {
		int indexPlayer = 0;
		for ( Node n : this.nodes) {
			if ( player.equals(n)) {
				break;
			}
			else {
				indexPlayer++;
			}
		}
		if (indexPlayer==this.nodes.size()) {
			System.out.println("Ce joueur n'existe pas");
			return 0;
		}
		int indexUtility = getIndexOfProfile(profile, this);
		return this.utilities_value.get(indexUtility)[indexPlayer];
	}

}
