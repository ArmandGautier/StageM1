package decision.game;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import decision.game.profile.ActionProfile;
import decision.game.profile.ActionProfile.ActionProfileIterator;
import decision.game.profile.Profile;

public class MapSNFGame<U> extends SNFGame<U> {
	
	private Map<ActionProfile,Profile<U>> utility;
	private int nPlayers = -1;
	private Profile<Integer> nActions;
	
	public MapSNFGame(Map<ActionProfile,Profile<U>> utility) {
		this.utility = new HashMap<>(utility);
		
		// Initialize this.nPlayers and this.nActions from the utility map.
		Iterator<Entry<ActionProfile,Profile<U>>> it = this.utility.entrySet().iterator();
		while (it.hasNext()) {
			ActionProfile p = it.next().getKey();
			if (this.nPlayers < 0) {
				this.nPlayers = p.size();
				this.nActions = new Profile<>();
				for (int i = 0 ; i < this.nPlayers ; i++) {
					this.nActions.add(0);
				}
			}
			for (int i = 0 ; i < this.nPlayers ; i++) {
				if (p.get(i) >= this.nActions.get(i)) {
					this.nActions.set(i, p.get(i)+1);
				}
			}
		}
	}
	
	public MapSNFGame(CGame<U> G) {
		this.nPlayers = G.nPlayers();
		this.nActions = G.nActions();
		this.utility = new HashMap<>();		
		Iterator<ActionProfile> it = new ActionProfileIterator(G.nActions());
		while (it.hasNext()) {
			ActionProfile p = it.next();
			this.utility.put(p, G.utility(p));
		}
	}
	
	@Override
	public int nPlayers() {
		return this.nPlayers;
	}

	@Override
	public Profile<Integer> nActions() {
		return this.nActions;
	}

	@Override
	public Profile<U> utility(ActionProfile p) {
		return this.utility.get(p);
	}

}
