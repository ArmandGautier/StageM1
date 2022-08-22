package Local_Game_for_BEL_GSG;


import java.util.ArrayList;

import Games.BEL_GSG;
import Tools.Node;
import Tools.Profile;

public class LGame_Ctransfo extends Local_Game {

		/**
		 * the list of omega in this localGame
		 */
		public ArrayList<int[]> listOmega = new ArrayList<int[]>();
		
		/**
		 * the list of player who have a utility of 0 in this local game
		 */
		protected ArrayList<Node> playerWithoutUtility = new ArrayList<Node>();

		/**
		 * @param listOmega
		 * @param indexOfFocalElt
		 */
		public LGame_Ctransfo(ArrayList<int[]> listOmega, int indexOfFocalElt) {
			super(indexOfFocalElt);
			this.listOmega = listOmega;
		}
		
		/**
		 * @param playerWithoutUtility the playerWithoutUtility to set
		 */
		public void setPlayerWithoutUtility(ArrayList<Node> playerWithoutUtility) {
			this.playerWithoutUtility = playerWithoutUtility;
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
					if ( playerWithoutUtility.contains(player)) { 
						utilities[localIndexPlayer] = 0;
					}
					else {
						float k = parentGame.computeK(player);
						float val = parentGame.getXeu().computeVal(player,playersAction,localIndexPlayer,this,parentGame);
						utilities[localIndexPlayer] = k * parentGame.getMass_function()[indexOfFocalElt] * val;
					}
					p[localIndexPlayer] = playersAction.get(localIndexPlayer);
					localIndexPlayer++;
				}
				Profile profile = new Profile(p);
				this.matrixOfGame.put(profile, utilities);
			}
		}
		
		/**
		 * @param game
		 * @return true if the game "game" concerned less of omega than this game
		 */
		public boolean isBigger(LGame_Ctransfo game) {
			return this.indexOfFocalElt == game.indexOfFocalElt && this.listOmega.size() > game.listOmega.size();
		}

		@Override
		public int[][] getOmegas() {
			int[][] omegas = new int[this.listOmega.size()][this.listOmega.get(0).length];
			
			for (int i=0;i<omegas.length;i++) {
				omegas[i] = this.listOmega.get(i);
			}
			
			return omegas;
		}
		
		/**
		 * @param n
		 * @param localGame
		 * @return true if the player "n" play in all omegas of the local game "localGame", false otherwise
		 */
		public boolean playInAllOmega(Node n) {
			int indexPlayer = 0;
			for (Node player : this.listPlayer) {
				if (n.equals(player)) {
					break;
				}
				else {
					indexPlayer++;
				}
			}
			for (ArrayList<Boolean> list : this.play_in_omega) {
				if (! list.get(indexPlayer)) {
					return false;
				}
			}
			return true;
		}

}
