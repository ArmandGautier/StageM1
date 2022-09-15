package Tools;

import java.util.ArrayList;

public class Profile implements Comparable<Object> {
	
	int[] actions;

	/**
	 * @param profile
	 */
	public Profile(int[] profile) {
		this.actions = profile;
	}
	
	/**
	 * @param profile
	 */
	public Profile(ArrayList<Integer> profile) {
		this.actions = new int[profile.size()];
		for (int i=0; i<this.actions.length; i++) {
			this.actions[i] = profile.get(i);
		}
	}
	
	public boolean equals(Object obj) {
		
		if (obj instanceof Profile) {
			
			Profile p = (Profile) obj;
			
			if (p.getActions().length != this.actions.length) {
				return false;
			}
			
			for (int i=0; i<this.actions.length; i++) {
				if (p.getActions()[i] != this.actions[i]) {
					return false;
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	public int hashCode() {
		return this.toString().hashCode();
	}
	
	/**
	 * @return actions of profile
	 */
	public int[] getActions() {
		return this.actions;
	}
	
	public String toString() {
		String res = "";
		for (int action : this.actions) {
			res += " " + action+" ";
		}
		return res;
	}

	@Override
	public int compareTo(Object obj) {
		
		if (obj instanceof Profile) {
			int[] a1 = this.actions;
			int[] a2 = ((Profile) obj).getActions();
			for (int i = 0; i<a1.length; i++) {
				if ( a1[i] < a2[i] ) {
					return -1;
				}
				if ( a1[i] > a2[i] ) {
					return 1;
				}
			}
		}
		return 0;
	}

}
