package Games;

import java.util.ArrayList;

import Tools.Type;
import Tools.UsefulFunctions;
import Transformations.Transformation;
import Methods.MethodForAttacker;
import Methods.MethodForDefender;
import Tools.Node;
import Tools.Profile;
import XEUs.XEU;

public class BEL_GSG extends GSG {
	
	/**
	 * GSG in SNF to represent all possibles worlds
	 */
	protected ArrayList<GSG_SNF> gsg_snf = new ArrayList<GSG_SNF>();
	/**
	 * a list of all the possible world
	 */
	protected ArrayList<int[]> possible_omega = new ArrayList<int[]>();
	/**
	 * represents all players of the game
	 */
	protected ArrayList<Node> nodes = new ArrayList<Node>();
	/**
	 *  a list of focal element, focal_element.get(i) give the focal element who take the value val_m[i]
	 */
	protected ArrayList<int[][]> focal_element = new ArrayList<int[][]>();
	/**
	 * a list of float to represent a mass function
	 */
	protected float[] mass_function;
	/**
	 * Value of herds
	 */
	protected int[] herd;
	/**
	 * number of location in the game
	 */
	protected int nb_location;
	/**
	 * give information about place see by poachers
	 */
	protected int[] seeFunction;
	/**
	 * number of herds who are chipped by defender
	 */
	protected int[] gps;
	/**
	 * give the XEU method use for this game
	 */
	protected XEU xeu;
	/**
	 * give the transformation to use for this game
	 */
	protected Transformation transfo;


	/**
	 * @param nb_attacker
	 * @param nb_defender
	 * @param attacker_utility
	 * @param defender_utility
	 * @param possible_actions
	 * @param herd
	 * @param fine_or_bribe
	 * @param nb_location
	 * @param focal_element
	 * @param mass_function
	 * @param gps
	 * @param xeu
	 */
	public BEL_GSG(int nb_attacker, int nb_defender, MethodForAttacker attMethod, MethodForDefender defMethod, ArrayList<ArrayList<Integer>> possibleActions, int[] herd, int nb_location, ArrayList<int[][]> focal_element, float[] mass_function, int[] seeFunction, int[] gps, XEU xeu, Transformation transfo) {
		super(nb_attacker, nb_defender, possibleActions, attMethod, defMethod);
		this.herd = herd;
		this.nb_location = nb_location;
		this.focal_element = focal_element;
		this.mass_function = mass_function;
		this.seeFunction = seeFunction;
		this.gps = gps;
		this.xeu = xeu;
		this.transfo = transfo;
		
		create_snf_gsg();
		create_noeuds();
		
	}
	
	/**
	 * construct a GSG in SNF for each possible word
	 */
	private void create_snf_gsg() {
		for (int[][] element : this.focal_element) {
			for (int[] omega : element) {
				float[] lambda = new float[nb_location];
				for (int i=0; i<omega.length; i++) {
					lambda[omega[i]] += herd[i];
				}
				GSG_SNF gsg = new GSG_SNF(this.nb_attacker, this.nb_defender, lambda, this.possibleActions, this.attMethod, this.defMethod);
				gsg.computeUtilities();
				this.gsg_snf.add(gsg);
				this.possible_omega.add(omega);
			}
		}
	}
	
	/**
	 * create all players, a player is a two-uplets (index_player,type_of_player)
	 */
	private void create_noeuds() {
		for (int[] omega : this.possible_omega) {
			for (int i=0; i<this.nb_player; i++) {
				Node n = new Node(i,omegaToTypes(omega,i));
				if (!containsNode(n)) {
					this.nodes.add(n);
				}
			}
		}
	}
	
	/**
	 * @param n
	 * @return true if the node n is already in the list of nodes, false otherwise
	 */
	private boolean containsNode(Node n) {
		for (Node node : this.nodes) {
			if (node.equals(n)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param omega
	 * @param ind_player
	 * @return the type of player "ind_player" depending the world omega
	 */
	public Type omegaToTypes(int[] omega, int ind_player) {
		Type res = new Type();
		if (ind_player < this.nb_attacker) {
			int herd_number = 0;
			for (int o : omega) {
				if ( o == see(ind_player)) {
					res.add("T"+herd_number);
				}
				herd_number ++;
			}
		}
		else {
			for (int herd : gps) {
				res.add("L"+omega[herd]);
			}
		}
		if ( res.getSet().isEmpty() ) {
			res.add("ensemble vide");
			return res;
		}
		else {
			return res;
		}
	}
	
	/**
	 * @param ind_player
	 * @return herds that player "ind_player" can see
	 */
	private int see(int ind_player) {
		return this.seeFunction[ind_player];
	}
	
	/**
	 * @param player
	 * @return
	 */
	public float computeK(Node player) {
		float k = 0;
		int i = 0;
		for (int[][] elt_focal : this.focal_element) {
			if (typeIsTrue(player,elt_focal)) {
				k+=this.mass_function[i];
			}
			i++;
		}
		return 1/k;
	}
	
	/**
	 * @param node
	 * @param focal_elt
	 * @return true if there is unless one element in "focal_elt" in which the type of the node exist
	 */
	public boolean typeIsTrue(Node n, int[][] focal_elt) {
		for (int[] omega : focal_elt) {
			if ( this.omegaToTypes(omega, n.getIndexPlayer()).equals(n.getType()) ) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param omega
	 * @return the index of the GSG on SNF corresponding to the state of world omega
	 */
	public int getIndexOfGSG(int[] omega) {
		int index = 0;
		UsefulFunctions u = new UsefulFunctions();
		for (int[] o : this.possible_omega) {
			if (u.same(omega,o)) {
				return index;
			}
			index++;
		}
		return -1;
	}
	
	/**
	 * @param player
	 * @return the index of a given player, -1 if this player doesn't play in this game
	 */
	public int getIndexOFPlayer(Node player) {
		int indexPlayer = 0;
		for ( Node n : this.nodes) {
			if ( player.equals(n)) {
				break;
			}
			else {
				indexPlayer++;
			}
		}
		if (indexPlayer==this.nodes.size()) {
			System.out.println("Ce joueur n'existe pas");
			return -1;
		}
		return indexPlayer;
	}
	
	/**
	 * @param transfo the transfo to set
	 */
	public void setTransfo(Transformation transfo) {
		this.transfo = transfo;
	}

	/**
	 * @param xeu the xeu to set
	 */
	public void setXeu(XEU xeu) {
		this.xeu = xeu;
	}

	/**
	 * @return the gsg_snf
	 */
	public ArrayList<GSG_SNF> getGsg_snf() {
		return gsg_snf;
	}

	/**
	 * @return the possible_omega
	 */
	public ArrayList<int[]> getPossible_omega() {
		return possible_omega;
	}

	/**
	 * @return the nodes
	 */
	public ArrayList<Node> getNodes() {
		return nodes;
	}

	/**
	 * @return the focal_element
	 */
	public ArrayList<int[][]> getFocal_element() {
		return focal_element;
	}

	/**
	 * @return the mass_function
	 */
	public float[] getMass_function() {
		return mass_function;
	}

	/**
	 * @return the herd
	 */
	public int[] getHerd() {
		return herd;
	}

	/**
	 * @return the nb_location
	 */
	public int getNb_location() {
		return nb_location;
	}

	/**
	 * @return the seeFunction
	 */
	public int[] getSeeFunction() {
		return seeFunction;
	}

	/**
	 * @return the gps
	 */
	public int[] getGps() {
		return gps;
	}
	
	/**
	 * @return the xeu
	 */
	public XEU getXeu() {
		return xeu;
	}

	/**
	 * @return the transfo
	 */
	public Transformation getTransfo() {
		return transfo;
	}

	@Override
	public void computeUtilities() {
		this.transfo.computeUtilities(this);
	}

	@Override
	public float getUtility(Profile profile, int joueur) {
		return this.transfo.getUtility(profile,joueur,this.nodes.size());
	}

	@Override
	public void writeInFile(String filename) {
		this.transfo.writeInFile(filename,this.nodes.size());
	}

}
