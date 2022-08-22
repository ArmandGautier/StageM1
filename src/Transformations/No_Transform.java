package Transformations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Games.BEL_GSG;
import Tools.Node;
import Tools.Profile;

public class No_Transform extends Transformation{

	@Override
	public void computeUtilities(BEL_GSG parentGame) {
		
		int dimension = 1;
		ArrayList<ArrayList<Boolean>> play_in_omega = new ArrayList<ArrayList<Boolean>>();
		Map<Profile,float[]> matrixOfGame = new HashMap<>();
		
		for (Node joueur : parentGame.getNodes()) {
			dimension = dimension * parentGame.getPossibleActions().get(joueur.getIndexPlayer()).size();
		}
		
		for (int[][] elt : parentGame.getFocal_element()) {
			for (int[] omega : elt) {
				ArrayList<Boolean> list_player_temp = new ArrayList<Boolean>();
				for (Node player : parentGame.getNodes()) {
					if ( parentGame.omegaToTypes(omega, player.getIndexPlayer()).equals(player.getType()) ) {
						list_player_temp.add(true);
					}
					else {
						list_player_temp.add(false);
					}
				}
				play_in_omega.add(list_player_temp);
			}
		}
		
		ArrayList<Integer> playersAction = new ArrayList<Integer>();
	    for (Node n : parentGame.getNodes()) {
	    	playersAction.add(parentGame.getPossibleActions().get(n.getIndexPlayer()).get(0));
	    }
		
		for (int i=0; i<dimension; i++) {
			
			if (i != 0) {
				
				int changeAction = dimension/parentGame.getPossibleActions().get(0).size();
			    for (int k=0; k < parentGame.getNodes().size(); k++) {
			    	
					if (Math.floorMod(i,changeAction) == 0) {
						
						int choiceOf_k = playersAction.get(k);
						int cuurentIndex = parentGame.getPossibleActions().get(k).indexOf(choiceOf_k);
						playersAction.set(k, parentGame.getPossibleActions().get(k).get(cuurentIndex+1));
						
					    for (int l=k+1; l < parentGame.getNodes().size(); l++) {
					    	playersAction.set(l,parentGame.getPossibleActions().get(l).get(0));
					    }
					    break;
					}
					
					else {
						if ( k < parentGame.getNodes().size()-1 ) {
							changeAction = changeAction/parentGame.getPossibleActions().get(k+1).size();
						}
					}
			    }
			}
			
			int IndexPlayer = 0;
			float[] utilities = new float[parentGame.getNodes().size()];
			int[] p = new int[parentGame.getNodes().size()];
			for (Node player : parentGame.getNodes()) {
				float val = parentGame.getXeu().computeVal(player,playersAction,IndexPlayer,parentGame,play_in_omega);
				utilities[IndexPlayer] = val;
				p[IndexPlayer] = playersAction.get(IndexPlayer);
				IndexPlayer++;
			}
			Profile profile = new Profile(p);
			matrixOfGame.put(profile, utilities);
		}
		game.put(0, matrixOfGame);
		
		ArrayList<Integer> listPlayer = new ArrayList<Integer>();
		for (int index=0; index<parentGame.getNodes().size(); index++) {
			listPlayer.add(index);
		}
		this.player_by_game.add(listPlayer);
	}
}
