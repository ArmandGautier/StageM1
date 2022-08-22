package Transformations;

import java.util.ArrayList;

import Tools.Node;
import Games.BEL_GSG;
import Local_Game_for_BEL_GSG.LGame_Ctransfo;
import Tools.UsefulFunctions;

public class Conditionned_Transform extends Transformation {

	@Override
	public void computeUtilities(BEL_GSG parentGame) {
		
		ArrayList<LGame_Ctransfo> localGames = new ArrayList<LGame_Ctransfo>();
		
		for ( Node n : parentGame.getNodes()) {
			int indexFocalElement = 0;
			for ( int[][] focal_elt : parentGame.getFocal_element()) {
				
				ArrayList<int[]> listOmega = new ArrayList<int[]>();
				for (int[] omega : focal_elt) {
					if ( parentGame.omegaToTypes(omega, n.getIndexPlayer()).equals(n.getType()) ) {
						listOmega.add(omega);
					}
				}
				
				if (listOmega.size() > 0) {
					
					if ( ! existGame(listOmega,indexFocalElement,localGames)) {
						
						LGame_Ctransfo l = new LGame_Ctransfo(listOmega,indexFocalElement);
						for (Node n2 : parentGame.getNodes()) {
							if (parentGame.typeIsTrue(n2,l.getOmegas())) {
								l.addPlayer(n2,parentGame.getPossibleActions().get(n2.getIndexPlayer()));
							}
						}
						
						ArrayList<ArrayList<Boolean>> play_in_omega = new ArrayList<ArrayList<Boolean>>();
						for (int[] omega : l.getOmegas()) {
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
						
						localGames.add(l);
					}
				}
				indexFocalElement++;
			}
		}
		
		int nbGame = 0;
		for (LGame_Ctransfo l : localGames) {
			
			ArrayList<Node> playerWithoutUtility = new ArrayList<Node>();
			
			for (Node n : l.getListPlayer()) {
				if ( ! l.playInAllOmega(n)) {
					playerWithoutUtility.add(n);
				}
				else {
					for (LGame_Ctransfo game : localGames) {
						if (game.playIn(n)) {
							if (game.isBigger(l) && game.playInAllOmega(n)) {
								playerWithoutUtility.add(n);
							}
						}
					}
				}
			}
			l.setPlayerWithoutUtility(playerWithoutUtility);
			
			l.computeUtilities(parentGame);
			this.game.put(nbGame, l.getMatrixOfGame());
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
			nbGame++;
		}
	}

	/**
	 * @param listOmega
	 * @param indexFocalElement
	 * @param localGames
	 * @return true if the local game defined by "listOmega" and "indexFocalElement" already exist, false otherwise
	 */
	private boolean existGame(ArrayList<int[]> listOmega, int indexFocalElement, ArrayList<LGame_Ctransfo> localGames ) {
		UsefulFunctions u = new UsefulFunctions();
		for (LGame_Ctransfo game : localGames) {
			if ( game.getIndexOfFocalElt() == indexFocalElement && u.same(game.listOmega,listOmega) ) {
				return true;
			}
		}
		return false;
		
	}
}
