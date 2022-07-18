package decision.game;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import decision.game.profile.ActionProfile;
import decision.game.profile.Profile;
import decision.game.profile.ActionProfile.ActionProfileIterator;

public abstract class SNFIGame<U> extends AbstractIGame<U> {
	
	public Map<Integer,Map<ActionProfile,Profile<U>>> utilityMap() {
		Map<Integer,Map<ActionProfile,Profile<U>>> map = new HashMap<>();
		for (int w = 0 ; w < this.nWorlds() ; w++) {
			Map<ActionProfile,Profile<U>> uMap = new HashMap<>();
			Iterator<ActionProfile> it = new ActionProfileIterator(this.nActions());
			while (it.hasNext()) {
				ActionProfile p = it.next();
				uMap.put(p, this.utility(p, w));
			}
			map.put(w, uMap);
		}
		return map;
	}

	@Override
	public int nValues() {
		int n = 1;
		for (int i = 0 ; i < this.nPlayers() ; i++) {
			n = n * this.nActions().get(i);
		}
		return n * this.nWorlds();
	}
	
	@Override
	public Profile<Integer> nTypes() {
		Profile<Integer> nTypes = new Profile<>();
		for (int i = 0 ; i < this.nPlayers() ; i++) {
			nTypes.add(1);
			for (int w = 0 ; w < this.nWorlds() ; w++) {
				if (this.signal(i, w) + 1 > nTypes.get(i)) {
					nTypes.set(i, this.signal(i,w) + 1);
				}
			}
		}
		return nTypes;
	}
	
	@Override
	public String toString() {
		String s = "";
		for (int w = 0 ; w < this.nWorlds() ; w++) {
			Iterator<ActionProfile> it = new ActionProfileIterator(this.nActions());
			while (it.hasNext()) {
				ActionProfile p = it.next();
				s = s + "u( " + p + ", " + w + " )\t= " + this.utility(p,w) + "\n";
			}
		}
		return s;
	}
	
}
