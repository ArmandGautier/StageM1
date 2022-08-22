package XEUs;

import java.util.ArrayList;

import Games.BEL_GSG;
import Games.GSG_SNF;
import Local_Game_for_BEL_GSG.Local_Game;
import Tools.Node;
import Tools.Profile;

public class TBEU extends XEU {

	@Override
	public float computeVal(Node player, ArrayList<Integer> playersAction, int localIndexPlayer, Local_Game localGame, BEL_GSG parentGame) {
		float val = 0;
		int nb_omega=0;
		int index_omega=0;
		for (int[] omega : localGame.getOmegas()) {
			if ( localGame.getPlay_in_omega().get(index_omega).get(localIndexPlayer) ) {
				nb_omega++;
				Profile profile = computeProfile(index_omega,playersAction,localGame,parentGame);
				int indiceGSG = parentGame.getIndexOfGSG(omega);
				GSG_SNF gsg = parentGame.getGsg_snf().get(indiceGSG);
				val += gsg.getUtility(profile, player.getIndexPlayer());
			}
			index_omega++;
		}
		if (nb_omega > 0) {
			return val/nb_omega;
		}
		else {
			return val;
		}
	}

	@Override
	public float computeVal(Node player, ArrayList<Integer> playersAction, int localIndexPlayer, BEL_GSG parentGame, ArrayList<ArrayList<Boolean>> play_in_omega) {
		float val = 0;
		int nb_omega=0;
		int index_omega=0;
		for (int[][] focalElement : parentGame.getFocal_element()) {
			for (int[] omega : focalElement) {
				if ( play_in_omega.get(index_omega).get(localIndexPlayer) ) {
					nb_omega++;
					Profile profile = computeProfile(index_omega,playersAction,parentGame,play_in_omega);
					int indiceGSG = parentGame.getIndexOfGSG(omega);
					GSG_SNF gsg = parentGame.getGsg_snf().get(indiceGSG);
					val += gsg.getUtility(profile, player.getIndexPlayer());
				}
				index_omega++;
			}
		}
		if (nb_omega > 0) {
			return val/nb_omega;
		}
		else {
			return val;
		}
	}
}
