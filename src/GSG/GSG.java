/**
 * 
 */
package GSG;

import java.util.ArrayList;
import java.util.List;


/**
 * @author agautier
 *
 */
public class GSG {

	// N joueurs, K braconniers et L garde chasse, les K premiers joueurs seront les braconniers et les L derniers joueurs les gardes chasses
	int nb_braconnier;
	int nb_garde_chasse;
	int nb_joueur;
	
	// Liste d'actions représentée sous forme d'entier.
	int actions[];
	
	// gain du braconnier à réaliser une action
	int gain_braconnier;
	
	// indique la méthode de calcul d'utilité qui devra être utilisée
	String utilite_calcule;
	
	// représente les matrices qui représentent le jeux, une matrice par joueur avec les valeurs de son utilité en fonction des choix de chacun
	ArrayList<ArrayList> matrices_du_jeux = new ArrayList<ArrayList>();

	public GSG(int nb_braconnier, int nb_garde_chasse, int[] ressources, int gain_braconnier, String utilite_calcule) {
		this.nb_braconnier = nb_braconnier;
		this.nb_garde_chasse = nb_garde_chasse;
		this.nb_joueur = nb_braconnier+nb_garde_chasse;
		this.actions = ressources;
		this.gain_braconnier = gain_braconnier;
		this.utilite_calcule = utilite_calcule;
		this.matrices_du_jeux = null;
	}
	private Integer calcul_utilite(int indice_joueur,ArrayList<Integer> choix_des_joueurs) {
		switch(this.utilite_calcule) {
		
		case "gain or zero":
			
			// cas où le joueur est un braconnier
			if (indice_joueur < this.nb_braconnier) {
				int choix = choix_des_joueurs.get(indice_joueur);
				List<Integer> choix_des_garde_chasse = choix_des_joueurs.subList(this.nb_braconnier, choix_des_joueurs.size());
				if (choix_des_garde_chasse.contains(choix)) {
					return 0;
				}
				else {
					return this.gain_braconnier;
				}
			}
			// cas où le joueur est un garde_chasse
			else {
				int choix = choix_des_joueurs.get(indice_joueur);
				List<Integer> choix_des_braconniers = choix_des_joueurs.subList(0, this.nb_braconnier);
				if (choix_des_braconniers.contains(choix)) {
					return 1;
				}
				else {
					return 0;
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
				return this.gain_braconnier - nb_gc;
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
				return nb_b;
			}
			
		default :
			System.out.println("Mauvaise utilitée");
			break;
		}
		return 0;
	}

	private ArrayList<ArrayList> calcul_matrice_2joueurs(ArrayList<ArrayList> tab) {
		ArrayList<ArrayList> t0 = new ArrayList<ArrayList>();
		ArrayList<ArrayList> t1 = new ArrayList<ArrayList>();
		for (int a1 : this.actions) {
			ArrayList<Integer> tab_temp0 = new ArrayList<Integer>();
			ArrayList<Integer> tab_temp1 = new ArrayList<Integer>();
			for (int a2 : this.actions) {
				ArrayList<Integer> choix_des_joueurs = new ArrayList<Integer>();
				choix_des_joueurs.add(a1);
				choix_des_joueurs.add(a2);
				tab_temp0.add(calcul_utilite(0,choix_des_joueurs));
				tab_temp1.add(calcul_utilite(1,choix_des_joueurs));
			}
			t0.add(tab_temp0);
			t1.add(tab_temp1);
		}
		tab.add(t0);
		tab.add(t1);
		return tab;
	}
	private ArrayList<ArrayList> calcul_matrice_3joueurs(ArrayList<ArrayList> tab) {
		ArrayList<ArrayList> t0 = new ArrayList<ArrayList>();
		ArrayList<ArrayList> t1 = new ArrayList<ArrayList>();
		ArrayList<ArrayList> t2 = new ArrayList<ArrayList>();
		for (int a1 : this.actions) {
			ArrayList<ArrayList> t0_bis = new ArrayList<ArrayList>();
			ArrayList<ArrayList> t1_bis = new ArrayList<ArrayList>();
			ArrayList<ArrayList> t2_bis = new ArrayList<ArrayList>();
			for (int a2 : this.actions) {
				ArrayList<Integer> tab_temp0 = new ArrayList<Integer>();
				ArrayList<Integer> tab_temp1 = new ArrayList<Integer>();
				ArrayList<Integer> tab_temp2 = new ArrayList<Integer>();
				for (int a3 : this.actions) {
					ArrayList<Integer> choix_des_joueurs = new ArrayList<Integer>();
					choix_des_joueurs.add(a1);
					choix_des_joueurs.add(a2);
					choix_des_joueurs.add(a3);
					tab_temp0.add(calcul_utilite(0,choix_des_joueurs));
					tab_temp1.add(calcul_utilite(1,choix_des_joueurs));
					tab_temp2.add(calcul_utilite(2,choix_des_joueurs));
				}
				t0_bis.add(tab_temp0);
				t1_bis.add(tab_temp1);
				t2_bis.add(tab_temp2);
			}
			t0.add(t0_bis);
			t1.add(t1_bis);
			t2.add(t2_bis);
		}
		tab.add(t0);
		tab.add(t1);
		tab.add(t2);
		return tab;
	}
	private ArrayList<ArrayList> calcul_matrice_4joueurs(ArrayList<ArrayList> tab) {
		ArrayList<ArrayList> t0 = new ArrayList<ArrayList>();
		ArrayList<ArrayList> t1 = new ArrayList<ArrayList>();
		ArrayList<ArrayList> t2 = new ArrayList<ArrayList>();
		ArrayList<ArrayList> t3 = new ArrayList<ArrayList>();
		for (int a1 : this.actions) {
			ArrayList<ArrayList> t0_bis = new ArrayList<ArrayList>();
			ArrayList<ArrayList> t1_bis = new ArrayList<ArrayList>();
			ArrayList<ArrayList> t2_bis = new ArrayList<ArrayList>();
			ArrayList<ArrayList> t3_bis = new ArrayList<ArrayList>();
			for (int a2 : this.actions) {
				ArrayList<ArrayList> t0_ter = new ArrayList<ArrayList>();
				ArrayList<ArrayList> t1_ter = new ArrayList<ArrayList>();
				ArrayList<ArrayList> t2_ter = new ArrayList<ArrayList>();
				ArrayList<ArrayList> t3_ter = new ArrayList<ArrayList>();
				for (int a3 : this.actions) {
					ArrayList<Integer> tab_temp0 = new ArrayList<Integer>();
					ArrayList<Integer> tab_temp1 = new ArrayList<Integer>();
					ArrayList<Integer> tab_temp2 = new ArrayList<Integer>();
					ArrayList<Integer> tab_temp3 = new ArrayList<Integer>();
					for (int a4 : this.actions) {
						ArrayList<Integer> choix_des_joueurs = new ArrayList<Integer>();
						choix_des_joueurs.add(a1);
						choix_des_joueurs.add(a2);
						choix_des_joueurs.add(a3);
						choix_des_joueurs.add(a4);
						tab_temp0.add(calcul_utilite(0,choix_des_joueurs));
						tab_temp1.add(calcul_utilite(1,choix_des_joueurs));
						tab_temp2.add(calcul_utilite(2,choix_des_joueurs));
						tab_temp3.add(calcul_utilite(3,choix_des_joueurs));
					}
					t0_ter.add(tab_temp0);
					t1_ter.add(tab_temp1);
					t2_ter.add(tab_temp2);
					t3_ter.add(tab_temp3);
				}
				t0_bis.add(t0_ter);
				t1_bis.add(t1_ter);
				t2_bis.add(t2_ter);
				t3_bis.add(t3_ter);
			}
			t0.add(t0_bis);
			t1.add(t1_bis);
			t2.add(t2_bis);
			t3.add(t3_bis);
		}
		tab.add(t0);
		tab.add(t1);
		tab.add(t2);
		tab.add(t3);
		return tab;
	}
	private ArrayList<ArrayList> calcul_matrice_5joueurs(ArrayList<ArrayList> tab) {
		ArrayList<ArrayList> t0 = new ArrayList<ArrayList>();
		ArrayList<ArrayList> t1 = new ArrayList<ArrayList>();
		ArrayList<ArrayList> t2 = new ArrayList<ArrayList>();
		ArrayList<ArrayList> t3 = new ArrayList<ArrayList>();
		ArrayList<ArrayList> t4 = new ArrayList<ArrayList>();
		for (int a1 : this.actions) {
			ArrayList<ArrayList> t0_bis = new ArrayList<ArrayList>();
			ArrayList<ArrayList> t1_bis = new ArrayList<ArrayList>();
			ArrayList<ArrayList> t2_bis = new ArrayList<ArrayList>();
			ArrayList<ArrayList> t3_bis = new ArrayList<ArrayList>();
			ArrayList<ArrayList> t4_bis = new ArrayList<ArrayList>();
			for (int a2 : this.actions) {
				ArrayList<ArrayList> t0_ter = new ArrayList<ArrayList>();
				ArrayList<ArrayList> t1_ter = new ArrayList<ArrayList>();
				ArrayList<ArrayList> t2_ter = new ArrayList<ArrayList>();
				ArrayList<ArrayList> t3_ter = new ArrayList<ArrayList>();
				ArrayList<ArrayList> t4_ter = new ArrayList<ArrayList>();
				for (int a3 : this.actions) {
					ArrayList<ArrayList> t0_quat = new ArrayList<ArrayList>();
					ArrayList<ArrayList> t1_quat = new ArrayList<ArrayList>();
					ArrayList<ArrayList> t2_quat = new ArrayList<ArrayList>();
					ArrayList<ArrayList> t3_quat = new ArrayList<ArrayList>();
					ArrayList<ArrayList> t4_quat = new ArrayList<ArrayList>();
					for (int a4 : this.actions) {
						ArrayList<Integer> tab_temp0 = new ArrayList<Integer>();
						ArrayList<Integer> tab_temp1 = new ArrayList<Integer>();
						ArrayList<Integer> tab_temp2 = new ArrayList<Integer>();
						ArrayList<Integer> tab_temp3 = new ArrayList<Integer>();
						ArrayList<Integer> tab_temp4 = new ArrayList<Integer>();
						for (int a5 : this.actions) {
							ArrayList<Integer> choix_des_joueurs = new ArrayList<Integer>();
							choix_des_joueurs.add(a1);
							choix_des_joueurs.add(a2);
							choix_des_joueurs.add(a3);
							choix_des_joueurs.add(a4);
							choix_des_joueurs.add(a5);
							tab_temp0.add(calcul_utilite(0,choix_des_joueurs));
							tab_temp1.add(calcul_utilite(1,choix_des_joueurs));
							tab_temp2.add(calcul_utilite(2,choix_des_joueurs));
							tab_temp3.add(calcul_utilite(3,choix_des_joueurs));
							tab_temp4.add(calcul_utilite(4,choix_des_joueurs));
						}
						t0_quat.add(tab_temp0);
						t1_quat.add(tab_temp1);
						t2_quat.add(tab_temp2);
						t3_quat.add(tab_temp3);
						t4_quat.add(tab_temp4);
					}
					t0_ter.add(t0_quat);
					t1_ter.add(t1_quat);
					t2_ter.add(t2_quat);
					t3_ter.add(t3_quat);
					t4_ter.add(t4_quat);
				}
				t0_bis.add(t0_ter);
				t1_bis.add(t1_ter);
				t2_bis.add(t2_ter);
				t3_bis.add(t3_ter);
				t4_bis.add(t4_ter);
			}
			t0.add(t0_bis);
			t1.add(t1_bis);
			t2.add(t2_bis);
			t3.add(t3_bis);
			t4.add(t4_bis);
		}
		tab.add(t0);
		tab.add(t1);
		tab.add(t2);
		tab.add(t3);
		tab.add(t4);
		return tab;
	}
	private ArrayList<ArrayList> calcul_matrice_6joueurs(ArrayList<ArrayList> tab) {
		ArrayList<ArrayList> t0 = new ArrayList<ArrayList>();
		ArrayList<ArrayList> t1 = new ArrayList<ArrayList>();
		ArrayList<ArrayList> t2 = new ArrayList<ArrayList>();
		ArrayList<ArrayList> t3 = new ArrayList<ArrayList>();
		ArrayList<ArrayList> t4 = new ArrayList<ArrayList>();
		ArrayList<ArrayList> t5 = new ArrayList<ArrayList>();
		for (int a1 : this.actions) {
			ArrayList<ArrayList> t0_bis = new ArrayList<ArrayList>();
			ArrayList<ArrayList> t1_bis = new ArrayList<ArrayList>();
			ArrayList<ArrayList> t2_bis = new ArrayList<ArrayList>();
			ArrayList<ArrayList> t3_bis = new ArrayList<ArrayList>();
			ArrayList<ArrayList> t4_bis = new ArrayList<ArrayList>();
			ArrayList<ArrayList> t5_bis = new ArrayList<ArrayList>();
			for (int a2 : this.actions) {
				ArrayList<ArrayList> t0_ter = new ArrayList<ArrayList>();
				ArrayList<ArrayList> t1_ter = new ArrayList<ArrayList>();
				ArrayList<ArrayList> t2_ter = new ArrayList<ArrayList>();
				ArrayList<ArrayList> t3_ter = new ArrayList<ArrayList>();
				ArrayList<ArrayList> t4_ter = new ArrayList<ArrayList>();
				ArrayList<ArrayList> t5_ter = new ArrayList<ArrayList>();
				for (int a3 : this.actions) {
					ArrayList<ArrayList> t0_quat = new ArrayList<ArrayList>();
					ArrayList<ArrayList> t1_quat = new ArrayList<ArrayList>();
					ArrayList<ArrayList> t2_quat = new ArrayList<ArrayList>();
					ArrayList<ArrayList> t3_quat = new ArrayList<ArrayList>();
					ArrayList<ArrayList> t4_quat = new ArrayList<ArrayList>();
					ArrayList<ArrayList> t5_quat = new ArrayList<ArrayList>();
					for (int a4 : this.actions) {
						ArrayList<ArrayList> t0_cinq = new ArrayList<ArrayList>();
						ArrayList<ArrayList> t1_cinq = new ArrayList<ArrayList>();
						ArrayList<ArrayList> t2_cinq = new ArrayList<ArrayList>();
						ArrayList<ArrayList> t3_cinq = new ArrayList<ArrayList>();
						ArrayList<ArrayList> t4_cinq = new ArrayList<ArrayList>();
						ArrayList<ArrayList> t5_cinq = new ArrayList<ArrayList>();
						for (int a5 : this.actions) {
							ArrayList<Integer> tab_temp0 = new ArrayList<Integer>();
							ArrayList<Integer> tab_temp1 = new ArrayList<Integer>();
							ArrayList<Integer> tab_temp2 = new ArrayList<Integer>();
							ArrayList<Integer> tab_temp3 = new ArrayList<Integer>();
							ArrayList<Integer> tab_temp4 = new ArrayList<Integer>();
							ArrayList<Integer> tab_temp5 = new ArrayList<Integer>();
							for (int a6 : this.actions) {
								ArrayList<Integer> choix_des_joueurs = new ArrayList<Integer>();
								choix_des_joueurs.add(a1);
								choix_des_joueurs.add(a2);
								choix_des_joueurs.add(a3);
								choix_des_joueurs.add(a4);
								choix_des_joueurs.add(a5);
								choix_des_joueurs.add(a6);
								tab_temp0.add(calcul_utilite(0,choix_des_joueurs));
								tab_temp1.add(calcul_utilite(1,choix_des_joueurs));
								tab_temp2.add(calcul_utilite(2,choix_des_joueurs));
								tab_temp3.add(calcul_utilite(3,choix_des_joueurs));
								tab_temp4.add(calcul_utilite(4,choix_des_joueurs));
								tab_temp5.add(calcul_utilite(5,choix_des_joueurs));
							}
							t0_cinq.add(tab_temp0);
							t1_cinq.add(tab_temp1);
							t2_cinq.add(tab_temp2);
							t3_cinq.add(tab_temp3);
							t4_cinq.add(tab_temp4);
							t5_cinq.add(tab_temp5);
						}
						t0_quat.add(t0_cinq);
						t1_quat.add(t1_cinq);
						t2_quat.add(t2_cinq);
						t3_quat.add(t3_cinq);
						t4_quat.add(t4_cinq);
						t5_quat.add(t5_cinq);
					}
					t0_ter.add(t0_quat);
					t1_ter.add(t1_quat);
					t2_ter.add(t2_quat);
					t3_ter.add(t3_quat);
					t4_ter.add(t4_quat);
					t5_ter.add(t5_quat);
				}
				t0_bis.add(t0_ter);
				t1_bis.add(t1_ter);
				t2_bis.add(t2_ter);
				t3_bis.add(t3_ter);
				t4_bis.add(t4_ter);
				t5_bis.add(t5_ter);
			}
			t0.add(t0_bis);
			t1.add(t1_bis);
			t2.add(t2_bis);
			t3.add(t3_bis);
			t4.add(t4_bis);
			t5.add(t5_bis);
		}
		tab.add(t0);
		tab.add(t1);
		tab.add(t2);
		tab.add(t3);
		tab.add(t4);
		tab.add(t5);
		return tab;
	}
	public  ArrayList<ArrayList> getMatrixes_of_game() {
		return matrices_du_jeux;
	}
	public void calcul_matrices() {
		ArrayList<ArrayList> tab = new ArrayList<ArrayList>();
		
		switch(nb_joueur) {
		
		case 2:
			this.matrices_du_jeux = calcul_matrice_2joueurs(tab);
			break;
		case 3:
			this.matrices_du_jeux = calcul_matrice_3joueurs(tab);
			break;
		case 4:
			this.matrices_du_jeux = calcul_matrice_4joueurs(tab);
			break;
		case 5:
			this.matrices_du_jeux = calcul_matrice_5joueurs(tab);
			break;
		case 6:
			this.matrices_du_jeux = calcul_matrice_6joueurs(tab);
			break;
		default:
			System.out.println("Nombre de joueurs non traité");
			break;
		}
		
	}	
	public void afficher_matrices() {
		for (int j=0; j<this.nb_joueur; j++) {
			System.out.println("Matrice donnant l'utilité du joueur " + j + "\n" + this.matrices_du_jeux.get(j));
		}
	}
}
