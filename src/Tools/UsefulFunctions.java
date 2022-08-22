package Tools;

import java.util.ArrayList;

public class UsefulFunctions {
	
	/**
	 * @param omega1
	 * @param omega2
	 * @return true if the two state of the world are the same, false otherwise
	 */
	public boolean same(int[] omega1, int[] omega2) {
		if (omega1.length != omega2.length) {
			return false;
		}
		for (int i=0; i<omega1.length; i++) {
			if (omega1[i] != omega2[i]) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @param omega1
	 * @param omega2
	 * @return true if the two lists of states of the world are the same, false otherwise
	 */
	public boolean same(ArrayList<int[]> omega1, ArrayList<int[]> omega2) {
		if ( omega1.size() != omega2.size()) {
			return false;
		}
		for (int i=0; i < omega1.size(); i++) {
			if (omega1.get(i).length != omega2.get(i).length) {
				return false;
			}
			for (int j=0; j<omega1.get(i).length; j++) {
				if (omega1.get(i)[j] != omega2.get(i)[j]) {
					return false;
				}
			}
		}
		return true;
	}

}
