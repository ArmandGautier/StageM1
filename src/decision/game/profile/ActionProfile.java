package decision.game.profile;

import java.util.Iterator;
import java.util.Vector;

public class ActionProfile extends Profile<Integer> {

	private static final long serialVersionUID = -7882463705109987320L;
	
	public ActionProfile() {
		super();
	}
	
	public ActionProfile(int[] array) {
		super();
		for (int i = 0 ; i < array.length; i++) {
			this.add(array[i]);
		}
	}

	public ActionProfile moveTo(int i, int a_i) {
		ActionProfile p = new ActionProfile();
		for (int j = 0 ; j < this.size() ; j++) {
			if (j == i) {
				p.add(a_i);
			}
			else {
				p.add(this.get(j));
			}
		}
		return p;
	}

	public static class ActionProfileIterator implements Iterator<ActionProfile> {

		private int nPlayers;
		private Vector<Integer> nActions;
		private ActionProfile currentProfile;
		private boolean hasNext = false;
		
		public ActionProfileIterator(Vector<Integer> nActions) {
			this.nPlayers = nActions.size();
			this.nActions = nActions;
			this.currentProfile = new ActionProfile();
			for (int i = 0 ; i < this.nPlayers ; i++) {
				this.currentProfile.add(0);
				this.hasNext = this.hasNext || (nActions.get(i) > 0);
			}
		}

		@Override
		public boolean hasNext() {
			return this.hasNext;
		}

		@Override
		public ActionProfile next() {
			ActionProfile nextProfile = this.currentProfile.moveTo(0, this.currentProfile.get(0)+1);

			for (int i = 0 ; i < this.nPlayers - 1 ; i++) {
				if (nextProfile.get(i) >= this.nActions.get(i)) {
					nextProfile = nextProfile.moveTo(i+1,nextProfile.get(i+1)+1).moveTo(i, 0);
				}
				else {
					break;
				}
				if (nextProfile.get(this.nPlayers-1) >= this.nActions.get(this.nPlayers-1)) {
					this.hasNext = false;
				}
			}
			ActionProfile p2 = this.currentProfile;
			this.currentProfile = nextProfile;
			return p2;
		}
		
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof ActionProfile) {
			ActionProfile p = (ActionProfile) o;
			if (p.size() == this.size()) {
				for (int i = 0 ; i < this.size() ; i++) {
					if (p.get(i) != this.get(i)) {
						return false;
					}
				}
				return true;
			}
		}
		else if (o instanceof int[]) {
			int[] p = (int[]) o;
			if (p.length == this.size()) {
				for (int i = 0 ; i < this.size() ; i++) {
					if (p[i] != this.get(i)) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

}
