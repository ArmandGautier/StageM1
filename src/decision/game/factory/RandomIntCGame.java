package decision.game.factory;

import java.util.Iterator;
import java.util.Random;

import decision.game.MapSNFGame;
import decision.game.profile.ActionProfile;
import decision.game.profile.ActionProfile.ActionProfileIterator;
import decision.game.profile.Profile;

public class RandomIntCGame extends MapSNFGame<Integer> {
	
	public RandomIntCGame(int nPlayers, Profile<Integer> nActions, int minUtility, int maxUtility) {
		this.nPlayers = nPlayers;
		this.nActions = nActions;
		
		// Generate random utility values
		Iterator<ActionProfile> it = new ActionProfileIterator(this.nActions);
		Random random = new Random();
		while (it.hasNext()) {
			ActionProfile p = it.next();
			Profile<Integer> u = new Profile<>();
			for (int i = 0 ; i < this.nPlayers ; i++) {
				u.add(random.nextInt(minUtility+maxUtility+1)-minUtility);
			}
			this.utility.put(p, u);
		}
	}
	
	public RandomIntCGame(int nPlayers, Profile<Integer> nActions) {
		this(nPlayers, nActions, 0, 10);
	}
	
	public RandomIntCGame(int nPlayers, int nActionsPerPlayer, int minUtility, int maxUtility) {
		this(nPlayers, new Profile<Integer>(nActionsPerPlayer, nPlayers), minUtility, maxUtility);
	}
	
	public RandomIntCGame(int nPlayers, int nActionsPerPlayer) {
		this(nPlayers, new Profile<Integer>(nActionsPerPlayer, nPlayers));
	}
}
