package Transformations;

import java.util.ArrayList;

import Games.BEL_GSG;
import Local_Game_for_BEL_GSG.LGame_TBMtransfo;
import Tools.Node;

public class TBM_Transform extends Transformation {

	@Override
	public void computeUtilities(BEL_GSG parentGame) {
		
		int indexOfFocalElt=0;
		for (int[][] focal_elt : parentGame.getFocal_element()) {
			
			for (int[] omega : focal_elt) {
			
				LGame_TBMtransfo l = new LGame_TBMtransfo(omega,indexOfFocalElt);
				for (Node n : parentGame.getNodes()) {
					if (parentGame.typeIsTrue(n,focal_elt)) {
						l.addPlayer(n,parentGame.getPossibleActions().get(n.getIndexPlayer()),computeNbOmegaPlays(n,focal_elt,parentGame));
					}
				}
				
				l.computeUtilities(parentGame);
				
				this.game.put(indexOfFocalElt, l.getMatrixOfGame());
				
				ArrayList<Integer> listPlayer = new ArrayList<Integer>();
				for (Node local_player : l.getListPlayer()) {
					int index = 0;
					for (Node player : parentGame.getNodes()) {
						if (local_player.equals(player)) {
							listPlayer.add(index);
						}
						index++;
					}
				}
				this.player_by_game.add(listPlayer);
			}
		}
	}

	/**
	 * @param n
	 * @param focal_elt
	 * @param parentGame
	 * @return the number of omega in "focal_elt" where the player "n" play
	 */
	private Integer computeNbOmegaPlays(Node n, int[][] focal_elt, BEL_GSG parentGame) {
		Integer res = 0;
		for (int[] element : focal_elt) {
			if ( parentGame.omegaToTypes(element, n.getIndexPlayer()).equals(n.getType()) ) {
				res++;
			}
		}
		return res;
	}
}
