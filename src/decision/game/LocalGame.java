package decision.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Local game of an hypergraphical game
 * @author Pierre Pomeret-Coquot
 * @param <U> Type for utility values
 */
public abstract class LocalGame<U> extends SNFGame<U> {
	
	/**
	 * Id of a local player in the global hypergraphical game
	 * @param i Local id of the player
	 * @return  Global id of the player
	 */
	public abstract int playerID(int i);
	
	/**
	 * Correspondance between local and global players' id
	 * @return Map where keys are local ids and values are global ids
	 */
	public Map<Integer,Integer> playersID() {
		Map<Integer,Integer> map = new HashMap<>();
		for (int i = 0 ; i < this.nPlayers() ; i++) {
			map.put(i, this.playerID(i));
		}
		return map;
	}

	@Override
	public String toString() {
		String s = "Local game where ";
		for (Entry<Integer,Integer> e : this.playersID().entrySet()) {
			s += e.getValue() + " has id #" + e.getKey() + ", ";
		}
		return s + "\n" + super.toString();
	}

}
