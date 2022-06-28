package GSG;

import java.util.ArrayList;
import java.util.Random;

public class Universe {

	int nb_location;
	int nb_attacker;
	int nb_defender;
	int nb_herd;
	ArrayList<ArrayList<Integer>> possible_actions = new ArrayList<ArrayList<Integer>>();
	ArrayList<int[][]> focal_elements = new ArrayList<int[][]>();
	float[] mass_function;
	int[] herd_value;
	int[] see_function;
	int[] gps;
	
	/**
	 * @param nb_location
	 * @param nb_herd
	 * @param nb_attacker
	 * @param nb_defender
	 * The nb_attacker MUST BE greater or equal to nb_location-1 ( for bijectivity )
	 */
	public Universe(int nb_location, int nb_herd, int nb_attacker, int nb_defender) {
		this.nb_location = nb_location;
		this.nb_herd = nb_herd;
		this.nb_attacker = nb_attacker;
		this.nb_defender = nb_defender;
		Random random = new Random();
		
		/** Construction of see_function */
		
		this.see_function = new int[this.nb_attacker];
		
		// On cr�� une liste de lieux de taille nb_location-1 correspondant aux lieux qui doivent �tre vue
		// et une liste de lieux correspondant � l'ensemble des lieux
		ArrayList<Integer> locationMustBeSee = new ArrayList<Integer>();
		ArrayList<Integer> location = new ArrayList<Integer>();
		for ( int l=0; l<this.nb_location; l++) {
			locationMustBeSee.add(l);
			location.add(l);
		}
		
		// on retire au hasard un des lieux qui doit �tre vue ( car nb_location - 1 lieux doivent �tre vu pour la bijectivit� )
		// int index = random.nextInt(0, this.nb_location);
		int index = random.nextInt(this.nb_location);
		locationMustBeSee.remove(index);
		// Pour chaque attaquant on d�termine le lieux qu'il verra
		for (int j=0; j<this.nb_attacker; j++) {
			if (!locationMustBeSee.isEmpty()) {
				// index = random.nextInt(0, locationMustBeSee.size());
				index = random.nextInt(locationMustBeSee.size());
				this.see_function[j] = locationMustBeSee.get(index);
				locationMustBeSee.remove(index);
			}
			else {
				// index = random.nextInt(0, this.nb_location);
				index = random.nextInt(this.nb_location);
				this.see_function[j] = location.get(index);
			}
		}
		
		/** Construction of gps */ //� rajouter un param
		
		
		// pour chaque troupeau on tire au hasard si il est puc� ou pas
		ArrayList<Integer> herdChipped = new ArrayList<Integer>();
		for (int t=0; t<this.nb_herd; t++) {
			boolean isChipped = random.nextBoolean();
			if (isChipped) {
				herdChipped.add(t);
			}
		}
		
		// on construit la liste des troupeux puc�s
		this.gps = new int[herdChipped.size()];
		for (int t=0; t<herdChipped.size(); t++) {
			this.gps[t] = herdChipped.get(t);
		}
		
		/** Construction of herd_value */
		
		this.herd_value = new int[this.nb_herd];
		
		for (int t=0; t<this.nb_herd; t++) {
			// int value = random.nextInt(1, 10);
			int value = 1 + random.nextInt(9);
			this.herd_value[t] = value;
		}
		
		/** Construction of focal_elements */
		
		Site site = new Site(this.nb_location,this.nb_herd);
		this.focal_elements = site.getFocalElements();
		
		/** Construction of mass_function */
		
		this.mass_function = site.mass_function;
		
		/** Construction of possible_actions */
		
		for (int i=0; i<this.nb_attacker+this.nb_defender; i++) {
			ArrayList<Integer> temp = new ArrayList<Integer>();
			for (int j=0; j<this.nb_location; j++) {
				temp.add(j);
			}
			this.possible_actions.add(temp);
		}
	}

	/**
	 * @return the possible_actions
	 */
	public ArrayList<ArrayList<Integer>> getPossible_actions() {
		return possible_actions;
	}

	/**
	 * @return the nb_location
	 */
	public int getNb_location() {
		return nb_location;
	}

	/**
	 * @return the nb_attacker
	 */
	public int getNb_attacker() {
		return nb_attacker;
	}

	/**
	 * @return the nb_defender
	 */
	public int getNb_defender() {
		return nb_defender;
	}

	/**
	 * @return the nb_herd
	 */
	public int getNb_herd() {
		return nb_herd;
	}

	/**
	 * @return the focal_elements
	 */
	public ArrayList<int[][]> getFocal_elements() {
		return focal_elements;
	}

	/**
	 * @return the mass_function
	 */
	public float[] getMass_function() {
		return mass_function;
	}

	/**
	 * @return the see_function
	 */
	public int[] getSee_function() {
		return see_function;
	}
	
	/**
	 * @return the herd_value
	 */
	public int[] getHerd_value() {
		return herd_value;
	}

	/**
	 * @return the gps
	 */
	public int[] getGps() {
		return gps;
	}

}
