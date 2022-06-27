package Verif;


import java.util.ArrayList;

import GSG.Bel_GSG_Direct_Transform;
import GSG.Bel_GSG_SNF;
import GSG.GSG_SNF;

public class Verif {

	public boolean verifAsSameBelGsg(Bel_GSG_SNF game1, Bel_GSG_Direct_Transform game2) {
		return true;
	}
	
	public boolean isEquilibrium(GSG_SNF gsg, int[] actionsOfSolution) {
		ArrayList<int[]> act = gsg.getProfiles();
		ArrayList<float[]> uti = gsg.getUtilities();
		int nb_joueur = act.get(0).length;
		int indexOfSolution = getIndexOfProfile(actionsOfSolution, gsg);
		for (int i=0; i<nb_joueur; i++) {
			ArrayList<Integer> listProfileToCompare = getSameProfile(actionsOfSolution,i,act);
			for (int index : listProfileToCompare) {
				if (uti.get(index)[i] > uti.get(indexOfSolution)[i]) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * @param profil
	 * @param joueur
	 * @return a list of index of profiles who are the same as "profil" excepted the action of player "joueur" that must be different
	 */
	private ArrayList<Integer> getSameProfile(int[] profil, int joueur, ArrayList<int[]> profils) {
		ArrayList<Integer> sameProfile = new ArrayList<Integer>();
		int nb_joueur = profil.length;
		int profileIndex=0;
		for (int[] p : profils) {
			boolean same = true;
			for (int j=0; j<nb_joueur; j++) {
				// si on regarde l'action d'un autre joueur que le joueur i
				if ( joueur != j) {
					same = profil[j] == p[j];
				}
				// si on regarde l'action du joueur i
				else {
					same = profil[j] != p[j];
				}
				if (! same) {
					break;
				}
			}
			if (same) {
				sameProfile.add(profileIndex);
			}
			profileIndex++;
		}
		return sameProfile;
	}
	
	/**
	 * @param profile
	 * @param gsg
	 * @return the index of the profile "profile" in the GSG in SNF "gsg"
	 */
	protected int getIndexOfProfile(int[] profile, GSG_SNF gsg) {
		int indice = 0;
		for (int[] p : gsg.getProfiles()) {
			if (same(profile,p)) {
				return indice;
			}
			indice++;
		}
		return -1;
	}
	
	/**
	 * @param profile1
	 * @param profile2
	 * @return true if the two arrays are the same, false otherwise
	 */
	private boolean same(int[] profile1, int[] profile2) {
		if (profile1.length != profile2.length) {
			return false;
		}
		for (int i=0; i<profile1.length; i++) {
			if (profile1[i] != profile2[i]) {
				return false;
			}
		}
		return true;
	}
	
}
