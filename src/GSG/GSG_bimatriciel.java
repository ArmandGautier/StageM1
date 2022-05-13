package GSG;

import java.util.ArrayList;

public class GSG_bimatriciel extends GSG {
	
	int position;
	int dimension_bimatriciel;
	
	// TO DO : revoir représentation poly.
	
	ArrayList<GSG> representation_bimatriciel = new ArrayList<GSG>();

	/**
	 * @param nb_braconnier
	 * @param nb_garde_chasse
	 * @param ressources
	 * @param gain_braconnier
	 * @param utilite_calcule
	 */
	public GSG_bimatriciel(int nb_braconnier, int nb_garde_chasse, ArrayList<Integer> ressources, float[] val_ressource, String utilite_calcule) {
		super(nb_braconnier, nb_garde_chasse, ressources, val_ressource, utilite_calcule);
		for (int i=0; i<this.val_ressource.length; i++) {
			this.val_ressource[i] = this.val_ressource[i] / this.nb_garde_chasse;
		}
		this.dimension_bimatriciel = (int) (nb_braconnier * nb_garde_chasse * Math.pow(ressources.size(), 2) * 2);
		this.position = 0;
	}

	/**
	 * @return all bi-matrixes representing the game
	 */
	private void calcul_bimatrices() {
		for (int i=0; i<this.nb_braconnier; i++) {
			for (int j=this.nb_braconnier; j<this.nb_joueur; j++) {
				representation_bimatriciel.add(new GSG(1,1,this.ressource_accesible.get(0),this.val_ressource,this.utilite_calcule));
			}
		}
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
			this.calcul_bimatrices();
			break;
		default :
			System.out.println("Cette utilité n'est pas reconnu");
			break;
		}
	}
}