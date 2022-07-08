package GSG;

import java.util.HashSet;
import java.util.Set;

public class Type {
	Set<String> set = new HashSet<>();

	public void add(String string) {
		this.set.add(string);
	}

	/**
	 * @return the set
	 */
	public Set<String> getSet() {
		return set;
	}
	
	public boolean equals(Type type) {
		Set<String> setToCompare = type.getSet();
		if (set.size() != setToCompare.size()) {
			return false;
		}
		if ( ! set.containsAll(setToCompare)) {
			return false;
		}
		return true;
	}
	
	public String toString() {
		String res = "";
		for (String string : this.set) {
			res += string;
		}
		return res;
	}
}
