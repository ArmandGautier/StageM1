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
	int fine_or_bribe;
	
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
		this.fine_or_bribe = random.nextInt(1,6);
		
		/** Construction of see_function */
		
		this.see_function = new int[this.nb_attacker];
		
		// On créé une liste de lieux de taille nb_location-1 correspondant aux lieux qui doivent être vue
		// et une liste de lieux correspondant à l'ensemble des lieux
		ArrayList<Integer> locationMustBeSee = new ArrayList<Integer>();
		ArrayList<Integer> location = new ArrayList<Integer>();
		for ( int l=0; l<this.nb_location; l++) {
			locationMustBeSee.add(l);
			location.add(l);
		}
		
		// on retire au hasard un des lieux qui doit être vue ( car nb_location - 1 lieux doivent être vu pour la bijectivité )
		int index = random.nextInt(0, this.nb_location);
		locationMustBeSee.remove(index);
		// Pour chaque attaquant on détermine le lieux qu'il verra
		for (int j=0; j<this.nb_attacker; j++) {
			if (!locationMustBeSee.isEmpty()) {
				index = random.nextInt(0, locationMustBeSee.size());
				this.see_function[j] = locationMustBeSee.get(index);
				locationMustBeSee.remove(index);
			}
			else {
				index = random.nextInt(0, this.nb_location);
				this.see_function[j] = location.get(index);
			}
		}
		
		/** Construction of gps */ //à rajouter un param
		
		
		// pour chaque troupeau on tire au hasard si il est pucé ou pas
		ArrayList<Integer> herdChipped = new ArrayList<Integer>();
		for (int t=0; t<this.nb_herd; t++) {
			boolean isChipped = random.nextBoolean();
			if (isChipped) {
				herdChipped.add(t);
				break;
			}
		}
		
		// on construit la liste des troupeux pucés
		this.gps = new int[herdChipped.size()];
		for (int t=0; t<herdChipped.size(); t++) {
			this.gps[t] = herdChipped.get(t);
		}
		
		/** Construction of herd_value */
		
		this.herd_value = new int[this.nb_herd];
		
		for (int t=0; t<this.nb_herd; t++) {
			int value = random.nextInt(1, 10);
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
	 * @return the fine_or_bribe
	 */
	public int getFine_or_bribe() {
		return fine_or_bribe;
	}

	/**
	 * @return the gps
	 */
	public int[] getGps() {
		return gps;
	}
	
	public String toString() {
		String res = "";
		res += "nb_location : " +nb_location;
		res += "\n";
		res += "nb_attacker : " +nb_attacker;
		res += "\n";
		res += "nb_defender : " +nb_defender;
		res += "\n";
		res += "nb_herd : " +nb_herd;
		res += "\n";
		for (int i=0; i<this.focal_elements.size(); i++) {
			res += " element focal numéro " + i;
			res += "\n";
			for (int[] omega : this.focal_elements.get(i)) {
				for (int lieu : omega) {
					res += lieu;
				}
				res += "\n";
			}
			res += "\n";
		}
		res += "\n";
		res += " Mass Function : ";
		for (int i=0; i<this.focal_elements.size(); i++) {
			res += mass_function[i] + " ";
		}
		res += "\n";
		res += " Herds Value : ";
		for (int i=0; i<this.nb_herd; i++) {
			res += herd_value[i] + " ";
		}
		res += "\n";
		res += " See Function : ";
		for (int i=0; i<nb_attacker; i++) {
			res += see_function[i] + " ";
		}
		res += "\n";
		res += " Gps : ";
		for (int i=0; i<gps.length; i++) {
			res += gps[i] + " ";
		}
		res += "\n";
		res += "fine_or_bribe" +this.fine_or_bribe;
		return res;
	}

}
