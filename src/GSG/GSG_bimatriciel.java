package GSG;

import java.util.ArrayList;

public class GSG_bimatriciel extends GSG {

	public GSG_bimatriciel(int nb_braconnier, int nb_garde_chasse, int[] ressources, int gain_braconnier, String utilite_calcule) {
		super(nb_braconnier, nb_garde_chasse, ressources, gain_braconnier, utilite_calcule);
		switch(this.utilite_calcule) {
		
		case "gain or zero" : 
			System.out.println("Cette utilité n'est pas compatible avec la forme polymatriciel des Green Security Games");
			break;
		default :

		this.matrices_du_jeux = calcul_bimatrices();
		
		}
	}

	private ArrayList<ArrayList> calcul_bimatrices() {
		ArrayList<ArrayList> tab = new ArrayList<ArrayList>();
		for (int i=0; i<this.nb_braconnier; i++) {
			for (int j=this.nb_braconnier; j<this.nb_joueur; j++) {
				GSG gsg = new GSG(1,1,this.actions,this.gain_braconnier,this.utilite_calcule);
				tab.add(getMatrixes_of_game());
			}
		}
		return tab;
	}
}