package decision.game.factory;

import decision.game.SNFGame;
import decision.game.profile.ActionProfile;
import decision.game.profile.Profile;

/**
 * Coordination game: players aim to choose the same action
 * @author Pierre Pomeret-Coquot
 *
 */
public class CoordinationGame extends SNFGame<Integer> {
	
	private int nPlayers;
	private Profile<Integer> nActions = new Profile<>();
	
	/**
	 * Instantiate a coordination game (players aim to choose the same action)
	 * @param nPlayers Number of players
	 * @param nActions Number of actions (for every player)
	 */
	public CoordinationGame(int nPlayers, int nActions) {
		this.nPlayers = nPlayers;
		for (int i = 0 ; i < nPlayers ; i++) {
			this.nActions.add(nActions);
		}
	}
	
	/**
	 * Instantiate a coordination game with two players and two actions each
	 */
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
