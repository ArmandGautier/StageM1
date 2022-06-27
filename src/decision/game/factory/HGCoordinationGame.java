package decision.game.factory;

import java.util.ArrayList;
import java.util.List;

import decision.game.HGGame;
import decision.game.LocalGame;
import decision.game.profile.ActionProfile;
import decision.game.profile.Profile;
import decision.utility.oplus.IntegerOplus;
import decision.utility.oplus.Oplus;

/**
 * Hypergraphical representation of coordination games
 * @author Pierre Pomeret-Coquot
 *
 */
public class HGCoordinationGame extends HGGame<Integer> {
	
	List<LocalGame<Integer>> localGames;
	Oplus<Integer> oplus = new IntegerOplus();
	
	/**
	 * Instantiate an hypergraphical coordination game
	 * @param nPlayers Number of players
	 * @param nActions Number of actions (for every player)
	 */
	public HGCoordinationGame(int nPlayers, int nActions) {
		this.localGames = new ArrayList<>();
		for (int i = 0 ; i < nPlayers ; i++) {
			for (int j = i+1 ; j < nPlayers ; j++) {
				this.localGames.add(new LocalCoordinationGame(i, j, nActions));
			}
		}
	}

	@Override
	public Oplus<Integer> oplus() {
		return this.oplus;
	}

	@Override
	public List<LocalGame<Integer>> localGames() {
		return this.localGames;
	}
	

	
	
	/**
	 * Local coordination games
	 * @author Pierre Pomeret-Coquot
	 *
	 */
	public static class LocalCoordinationGame extends LocalGame<Integer> {
		
		private int player0;
		private int player1;
		private int nActions;
		
		/**
		 * Instantiate a local coordination game
		 * @param player0 Global id of local player 0
		 * @param player1 Global id of local player 1
		 * @param nActions Number of actions (for both player0 and player1)
		 */
		protected LocalCoordinationGame(int player0, int player1, int nActions) {
			this.player0 = player0;
			this.player1 = player1;
			this.nActions = nActions;
		}

		@Override
		public int playerID(int i) {
			switch (i) {
				case 0: return this.player0;
				case 1: return this.player1;
				default: throw new IllegalArgumentException("no such player " + i + " in LocalCoordinationGame");
			}
		}

		@Override
		public int nPlayers() {
			return 2;
		}

		@Override
		public Profile<Integer> nActions() {
			Profile<Integer> nActions = new Profile<>();
			nActions.add(this.nActions);
			nActions.add(this.nActions);
			return nActions;
		}

		@Override
		public Profile<Integer> utility(ActionProfile p) {
			Profile<Integer> u = new Profile<>();
			if (p.get(0) == p.get(1)) {
				u.add(1);
				u.add(1);
			}
			else {
				u.add(0);
				u.add(0);
			}
			return u;
		}
		
	}

}
