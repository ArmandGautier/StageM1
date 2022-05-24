/**
 * 
 */
package GSG;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author agautier
 */
public class GSG_SNF extends GSG {
	
	/**
	 * a list of profile. 
	 */
	ArrayList<int[]> profiles = new ArrayList<int[]>();
	/**
	 * a list of utility. uti.get(i) gives utilities of every players in the profile act.get(i)
	 */
	ArrayList<float[]> utilities_value = new ArrayList<float[]>();

	/**
	 * Constructor when all players can make the same actions and actions have all the same value.
	 * @param nb_attacker
	 * @param nb_defender
	 * @param attacker_utility
	 * @param defender_utility
	 * @param lambda
	 * @param actions
	 */
	public GSG_SNF(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float lambda, int[] actions) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, actions);
		this.dimension = (int) (Math.pow(actions.length, this.nb_player));
	}
	/**
	 * Constructor when all players can make the same actions.
	 * @param nb_attacker
	 * @param nb_defender
	 * @param attacker_utility
	 * @param defender_utility
	 * @param lambda
	 * @param actions
	 */
	public GSG_SNF(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float[] lambda, int[] actions) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, actions);
		this.dimension = (int) (Math.pow(actions.length, this.nb_player));
	}
	/**
	 * Constructor when all players have access to different actions and actions have all the same value.
	 * @param nb_attacker
	 * @param nb_defender
	 * @param attacker_utility
	 * @param defender_utility
	 * @param lambda
	 * @param possible_actions
	 */
	public GSG_SNF(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float lambda, ArrayList<ArrayList<Integer>> possible_actions) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, possible_actions);
		this.dimension = 1;
		for ( ArrayList<Integer> list_actions : possible_actions) {
			this.dimension = this.dimension * list_actions.size();
		}
	}
	/**
	 * Constructor when all players have access to different actions.
	 * @param nb_attacker
	 * @param nb_defender
	 * @param attacker_utility
	 * @param defender_utility
	 * @param lambda
	 * @param possible_actions
	 */
	public GSG_SNF(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float[] lambda, ArrayList<ArrayList<Integer>> possible_actions) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, possible_actions);
		this.dimension = 1;
		for ( ArrayList<Integer> list_actions : possible_actions) {
			this.dimension = this.dimension * list_actions.size();
		}
	}
	/**
	 * Constructor when all players can make the same actions, actions have all the same value and to change the value of "fine_or_bribe".
	 * @param nb_attacker
	 * @param nb_defender
	 * @param attacker_utility
	 * @param defender_utility
	 * @param lambda
	 * @param actions
	 * @param fine_or_bribe
	 */
	public GSG_SNF(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float lambda, int[] actions, int fine_or_bribe) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, actions, fine_or_bribe);
		this.dimension = (int) (Math.pow(actions.length, this.nb_player));
	}
	/**
	 * Constructor when all players can make the same actions and to change the value of "fine_or_bribe".
	 * @param nb_attacker
	 * @param nb_defender
	 * @param attacker_utility
	 * @param defender_utility
	 * @param lambda
	 * @param actions
	 * @param fine_or_bribe
	 */
	public GSG_SNF(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float[] lambda, int[] actions, int fine_or_bribe) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, actions, fine_or_bribe);
		this.dimension = (int) (Math.pow(actions.length, this.nb_player));
	}
	/**
	 * Constructor when all players have access to different actions, actions have all the same value and to change the value of "fine_or_bribe".
	 * @param nb_attacker
	 * @param nb_defender
	 * @param attacker_utility
	 * @param defender_utility
	 * @param lambda
	 * @param possible_actions
	 * @param fine_or_bribe
	 */
	public GSG_SNF(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float lambda, ArrayList<ArrayList<Integer>> possible_actions, int fine_or_bribe) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, possible_actions, fine_or_bribe);
		this.dimension = 1;
		for ( ArrayList<Integer> list_actions : possible_actions) {
			this.dimension = this.dimension * list_actions.size();
		}
	}
	/**
	 * Constructor when all players have access to different actions and to change the value of "fine_or_bribe".
	 * @param nb_attacker
	 * @param nb_defender
	 * @param attacker_utility
	 * @param defender_utility
	 * @param lambda
	 * @param possible_actions
	 * @param fine_or_bribe
	 */
	public GSG_SNF(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float[] lambda, ArrayList<ArrayList<Integer>> possible_actions, int fine_or_bribe) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, possible_actions, fine_or_bribe);
		this.dimension = 1;
		for ( ArrayList<Integer> list_actions : possible_actions) {
			this.dimension = this.dimension * list_actions.size();
		}
	}

	/**
	 * @param indice_joueur
	 * @param choix_des_joueurs
	 * @return the utility of the player in "indice_joueur" position depending actions choose by players and methods to compute the utility
	 */
	private Float calcul_utilite(int indice_joueur,ArrayList<Integer> choix_des_joueurs) {
		
		int choix = choix_des_joueurs.get(indice_joueur);
		List<Integer> choix_des_braconniers = choix_des_joueurs.subList(0, this.nb_attacker);
		List<Integer> choix_des_garde_chasse = choix_des_joueurs.subList(this.nb_attacker, choix_des_joueurs.size());
		
		// cas où le joueur est un braconnier
		
		if (indice_joueur < this.nb_attacker) {
			
			switch(this.attacker_utility) {
			
			case "Poach or hide" :
				
				if (choix_des_garde_chasse.contains(choix)) {
					return (float) 0;
				}
				else {
					return this.lambda[choix];
				}
			
			case "Poach and bribe" :
				
				int nb_gc = 0;
				for (int c : choix_des_garde_chasse) {
					if ( c == choix) {
						nb_gc++;
					}
				}
				return this.lambda[choix] - this.fine_or_bribe*nb_gc;
			
			case "Team-poach or hide" :
				
				if (choix_des_garde_chasse.contains(choix)) {
					return (float) 0;
				}
				else {
					
					float nb_braco = 0;
					for (int c : choix_des_braconniers) {
						if ( c == choix) {
							nb_braco++;
						}
					}
					return this.lambda[choix]/nb_braco;
				}
				
			case "Team-poach and bribe" :
				
				int nb_garde = 0;
				for (int c : choix_des_garde_chasse) {
					if ( c == choix) {
						nb_garde++;
					}
				}
				
				float nb_braco = 0;
				for (int c : choix_des_braconniers) {
					if ( c == choix) {
						nb_braco++;
					}
				}
				return this.lambda[choix]/nb_braco - nb_garde;
				
			default :
				System.out.println("Le calcul d'utilité concernant les braconniers n'existe pas");
				break;
			}			
		}
		
		// cas où le joueur est un garde_chasse
		
		else {
			
			switch(this.defender_utility) {
			
			case "Defend the location" :
	
				if (choix_des_braconniers.contains(choix)) {
					return (float) this.fine_or_bribe;
				}
				else {
					return (float) 0;
				}
			
			case "Bribemon : Gotta catch'em all!" :
		
				float nb_b = 0;
				for (int c : choix_des_braconniers) {
					if ( c == choix) {
						nb_b++;
					}
				}
				return nb_b*this.fine_or_bribe;
				
			case "captor" :
				
				float nb_braco = 0;
				float nb_garde = 0;
				for (int c : choix_des_braconniers) {
					if ( c == choix) {
						nb_braco++;
					}
				}
				for (int c : choix_des_garde_chasse) {
					if ( c == choix) {
						nb_garde++;
					}
				}
				return (nb_braco*this.fine_or_bribe)/nb_garde;
				
			case "Avoid poaching" :
				
				float val = 0;
				for (int i=0; i<this.nb_attacker; i++) {
					val -= calcul_utilite(i,choix_des_joueurs);
				}
				return val;
				
			default :
				
				System.out.println("Le calcul d'utilité concernant les gardes chasses n'existe pas");
				break;
				
			}
		}
		return (float) 0;
	}
	
	/**
	 * Compute all utilities value and corresponding profiles.
	 */
	public void calcul_val() {
		ArrayList<Integer> choix_des_joueurs = new ArrayList<Integer>();
	    for (int k=0; k < this.nb_player; k++) {
	    	choix_des_joueurs.add(this.possible_actions.get(k).get(0));
	    }
		for (int i=0; i < this.dimension; i++) {
			float[] uti_tmp = new float[nb_player];
			int changement_action_pour_k = this.dimension/this.possible_actions.get(0).size();
			
			if (i != 0) {
			    for (int k=0; k < this.nb_player; k++) {
			    	
					if (Math.floorMod(i,changement_action_pour_k) == 0) {
						int a = choix_des_joueurs.get(k);
						int ind_actuel = this.possible_actions.get(k).indexOf(a);
						choix_des_joueurs.set(k, this.possible_actions.get(k).get(ind_actuel+1));
					    for (int l=k+1; l < this.nb_player; l++) {
					    	choix_des_joueurs.set(l,0);
					    }
					    break;
					}
					
					else {
						changement_action_pour_k = changement_action_pour_k/this.possible_actions.get(k).size();
					}
			    }
			}
		    
			int[] c = new int[nb_player];
			for (int j=0; j < this.nb_player; j++) {
				uti_tmp[j] = this.calcul_utilite(j,choix_des_joueurs);
				c[j] = choix_des_joueurs.get(j);
			}
			
			this.utilities_value.add(uti_tmp);
			this.profiles.add(c);
		}
	}
	/**
	 * Print information about the game.
	 */
	public void afficher_jeux() {
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
}
