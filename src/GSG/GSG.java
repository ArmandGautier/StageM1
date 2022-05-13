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
public class GSG {

	/**
	 * N players, K attackers and L defenders, the first K players are the attackers
	 * actions define the possible choice for players
	 */
	int nb_braconnier;
	int nb_garde_chasse;
	int nb_joueur;
	float val_ressource[];
	ArrayList<ArrayList<Integer>> ressource_accesible = new ArrayList<ArrayList<Integer>>();
	
	/**
	 * can be "gain or zero" or "gain less number of defender"
	 * define the method use to compute the utility depending the choice's player
	 */
	String utilite_calcule;
	
	/**
	 * represent the number of possible situation
	 */
	int dimension=1;
	
	/**
	 * represent matrixes of utility (one by player) in one dimension
	 */
	ArrayList<int[]> act = new ArrayList<int[]>();
	ArrayList<float[]> uti = new ArrayList<float[]>();
	
	/**
	 * constructor when all players have same possible actions
	 * @param nb_braconnier
	 * @param nb_garde_chasse
	 * @param ressources
	 * @param val_ressource
	 * @param utilite_calcule
	 */
	public GSG(int nb_braconnier, int nb_garde_chasse, ArrayList<Integer> ressources, float[] val_ressource, String utilite_calcule) {
		this.nb_braconnier = nb_braconnier;
		this.nb_garde_chasse = nb_garde_chasse;
		this.nb_joueur = nb_braconnier+nb_garde_chasse;
		this.val_ressource = val_ressource;
		this.utilite_calcule = utilite_calcule;
		this.dimension = (int) (Math.pow(ressources.size(), nb_joueur));
		for (int i=0; i<this.nb_joueur; i++) {
			this.ressource_accesible.add(ressources);
		}
	}
	
	/**
	 * constructor when players have different possible actions
	 * @param nb_braconnier
	 * @param nb_garde_chasse
	 * @param ressources
	 * @param val_ressource
	 * @param utilite_calcule
	 * @param ressource_accessible
	 */
	public GSG(int nb_braconnier, int nb_garde_chasse, float[] val_ressource, String utilite_calcule, ArrayList<ArrayList<Integer>> ressource_accessible) {
		this.nb_braconnier = nb_braconnier;
		this.nb_garde_chasse = nb_garde_chasse;
		this.nb_joueur = nb_braconnier+nb_garde_chasse;
		this.val_ressource = val_ressource;
		this.utilite_calcule = utilite_calcule;
		this.ressource_accesible = ressource_accessible;
		for (int i=0; i<this.nb_joueur; i++) {
			this.dimension = this.dimension*this.ressource_accesible.get(i).size();
		}
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
			
			// cas o� le joueur est un braconnier
			if (indice_joueur < this.nb_braconnier) {
				int choix = choix_des_joueurs.get(indice_joueur);
				List<Integer> choix_des_garde_chasse = choix_des_joueurs.subList(this.nb_braconnier, choix_des_joueurs.size());
				if (choix_des_garde_chasse.contains(choix)) {
					return (float) 0;
				}
				else {
					return this.val_ressource[choix];
				}
			}
			// cas o� le joueur est un garde_chasse
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
			
			// cas o� le joueur est un braconnier
			if (indice_joueur < this.nb_braconnier) {
				int choix = choix_des_joueurs.get(indice_joueur);
				List<Integer> choix_des_garde_chasse = choix_des_joueurs.subList(this.nb_braconnier, choix_des_joueurs.size());
				int nb_gc = 0;
				for (int c : choix_des_garde_chasse) {
					if ( c == choix) {
						nb_gc++;
					}
				}
				return this.val_ressource[choix] - nb_gc;
			}
			// cas o� le joueur est un garde_chasse
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
			System.out.println("Mauvaise utilit�e");
			break;
		}
		return (float) 0;
	}
	
	/**
	 * compute values of utility for every players in all possible situation
	 */
	public void calcul_val() {
		ArrayList<Integer> choix_des_joueurs = new ArrayList<Integer>();
	    for (int k=0; k < this.nb_joueur; k++) {
	    	choix_des_joueurs.add(this.ressource_accesible.get(k).get(0));
	    }
		for (int i=0; i < this.dimension; i++) {
			float[] uti_tmp = new float[nb_joueur];
			int changement_action_pour_k = this.dimension/this.ressource_accesible.get(0).size();
			
			if (i != 0) {
			    for (int k=0; k < this.nb_joueur; k++) {
			    	
					if (Math.floorMod(i,changement_action_pour_k) == 0) {
						int a = choix_des_joueurs.get(k);
						int ind_actuel = this.ressource_accesible.get(k).indexOf(a);
						choix_des_joueurs.set(k, this.ressource_accesible.get(k).get(ind_actuel+1));
					    for (int l=k+1; l < this.nb_joueur; l++) {
					    	choix_des_joueurs.set(l,0);
					    }
					    break;
					}
					
					else {
						changement_action_pour_k = changement_action_pour_k/this.ressource_accesible.get(k).size();
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
			System.out.print(" Utilit�es correspondantes : ");
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
