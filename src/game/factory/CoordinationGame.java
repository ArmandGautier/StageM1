package game.factory;

import java.util.Vector;

import decision.game.CGame;
import decision.game.profile.ActionProfile;
import decision.game.profile.Profile;

public class CoordinationGame extends CGame<Integer> {
	
	private int nPlayers;
	private Profile<Integer> nActions = new Profile<>();
	
	public CoordinationGame(int nPlayers, int nActions) {
		this.nPlayers = nPlayers;
		for (int i = 0 ; i < nPlayers ; i++) {
			this.nActions.add(nActions);
		}
	}
	
	public CoordinationGame() {
		this(2,2);
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
	public Profile<Integer> utility(ActionProfile p) {
		Profile<Integer> u = new Profile<>();
		for (int i = 0 ; i < this.nPlayers ; i++) {
			int ui = -1;
			for (int j = 0 ; j < this.nPlayers ; j++) {
				if (p.get(i) == p.get(j) ) {
					ui = ui + 1;
				}
			}
			u.add(ui);
		}
		return u;
	}

}
