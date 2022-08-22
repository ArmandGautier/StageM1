package Transformations;

import java.util.ArrayList;

import Games.BEL_GSG;
import Local_Game_for_BEL_GSG.LGame_Dtransfo;
import Tools.Node;

public class Direct_Transform extends Transformation {

	@Override
	public void computeUtilities(BEL_GSG parentGame) {
		
		int indexOfFocalElt=0;
		for (int[][] focal_elt : parentGame.getFocal_element()) {
			
			LGame_Dtransfo l = new LGame_Dtransfo(focal_elt,indexOfFocalElt);
			for (Node n : parentGame.getNodes()) {
				if (parentGame.typeIsTrue(n,focal_elt)) {
					l.addPlayer(n,parentGame.getPossibleActions().get(n.getIndexPlayer()));
				}
			}
			
			ArrayList<ArrayList<Boolean>> play_in_omega = new ArrayList<ArrayList<Boolean>>();
			for (int[] omega : focal_elt) {
				ArrayList<Boolean> list_player_temp = new ArrayList<Boolean>();
				for (Node player : l.getListPlayer()) {
					if ( parentGame.omegaToTypes(omega, player.getIndexPlayer()).equals(player.getType()) ) {
						list_player_temp.add(true);
					}
					else {
						list_player_temp.add(false);
					}
				}
				play_in_omega.add(list_player_temp);
			}
			l.setPlay_in_omega(play_in_omega);
			
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
			indexOfFocalElt++;
		}
	}
}
