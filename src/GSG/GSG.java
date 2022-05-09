/**
 * 
 */
package GSG;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class GSG {

	/**
	 * N players, K attackers and L defenders, the first K players are the attackers
	 * actions define the possible choice for players
	 */
	int nb_braconnier;
	int nb_garde_chasse;
	int nb_joueur;
	int actions[];
	float gain_braconnier[];
	
	/**
	 * can be "gain or zero" or "gain less number of defender"
	 * define the method use to compute the utility depending the choice's player
	 */
	String utilite_calcule;
	
	/**
	 * represent the number of possible profile
	 */
	int dimension;
	
	/**
	 * represent all the profiles and their corresponding utilities
	 */
	ArrayList<int[]> act = new ArrayList<int[]>();
	ArrayList<float[]> uti = new ArrayList<float[]>();
	
	/**
	 * @param nb_braconnier
	 * @param nb_garde_chasse
	 * @param ressources
	 * @param gain_braconnier
	 * @param utilite_calcule
	 */
	public GSG(int nb_braconnier, int nb_garde_chasse, int[] ressources, float[] gain_braconnier, String utilite_calcule) {
		this.nb_braconnier = nb_braconnier;
		this.nb_garde_chasse = nb_garde_chasse;
		this.nb_joueur = nb_braconnier+nb_garde_chasse;
		this.actions = ressources;
		this.gain_braconnier = gain_braconnier;
		this.utilite_calcule = utilite_calcule;
		this.dimension = (int) (Math.pow(actions.length, nb_joueur));
	}
	
	/**
	 * @param indice_joueur
	 * @param choix_des_joueurs
	 * @return the utility of the player in "indice_joueur" position
	 *  depending actions choose by players and method to compute the utility
	 */
	private Float calcul_utilite(int indice_joueur,ArrayList<Integer> choix_des_joueurs) {
		switch(this.utilite_calcule) {
		
		case "gain or zero":
			
			// cas où le joueur est un braconnier
			if (indice_joueur < this.nb_braconnier) {
				int choix = choix_des_joueurs.get(indice_joueur);
				List<Integer> choix_des_garde_chasse = choix_des_joueurs.subList(this.nb_braconnier, choix_des_joueurs.size());
				if (choix_des_garde_chasse.contains(choix)) {
					return (float) 0;
				}
				else {
					return this.gain_braconnier[choix];
				}
			}
			// cas où le joueur est un garde_chasse
			else {
				int choix = choix_des_joueurs.get(indice_joueur);
				List<Integer> choix_des_braconniers = choix_des_joueurs.subList(0, this.nb_braconnier);
				if (choix_des_braconniers.contains(choix)) {
					return (float) 1;
				}
				else {
					return (float) 0;
				}
			}
			
		case "gain less number of defender":
			
			// cas où le joueur est un braconnier
			if (indice_joueur < this.nb_braconnier) {
				int choix = choix_des_joueurs.get(indice_joueur);
				List<Integer> choix_des_garde_chasse = choix_des_joueurs.subList(this.nb_braconnier, choix_des_joueurs.size());
				int nb_gc = 0;
				for (int c : choix_des_garde_chasse) {
					if ( c == choix) {
						nb_gc++;
					}
				}
				return this.gain_braconnier[choix] - nb_gc;
			}
			// cas où le joueur est un garde_chasse
			else {
				int choix = choix_des_joueurs.get(indice_joueur);
				List<Integer> choix_des_braconniers = choix_des_joueurs.subList(0, this.nb_braconnier);
				int nb_b = 0;
				for (int c : choix_des_braconniers) {
					if ( c == choix) {
						nb_b++;
					}
				}
				return (float) nb_b;
			}
			
		default :
			System.out.println("Mauvaise utilitée");
			break;
		}
		return (float) 0;
	}
	
	/**
	 * compute values of utility for every players in all possible profiles
	 */
	public void calcul_val() {
		ArrayList<Integer> choix_des_joueurs = new ArrayList<Integer>();
	    for (int k=0; k < this.nb_joueur; k++) {
	    	choix_des_joueurs.add(0);
	    }
		for (int i=0; i < this.dimension; i++) {
			float[] uti_tmp = new float[nb_joueur];
			int changement_action_pour_k = this.dimension/this.actions.length;
			
			if (i != 0) {
			    for (int k=0; k < this.nb_joueur; k++) {
			    	
					if (Math.floorMod(i,changement_action_pour_k) == 0) {
						choix_des_joueurs.set(k, choix_des_joueurs.get(k)+1);
					    for (int l=k+1; l < this.nb_joueur; l++) {
					    	choix_des_joueurs.set(l,0);
					    }
					    break;
					}
					
					else {
						changement_action_pour_k = changement_action_pour_k/this.actions.length;
					}
			    }
			}
		    
			int[] c = new int[nb_joueur];
			for (int j=0; j < this.nb_joueur; j++) {
				uti_tmp[j] = calcul_utilite(j,choix_des_joueurs);
				c[j] = choix_des_joueurs.get(j);
			}
			
			uti.add(uti_tmp);
			act.add(c);
		}
	}
	
	/**
	 * some print function 
	 */
	
	public void afficher_int(ArrayList<int[]> t) {
		for (int[] tab : t) {
			for (int i=0; i<tab.length; i++) {
				System.out.println(tab[i]);
			}
		}
	}
	
	public void afficher_float(ArrayList<float[]> t) {
		for (float[] tab : t) {
			for (int i=0; i<tab.length; i++) {
				System.out.println(tab[i]);
			}
		}
	}
	
	public void afficher_jeux() {
		ListIterator<int[]> it1 = this.act.listIterator();
		ListIterator<float[]> it2 = this.uti.listIterator();
		
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
	 * @return the act
	 */
	public ArrayList<int[]> getAct() {
		return act;
	}

	/**
	 * @return the uti
	 */
	public ArrayList<float[]> getUti() {
		return uti;
	}
}
