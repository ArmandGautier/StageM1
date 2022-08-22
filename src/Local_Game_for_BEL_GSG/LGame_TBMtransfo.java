package Local_Game_for_BEL_GSG;

import java.util.ArrayList;


import Games.BEL_GSG;
import Games.GSG_SNF;
import Tools.Node;
import Tools.Profile;

public class LGame_TBMtransfo extends Local_Game {
	
	/**
	 * the omega corresponding to this local game
	 */
	protected int[] omega;
	
	/**
	 * the number of omega where player i plays
	 */
	protected ArrayList<Integer> nbOmegaPlays = new ArrayList<Integer>();

	/**
	 * @param omega
	 * @param indexOfFocalElt
	 */
	public LGame_TBMtransfo(int[] omega, int indexOfFocalElt) {
		super(indexOfFocalElt);
		this.omega = omega;
	}

	@Override
	public void computeUtilities(BEL_GSG parentGame) {
		
		for (ArrayList<Integer> list_actions : this.possibleActions) {
			this.local_dimension = this.local_dimension*list_actions.size();
		}
		
		ArrayList<Integer> playersAction = new ArrayList<Integer>();
	    for (int k=0; k < this.listPlayer.size(); k++) {
	    	playersAction.add(this.possibleActions.get(k).get(0));
	    }
	    
		for (int i=0; i<this.local_dimension; i++) {
			
			if (i != 0) {
				
				int changeAction = this.local_dimension/this.possibleActions.get(0).size();
			    for (int k=0; k < this.listPlayer.size(); k++) {
			    	
					if (Math.floorMod(i,changeAction) == 0) {
						
						int choiceOf_k = playersAction.get(k);
						int cuurentIndex = this.possibleActions.get(k).indexOf(choiceOf_k);
						playersAction.set(k, this.possibleActions.get(k).get(cuurentIndex+1));
						
					    for (int l=k+1; l < this.listPlayer.size(); l++) {
					    	playersAction.set(l,this.possibleActions.get(l).get(0));
					    }
					    break;
					}
					
					else {
						if ( k < this.listPlayer.size()-1 ) {
							changeAction = changeAction/this.possibleActions.get(k+1).size();
						}
					}
			    }
			}
			
			int localIndexPlayer = 0;
			float[] utilities = new float[this.listPlayer.size()];
			int[] p = new int[this.listPlayer.size()];
			for (Node player : this.listPlayer) {
				float k = parentGame.computeK(player);
				float val = computeVal(player,playersAction,localIndexPlayer,parentGame);
				utilities[localIndexPlayer] = k * parentGame.getMass_function()[indexOfFocalElt] * val;
				p[localIndexPlayer] = playersAction.get(localIndexPlayer);
				localIndexPlayer++;
			}
			Profile profile = new Profile(p);
			this.matrixOfGame.put(profile, utilities);
		}
	}
	
	/**
	 * @param player
	 * @param playersAction
	 * @param localIndexPlayer
	 * @param parentGame
	 * @return the utility of a player for a set of action 
	 */
	private float computeVal(Node player, ArrayList<Integer> playersAction, int localIndexPlayer, BEL_GSG parentGame) {
		
		Profile p = new Profile(playersAction);
		int indiceGSG = parentGame.getIndexOfGSG(p.getActions());
		GSG_SNF gsg = parentGame.getGsg_snf().get(indiceGSG);
		float val = gsg.getUtility(p, player.getIndexPlayer());

		if ( this.nbOmegaPlays.get(localIndexPlayer) > 0) {
			return val/this.nbOmegaPlays.get(localIndexPlayer);
		}
		else {
			return val;
		}
	}
	
	/**
	 * @param player
	 * @param actions
	 */
	public void addPlayer(Node player, ArrayList<Integer> actions, int nbOmegaPlay) {
		this.listPlayer.add(player);
		this.possibleActions.add(actions);
		this.nbOmegaPlays.add(nbOmegaPlay);
	}

	@Override
	public int[][] getOmegas() {
		int[][] res = new int[1][this.omega.length];
		res[0] = this.omega;
		return res;
	}
	
}
