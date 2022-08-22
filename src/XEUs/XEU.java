package XEUs;

import java.util.ArrayList;

import Games.BEL_GSG;
import Local_Game_for_BEL_GSG.Local_Game;
import Tools.Node;
import Tools.Profile;

public abstract class XEU {

	public abstract float computeVal(Node player, ArrayList<Integer> playersAction, int localIndexPlayer, Local_Game localGame, BEL_GSG parentGame);
	public abstract float computeVal(Node player, ArrayList<Integer> playersAction, int localIndexPlayer, BEL_GSG parentGame, ArrayList<ArrayList<Boolean>> play_in_omega);

	/**
	 * @param index_omega
	 * @param choix_des_joueurs
	 * @return a restricting profile of "playersAction" considering only players who played in omega
	 */
	protected Profile computeProfile(int index_omega, ArrayList<Integer> playersAction, Local_Game localGame, BEL_GSG parentGame) {
		int[] profile = new int[parentGame.getNb_player()];
		int index_player = 0;
		for (Node player : localGame.getListPlayer()) {
			if (localGame.getPlay_in_omega().get(index_omega).get(index_player)) {
				profile[player.getIndexPlayer()] = playersAction.get(index_player);
			}
			index_player++;
		}
		Profile p = new Profile(profile);
		return p;
	}
	
	/**
	 * @param index_omega
	 * @param choix_des_joueurs
	 * @return a restricting profile of "playersAction" considering only players who played in omega
	 */
	protected Profile computeProfile(int index_omega, ArrayList<Integer> playersAction, BEL_GSG parentGame, ArrayList<ArrayList<Boolean>> play_in_omega) {
		int[] profile = new int[parentGame.getNb_player()];
		int index_player = 0;
		for (Node player : parentGame.getNodes()) {
			if (play_in_omega.get(index_omega).get(index_player)) {
				profile[player.getIndexPlayer()] = playersAction.get(index_player);
			}
			index_player++;
		}
		Profile p = new Profile(profile);
		return p;
	}
}
