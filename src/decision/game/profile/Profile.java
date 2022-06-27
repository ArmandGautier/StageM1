package decision.game.profile;

import java.util.Vector;


/**
 * A profile is a n-dimensional vector
 * @author Pierre Pomeret-Coquot
 * @param <T> Type for components' values
 */
public class Profile<T> extends Vector<T> {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiate an empty profile
	 */
	public Profile() {
		super();
	}
	
	/** 
	 * Instantiate an uninitialized profile with n players
	 * @param initialCapacity Number of players
	 */
	public Profile(int initialCapacity) {
		super(initialCapacity);
	}
	
	/**
	 * Instantiate and initialize a profile from its corresponding array
	 * @param array The values to put into the profile
	 */
	public Profile(T[] array) {
		for (T t : array) {
			this.add(t);
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Profile<?>) {
			Profile<?> p = (Profile<?>) o;
			if (this.size() != p.size()) {
				return false;
			}
			for (int i = 0 ; i < this.size(); i++) {
				if (this.get(i) != p.get(i)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
