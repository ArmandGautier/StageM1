package Local_Game_for_BEL_GSG;

import java.util.ArrayList;

import Games.BEL_GSG;
import Tools.Node;
import Tools.Profile;

public class LGame_Dtransfo extends Local_Game {

		/**
		 * the focal element corresponding to this local game
		 */
		private int[][] focal_elt;
		
		/**
		 * @param focal_elt
		 * @param indexOf_focal_elt
		 */
		public LGame_Dtransfo(int[][] focal_elt, int indexOfFocalElt) {
			super(indexOfFocalElt);
			this.focal_elt = focal_elt;
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
					float val = parentGame.getXeu().computeVal(player,playersAction,localIndexPlayer,this,parentGame);
					utilities[localIndexPlayer] = k * parentGame.getMass_function()[indexOfFocalElt] * val;
					p[localIndexPlayer] = playersAction.get(localIndexPlayer);
					localIndexPlayer++;
				}
				Profile profile = new Profile(p);
				this.matrixOfGame.put(profile, utilities);
			}
		}
	
		@Override
		public int[][] getOmegas() {
			return focal_elt;
		}

}
