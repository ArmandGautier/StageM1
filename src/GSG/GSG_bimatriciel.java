package GSG;

import java.util.ArrayList;

public class GSG_bimatriciel extends GSG {

	/**
	 * @param nb_braconnier
	 * @param nb_garde_chasse
	 * @param ressources
	 * @param gain_braconnier
	 * @param utilite_calcule
	 */
	public GSG_bimatriciel(int nb_braconnier, int nb_garde_chasse, int[] ressources, float[] gain_braconnier, String utilite_calcule) {
		super(nb_braconnier, nb_garde_chasse, ressources, gain_braconnier, utilite_calcule);
		for (int i=0; i<this.gain_braconnier.length; i++) {
			this.gain_braconnier[i] = this.gain_braconnier[i] / this.nb_garde_chasse;
		}
	}

	/**
	 * @return all bi-matrixes representing the game
	 */
	private ArrayList<ArrayList> calcul_bimatrices() {
		ArrayList<ArrayList> tab = new ArrayList<ArrayList>();
		for (int i=0; i<this.nb_braconnier; i++) {
			for (int j=this.nb_braconnier; j<this.nb_joueur; j++) {
				GSG gsg = new GSG(1,1,this.actions,this.gain_braconnier,this.utilite_calcule);
				gsg.calcul_matrices();
				tab.add(gsg.getMatrixes_of_game());
			}
		}
		return tab;
	}
	
	/**
	 * compute matrixes of game depending of if the method to compute utility is compatible with bi-matrix representation
	 */
	public void calcul_matrices() {
		switch(this.utilite_calcule) {
		case "gain or zero" : 
			System.out.println("Cette utilité n'est pas compatible avec la forme bimatriciel des Green Security Games");
			break;
		case "gain less number of defender" :
			this.matrices_du_jeux = this.calcul_bimatrices();
			break;
		default :
			System.out.println("Cette utilité n'est pas reconnu");
			break;
		}
	}
	
	/**
	 * print bi-matrixes
	 */
	public void afficher_matrices() {
		if (this.matrices_du_jeux.isEmpty()) {
			System.out.println("Les matrices du jeux n'existent pas");
		}
		else {
			int k = 0;
			for (int i=0; i<this.nb_braconnier; i++) {
				for (int j=this.nb_braconnier; j<this.nb_joueur; j++) {
					ArrayList<ArrayList> mat = this.matrices_du_jeux.get(k);
					System.out.println("Bimatrice " + k );
					System.out.println("Matrice donnant l'utilité du braconnier " + i + "\n" + mat.get(0) + "\n");
					System.out.println("Matrice donnant l'utilité du garde chasse " + j + "\n" + mat.get(1) + "\n");
					k++;
				}
			}
		}
	}
}