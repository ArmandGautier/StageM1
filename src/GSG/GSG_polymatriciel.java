package GSG;

import java.util.ArrayList;

public class GSG_polymatriciel extends GSG {

	public GSG_polymatriciel(int nb_braconnier, int nb_garde_chasse, int[] ressources, int gain_braconnier, String utilite_calcule) {
		super(nb_braconnier, nb_garde_chasse, ressources, gain_braconnier, utilite_calcule);
		switch(this.utilite_calcule) {
		
		case "gain or zero" : 
			System.out.println("Cette utilité n'est pas compatible avec la forme polymatriciel des Green Security Games");
			break;
		default :
		// decomposer et créer des CSG à deux joueurs ?
		this.matrices_du_jeux = calcul_polymatrices();
		
		}
	}

	private ArrayList<ArrayList> calcul_polymatrices() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
