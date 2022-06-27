package decision.game.profile;

import java.util.Vector;


public class Profile<T> extends Vector<T> {
	
	private static final long serialVersionUID = 1L;
	
	public Profile() {
		super();
	}
	
	public Profile(int initialCapacity) {
		super(initialCapacity);
	}
	
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
