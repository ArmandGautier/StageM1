package GSG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Site {
	
	int nb_location;
	int nb_herd;
	int[] herdLocation;
	float[] mass_function;
	float[] probaStay;
	Map<Integer,ArrayList<Integer>> graphe = new HashMap<>();

	public Site(int nb_location, int nb_herd) {
		this.nb_location = nb_location;
		this.nb_herd = nb_herd;
		this.herdLocation = new int[this.nb_herd];
		Random random = new Random();
		
		for (int i=0; i<this.nb_herd; i++) {
			// int location = random.nextInt(0, this.nb_location);
			int location = random.nextInt(this.nb_location);
			this.herdLocation[i] = location;
			boolean pStay = random.nextBoolean();
			if (pStay) {
				probaStay[i] = 1/2;
			}
			else {
				probaStay[i] = 1/4;
			}
		}
		
		for (int i=0; i<this.nb_location; i++) {
			if ( i==0 ) {
				ArrayList<Integer> sommet = new ArrayList<Integer>();
				sommet.add(i+1);
				this.graphe.put(i, sommet);
			}
			else if ( i==this.nb_location-1) {
				ArrayList<Integer> sommet = new ArrayList<Integer>();
				sommet.add(i-1);
				this.graphe.put(i, sommet);
			}
			else {
				ArrayList<Integer> sommet = new ArrayList<Integer>();
				sommet.add(i-1);
				sommet.add(i+1);
				this.graphe.put(i, sommet);
			}
		}
		
		for (int i=0; i<this.nb_location-1; i++) {
			for (int j=i+2; j<this.nb_location-1; j++) {
				boolean b = random.nextBoolean();
				if (b) {
					ArrayList<Integer> s1 = this.graphe.get(i);
					ArrayList<Integer> s2 = this.graphe.get(j);
					s1.add(j);
					s2.add(i);
					this.graphe.put(i, s1);
					this.graphe.put(j, s2);
				}
			}
		}
	}
	
	private ArrayList<Integer> moove(int herdIndex, ArrayList<Integer> res) {
		for (Integer location : this.graphe.get(herdIndex)) {
			res.add(location);
		}
		return res;
	}

	public ArrayList<int[][]> getFocalElements() {
	
		ArrayList<int[][]> focal_elements = new ArrayList<int[][]>();
		ArrayList<String> mooveOfHerd = new ArrayList<String>();
		
	    for (int k=0; k < this.nb_herd; k++) {
	    	mooveOfHerd.add("Stay");
	    }
	    
	    int nb_focal_element = (int) (Math.pow(2, this.nb_herd));
	    this.mass_function = new float[nb_focal_element];
	    
		for (int i=0; i < nb_focal_element; i++) {
			int changement_action_pour_k = nb_focal_element/2;
			
			if (i != 0) {
			    for (int k=0; k < this.nb_herd; k++) {
			    	
					if (Math.floorMod(i,changement_action_pour_k) == 0) {
						mooveOfHerd.set(k, "Moove");
					    for (int l=k+1; l < this.nb_herd; l++) {
					    	mooveOfHerd.set(l,"Stay");
					    }
					    break;
					}
					
					else {
						changement_action_pour_k = changement_action_pour_k/2;
					}
			    }
			}
			
			focal_elements.add(giveFocalElement(mooveOfHerd));
			float val = 1;
			for (int t=0; t<this.nb_herd; t++) {
				if (mooveOfHerd.get(t).equals("Stay")) {
					val = val*this.probaStay[t];
				}
				else {
					val = val*(1-this.probaStay[t]);
				}
			}
		}
		return focal_elements;
	}

	private int[][] giveFocalElement(ArrayList<String> moveOfHerd) {
		int nbPossibleOmega = 1;
		ArrayList<ArrayList<Integer>> locationPossible = new ArrayList<ArrayList<Integer>>();
		
		int herdIndex = 0;
		for (String s : moveOfHerd) {
			ArrayList<Integer> l = new ArrayList<Integer>();
			if (s.equals("Moove")) {
				l = moove(herdIndex,l);
				nbPossibleOmega = nbPossibleOmega * l.size();
			}
			else {
				l.add(this.herdLocation[herdIndex]);
			}
			locationPossible.add(l);
		}
		
		int[][] element = new int[nbPossibleOmega][this.nb_herd];
		ArrayList<Integer> possibleHerdLocation = new ArrayList<Integer>();
		
	    for (int k=0; k < this.nb_herd; k++) {
	    	possibleHerdLocation.add(locationPossible.get(k).get(0));
	    }
		
		for (int i=0; i<nbPossibleOmega; i++) {
			int changement_action_pour_k = nbPossibleOmega/locationPossible.get(0).size();
			
			if (i != 0) {
			    for (int k=0; k < this.nb_herd; k++) {
			    	
					if (Math.floorMod(i,changement_action_pour_k) == 0) {
						int a = possibleHerdLocation.get(k);
						int ind_actuel = locationPossible.get(k).indexOf(a);
						possibleHerdLocation.set(k, locationPossible.get(k).get(ind_actuel+1));
					    for (int l=k+1; l < this.nb_herd; l++) {
					    	possibleHerdLocation.set(l,locationPossible.get(k).get(0));
					    }
					    break;
					}
					
					else {
						changement_action_pour_k = changement_action_pour_k/locationPossible.get(k).size();
					}
			    }
			}
			
			for (int t=0; t<this.nb_herd; t++) {
				element[i][t] = possibleHerdLocation.get(t);
			}
		}
		return element;
	}
	
	/**
	 * @return the mass_function
	 */
	public float[] getMass_function() {
		return mass_function;
	}
}
