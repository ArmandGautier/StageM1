package XEUs;

import java.util.ArrayList;

import Games.BEL_GSG;
import Games.GSG_SNF;
import Local_Game_for_BEL_GSG.Local_Game;
import Tools.Node;
import Tools.Profile;

public class CEU extends XEU {

	@Override
	public float computeVal(Node player, ArrayList<Integer> playersAction, int localIndexPlayer, Local_Game localGame, BEL_GSG parentGame) {
		float min = Float.MAX_VALUE;
		int index_omega=0;
		for (int[] omega : localGame.getOmegas()) {
			if ( localGame.getPlay_in_omega().get(index_omega).get(localIndexPlayer) ) {
				Profile profile = computeProfile(index_omega,playersAction,localGame,parentGame);
				int indiceGSG = parentGame.getIndexOfGSG(omega);
				GSG_SNF gsg = parentGame.getGsg_snf().get(indiceGSG);
				float toCompare = gsg.getUtility(profile, player.getIndexPlayer());
				if ( toCompare < min ) {
					min = toCompare;
				}
			}
			index_omega++;
		}
		return min;
	}

	@Override
	public float computeVal(Node player, ArrayList<Integer> playersAction, int localIndexPlayer, BEL_GSG parentGame, ArrayList<ArrayList<Boolean>> play_in_omega) {
		float min = Float.MAX_VALUE;
		float res = 0;
		int index_omega=0;
		int index_focalElt=0;
		boolean hasPlay = false;
		for (int[][] focalElement : parentGame.getFocal_element()) {
			for (int[] omega : focalElement) {
				if ( play_in_omega.get(index_omega).get(localIndexPlayer) ) {
					hasPlay = true;
					Profile profile = computeProfile(index_omega,playersAction,parentGame,play_in_omega);
					int indiceGSG = parentGame.getIndexOfGSG(omega);
					GSG_SNF gsg = parentGame.getGsg_snf().get(indiceGSG);
					float toCompare = gsg.getUtility(profile, player.getIndexPlayer());
					if ( toCompare < min ) {
						min = toCompare;
					}
				}
				index_omega++;
			}
			if ( hasPlay ) {
				res += min * parentGame.getMass_function()[index_focalElt] * parentGame.computeK(player);
				hasPlay = false;
				min = Float.MAX_VALUE;
			}
			index_focalElt++;
		}
		return res;
	}

}
