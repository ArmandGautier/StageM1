package GSG;

import java.util.ArrayList;

public class GSG_bimatriciel extends GSG {

	public GSG_bimatriciel(int nb_braconnier, int nb_garde_chasse, int[] ressources, int gain_braconnier, String utilite_calcule) {
		super(nb_braconnier, nb_garde_chasse, ressources, gain_braconnier, utilite_calcule);
	}

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
}