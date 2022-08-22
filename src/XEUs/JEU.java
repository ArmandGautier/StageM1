package XEUs;

import java.util.ArrayList;

import Games.BEL_GSG;
import Games.GSG_SNF;
import Local_Game_for_BEL_GSG.Local_Game;
import Tools.Node;
import Tools.Profile;

public class JEU extends XEU{
	
	private float[] alpha;

	/**
	 * @param alpha
	 */
	public JEU(float[] alpha) {
		this.alpha = alpha;
	}

	@Override
	public float computeVal(Node player, ArrayList<Integer> playersAction, int localIndexPlayer, Local_Game localGame, BEL_GSG parentGame) {
		float val = 0;
		int index_omega=0;
		float min = Float.MAX_VALUE;
		float max = Float.MIN_VALUE;
		for (int[] omega : localGame.getOmegas()) {
			if ( localGame.getPlay_in_omega().get(index_omega).get(localIndexPlayer) ) {
				Profile profile = computeProfile(index_omega,playersAction,localGame,parentGame);
				int indiceGSG = parentGame.getIndexOfGSG(omega);
				GSG_SNF gsg = parentGame.getGsg_snf().get(indiceGSG);
				float toCompare = gsg.getUtility(profile, player.getIndexPlayer());
				if ( toCompare < min ) {
					min = toCompare;
				}
				if ( toCompare > max ) {
					max = toCompare;
				}
			}
			index_omega++;
		}
		val = this.alpha[player.getIndexPlayer()]*min+(1-this.alpha[player.getIndexPlayer()])*max;
		return val;
	}

	@Override
	public float computeVal(Node player, ArrayList<Integer> playersAction, int localIndexPlayer, BEL_GSG parentGame, ArrayList<ArrayList<Boolean>> play_in_omega) {
		float val = 0;
		int index_omega=0;
		float min = Float.MAX_VALUE;
		float max = Float.MIN_VALUE;
		for (int[][] focalElement : parentGame.getFocal_element()) {
			for (int[] omega : focalElement) {
				if ( play_in_omega.get(index_omega).get(localIndexPlayer) ) {
					Profile profile = computeProfile(index_omega,playersAction,parentGame,play_in_omega);
					int indiceGSG = parentGame.getIndexOfGSG(omega);
					GSG_SNF gsg = parentGame.getGsg_snf().get(indiceGSG);
					float toCompare = gsg.getUtility(profile, player.getIndexPlayer());
					if ( toCompare < min ) {
						min = toCompare;
					}
					if ( toCompare > max ) {
						max = toCompare;
					}
				}
				index_omega++;
			}
		}
		val = this.alpha[player.getIndexPlayer()]*min+(1-this.alpha[player.getIndexPlayer()])*max;
		return val;
	}

}
