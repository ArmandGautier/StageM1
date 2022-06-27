package decision.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class LocalGame<U> extends CGame<U> {
	
	public abstract int playerID(int i);
	
	public Map<Integer,Integer> playersID() {
		Map<Integer,Integer> map = new HashMap<>();
		for (int i = 0 ; i < this.nPlayers() ; i++) {
			map.put(i, this.playerID(i));
		}
		return map;
	}
	
	public String toString() {
		String s = "Local game where ";
		for (Entry<Integer,Integer> e : this.playersID().entrySet()) {
			s += e.getValue() + " has id #" + e.getKey() + ", ";
		}
		return s + "\n" + super.toString();
	}

}
