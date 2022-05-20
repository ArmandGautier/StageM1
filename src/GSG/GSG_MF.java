package GSG;

import java.util.ArrayList;
import java.util.ListIterator;

public class GSG_MF extends GSG_SNF {
	/**
	 * GSG in SNf ( each one corresponding to one possible world )
	 */
	ArrayList<GSG_SNF> gsg_snf = new ArrayList<GSG_SNF>();
	/**
	 *  a list of focal element, elt_focaux.get(i) give the focal element who take the value val_m[i]
	 */
	ArrayList<int[]> focal_element = new ArrayList<int[]>();
	/**
	 * a list of float to represent a mass function
	 */
	float[] mass_function;
	/**
	 * The method use to compute the total utility considering all the possible world
	 */
	String method;
	/**
	 * Value of alpha used in the method JEU, can be change
	 */
	double alpha = 0.5;

	public GSG_MF(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float lambda, int[] actions, ArrayList<int[]> focal_element, float[] mass_function, String method) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, actions);
		this.focal_element = focal_element;
		this.mass_function = mass_function;
		this.method = method;
		this.dimension = (int) (Math.pow(actions.length, this.nb_player));
	}
	public GSG_MF(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float[] lambda, int[] actions, ArrayList<int[]> focal_element, float[] mass_function, String method) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, actions);
		this.focal_element = focal_element;
		this.mass_function = mass_function;
		this.method = method;
		this.dimension = (int) (Math.pow(actions.length, this.nb_player));
	}
	public GSG_MF(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float lambda, ArrayList<ArrayList<Integer>> possible_actions, ArrayList<int[]> focal_element, float[] mass_function, String method) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, possible_actions);
		this.focal_element = focal_element;
		this.mass_function = mass_function;
		this.method = method;
		this.dimension = 1;
		for ( ArrayList<Integer> list_actions : possible_actions) {
			this.dimension = this.dimension * list_actions.size();
		}
	}
	public GSG_MF(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float[] lambda, ArrayList<ArrayList<Integer>> possible_actions, ArrayList<int[]> focal_element, float[] mass_function, String method) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, possible_actions);
		this.focal_element = focal_element;
		this.mass_function = mass_function;
		this.method = method;
		this.dimension = 1;
		for ( ArrayList<Integer> list_actions : possible_actions) {
			this.dimension = this.dimension * list_actions.size();
		}
	}
	public GSG_MF(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float lambda, int[] actions, ArrayList<int[]> focal_element, float[] mass_function, String method, int fine_or_bribe) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, actions, fine_or_bribe);
		this.focal_element = focal_element;
		this.mass_function = mass_function;
		this.method = method;
		this.dimension = (int) (Math.pow(actions.length, this.nb_player));
	}
	public GSG_MF(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float[] lambda, int[] actions, ArrayList<int[]> focal_element, float[] mass_function, String method, int fine_or_bribe) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, actions, fine_or_bribe);
		this.focal_element = focal_element;
		this.mass_function = mass_function;
		this.method = method;
		this.dimension = (int) (Math.pow(actions.length, this.nb_player));
	}
	public GSG_MF(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float lambda, ArrayList<ArrayList<Integer>> possible_actions, ArrayList<int[]> focal_element, float[] mass_function, String method, int fine_or_bribe) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, possible_actions, fine_or_bribe);
		this.focal_element = focal_element;
		this.mass_function = mass_function;
		this.method = method;
		this.dimension = 1;
		for ( ArrayList<Integer> list_actions : possible_actions) {
			this.dimension = this.dimension * list_actions.size();
		}
	}
	public GSG_MF(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float[] lambda, ArrayList<ArrayList<Integer>> possible_actions, ArrayList<int[]> focal_element, float[] mass_function, String method, int fine_or_bribe) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, possible_actions, fine_or_bribe);
		this.focal_element = focal_element;
		this.mass_function = mass_function;
		this.method = method;
		this.dimension = 1;
		for ( ArrayList<Integer> list_actions : possible_actions) {
			this.dimension = this.dimension * list_actions.size();
		}
	}
	public GSG_MF(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float lambda, int[] actions, ArrayList<int[]> focal_element, float[] mass_function, String method, double alpha) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, actions);
		this.focal_element = focal_element;
		this.mass_function = mass_function;
		this.method = method;
		this.alpha = alpha;
		this.dimension = (int) (Math.pow(actions.length, this.nb_player));
	}
	public GSG_MF(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float[] lambda, int[] actions, ArrayList<int[]> focal_element, float[] mass_function, String method, double alpha) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, actions);
		this.focal_element = focal_element;
		this.mass_function = mass_function;
		this.method = method;
		this.alpha = alpha;
		this.dimension = (int) (Math.pow(actions.length, this.nb_player));
	}
	public GSG_MF(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float lambda, ArrayList<ArrayList<Integer>> possible_actions, ArrayList<int[]> focal_element, float[] mass_function, String method, double alpha) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, possible_actions);
		this.focal_element = focal_element;
		this.mass_function = mass_function;
		this.method = method;
		this.alpha = alpha;
		this.dimension = 1;
		for ( ArrayList<Integer> list_actions : possible_actions) {
			this.dimension = this.dimension * list_actions.size();
		}
	}
	public GSG_MF(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float[] lambda, ArrayList<ArrayList<Integer>> possible_actions, ArrayList<int[]> focal_element, float[] mass_function, String method, double alpha) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, possible_actions);
		this.focal_element = focal_element;
		this.mass_function = mass_function;
		this.method = method;
		this.alpha = alpha;
		this.dimension = 1;
		for ( ArrayList<Integer> list_actions : possible_actions) {
			this.dimension = this.dimension * list_actions.size();
		}
	}
	public GSG_MF(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float lambda, int[] actions, ArrayList<int[]> focal_element, float[] mass_function, String method, int fine_or_bribe, double alpha) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, actions, fine_or_bribe);
		this.focal_element = focal_element;
		this.mass_function = mass_function;
		this.method = method;
		this.alpha = alpha;
		this.dimension = (int) (Math.pow(actions.length, this.nb_player));
	}
	public GSG_MF(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float[] lambda, int[] actions, ArrayList<int[]> focal_element, float[] mass_function, String method, int fine_or_bribe, double alpha) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, actions, fine_or_bribe);
		this.focal_element = focal_element;
		this.mass_function = mass_function;
		this.method = method;
		this.alpha = alpha;
		this.dimension = (int) (Math.pow(actions.length, this.nb_player));
	}
	public GSG_MF(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float lambda, ArrayList<ArrayList<Integer>> possible_actions, ArrayList<int[]> focal_element, float[] mass_function, String method, int fine_or_bribe, double alpha) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, possible_actions, fine_or_bribe);
		this.focal_element = focal_element;
		this.mass_function = mass_function;
		this.method = method;
		this.alpha = alpha;
		this.dimension = 1;
		for ( ArrayList<Integer> list_actions : possible_actions) {
			this.dimension = this.dimension * list_actions.size();
		}
	}
	public GSG_MF(int nb_attacker, int nb_defender, String attacker_utility, String defender_utility, float[] lambda, ArrayList<ArrayList<Integer>> possible_actions, ArrayList<int[]> focal_element, float[] mass_function, String method, int fine_or_bribe, double alpha) {
		super(nb_attacker, nb_defender, attacker_utility, defender_utility, lambda, possible_actions, fine_or_bribe);
		this.focal_element = focal_element;
		this.mass_function = mass_function;
		this.method = method;
		this.alpha = alpha;
		this.dimension = 1;
		for ( ArrayList<Integer> list_actions : possible_actions) {
			this.dimension = this.dimension * list_actions.size();
		}
	}
	
	/**
	 * compute gsg in SNF for each possible world ( one world = we consider that one resource is active )
	 */
	private void create_snf_gsg() {
		for (int i=0; i<this.lambda.length; i++) {
			float[] lambda_bis = this.lambda.clone();
			for (int j=0; j<lambda_bis.length; j++) {
				if (j!=i) {
					lambda_bis[j] = (float) 0.0;
				}
			}
			GSG_SNF gsg = new GSG_SNF(this.nb_attacker, this.nb_defender, this.defender_utility, this.attacker_utility, lambda_bis, this.possible_actions);
			gsg.calcul_val();
			this.gsg_snf.add(gsg);
		}
	}
	
	public void calcul_val() {
		create_snf_gsg();
		
		switch(this.method) {
		case "CEU" :
			calcul_val_using_CEU();
			break;
		case "JEU" :
			calcul_val_using_JEU();
			break;
		case "TBEU" :
			calcul_val_using_TBEU();
			break;
		default : 
			System.out.println("Cette méthode n'existe pas ou n'est pas implémentée");
			break;
		}
	}

	private void calcul_val_using_TBEU() {
		for (int i=0; i < this.dimension; i++) {
			int j=0;
			float[] uti_tmp = new float[this.nb_player];
			for (int[] e : this.focal_element) {
				for (int k=0; k<this.nb_player; k++) {
					float uti = 0;
					for (int elt : e) {
						uti += this.gsg_snf.get(elt).getUtilities().get(i)[k];
					}
					uti = uti/e.length;
					uti_tmp[k] += this.mass_function[j] * uti;
				}
				j++;
			}
			this.utilities_value.add(uti_tmp);
			this.profiles.add(this.gsg_snf.get(0).getProfiles().get(i));
		}	
	}

	private void calcul_val_using_JEU() {
		for (int i=0; i < this.dimension; i++) {
			int j=0;
			float[] uti_tmp = new float[nb_player];
			for (int[] e : this.focal_element) {
				for (int k=0; k<this.nb_player; k++) {
					float min_uti = Float.MAX_VALUE;
					float max_uti = 0;
					for (int elt : e) {
						float to_compare = this.gsg_snf.get(elt).getUtilities().get(i)[k];
						if ( to_compare < min_uti) {
							min_uti = to_compare;
						}
						if ( to_compare > max_uti) {
							max_uti = to_compare;
						}
					}
					uti_tmp[k] += this.mass_function[j] * ( this.alpha*min_uti + (1-this.alpha)*max_uti);
				}
				j++;
			}
			this.utilities_value.add(uti_tmp);
			this.profiles.add(this.gsg_snf.get(0).getProfiles().get(i));
		}	
	}

	private void calcul_val_using_CEU() {
		for (int i=0; i < this.dimension; i++) {
			int j=0;
			float[] uti_tmp = new float[nb_player];
			for (int[] e : this.focal_element) {
				for (int k=0; k<this.nb_player; k++) {
					float min_uti = Float.MAX_VALUE;
					for (int elt : e) {
						float to_compare = this.gsg_snf.get(elt).getUtilities().get(i)[k];
						if ( to_compare < min_uti) {
							min_uti = to_compare;
						}
					}
					uti_tmp[k] += this.mass_function[j] * min_uti;
				}
				j++;
			}
			this.utilities_value.add(uti_tmp);
			this.profiles.add(this.gsg_snf.get(0).getProfiles().get(i));
		}	
	}

	public void afficher_jeux() {
		int ind_g = 0;
		for (GSG_SNF g : this.gsg_snf) {
			System.out.println("Local game corresponding to the presence of the herd in place " + ind_g);
			g.afficher_jeux();
			ind_g++;
		}
		System.out.println("Add of local games by "+this.method+" :");
		ListIterator<int[]> it1 = this.profiles.listIterator();
		ListIterator<float[]> it2 = this.utilities_value.listIterator();
		while (it1.hasNext() && it2.hasNext()) {
			int[] tab1 = it1.next();
			float[] tab2 = it2.next();
			System.out.print("Profile : ");
			for (int i=0; i<tab1.length; i++) {
				System.out.print(tab1[i] + " ");
			}
			System.out.print(" Corresponding utility : ");
			for (int j=0; j<tab2.length; j++) {
				System.out.print(tab2[j] + " ");
			}
			System.out.println();
		}
	}
	
	
}
