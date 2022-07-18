package decision.game.profile;

import java.util.Iterator;
import java.util.Vector;

/**
 * IActionProfile are profiles of profiles of integer
 * @author Pierre Pomeret-Coquot
 *
 */
public class IActionProfile extends Profile<ActionProfile> {

	private static final long serialVersionUID = -7882463705109987320L;
	
	/**
	 * Instantiate an empty action profile
	 */
	public IActionProfile() {
		super();
	}
	
	/**
	 * Instantiate an action profile from its corresponding array
	 * @param array Values to put in the action profile
	 */
	public IActionProfile(ActionProfile[] array) {
		super();
		for (int i = 0 ; i < array.length; i++) {
			this.add(array[i]);
		}
	}

	/**
	 * Change the i-th component to a_i
	 * @param i Number of the component to change
	 * @param a_i New value of the i-th component
	 * @return The new action profile where the i-th component has changed to a_i
	 */
	public IActionProfile moveTo(int i, int t_i, int a_i) {
		IActionProfile p = new IActionProfile();
		for (int j = 0 ; j < this.size() ; j++) {
			ActionProfile p_j = new ActionProfile();
			for (int t_j = 0 ; t_j < this.get(j).size(); t_j++) {
				if (j == i && t_j == t_i) {
					p_j.add(a_i);
				}
				else {
					p_j.add(this.get(j).get(t_j));
				}
			}
			p.add(p_j);
		}
		return p;
	}

	
	
	
	/**
	 * Iterator over all action profiles
	 * @author Pierre Pomeret-Coquot
	 *
	 */
	public static class IActionProfileIterator implements Iterator<IActionProfile> {

		private int nPlayers;
		private Vector<Integer> nActions;
		private Vector<Integer> nTypes;
		private IActionProfile currentProfile;
		private boolean hasNext = false;
		
		/**
		 * Instantiate an iterator over all action profiles
		 * @param nActions the Integer-profile where the i-th component is the number of actions of Player i
		 */
		public IActionProfileIterator(Vector<Integer> nActions, Vector<Integer> nTypes) {
			this.nPlayers = nActions.size();
			this.nActions = nActions;
			this.nTypes = nTypes;
			this.currentProfile = new IActionProfile();
			for (int i = 0 ; i < this.nPlayers ; i++) {
				ActionProfile p = ActionProfile.ACTION_PROFILE_0(this.nTypes.get(i));
				this.hasNext = this.hasNext || (nActions.get(i) > 0);
				this.currentProfile.add(p);
			}
		}

		@Override
		public boolean hasNext() {
			return this.hasNext;
		}

		@Override
		public IActionProfile next() {
			IActionProfile nextProfile = this.currentProfile.moveTo(0, 0, this.currentProfile.get(0).get(0)+1);

			boolean ok = false;
			for (int i = 0 ; i < this.nPlayers ; i++) {
				for (int t_i = 0 ; t_i < this.nTypes.get(i) ; t_i++) {
					if (nextProfile.get(i).get(t_i) >= this.nActions.get(i)) {
						if (t_i < this.nTypes.get(i) - 1) {
							nextProfile = nextProfile.moveTo(i,t_i+1,nextProfile.get(i).get(t_i+1)+1).moveTo(i, t_i, 0);
						}
						else if (i < this.nPlayers - 1) {
							nextProfile = nextProfile.moveTo(i+1, 0, nextProfile.get(i+1).get(0)+1).moveTo(i,  t_i,  0);
						}
						else {
							this.hasNext = false;
						}
					}
					else {
						ok = true;
						break;
					}
				}
				if (ok) {
					break;
				}
			}
			IActionProfile p2 = this.currentProfile;
			this.currentProfile = nextProfile;
			return p2;
		}
		
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof IActionProfile) {
			IActionProfile p = (IActionProfile) o;
			if (p.size() == this.size()) {
				for (int i = 0 ; i < this.size() ; i++) {
					if (p.get(i) != this.get(i)) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

}
