package Games;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import Methods.MethodForAttacker;
import Methods.MethodForDefender;
import Tools.Profile;

public class GSG_SNF extends GSG {
	
	/**
	 * value of actions
	 */
	protected float[] lambda;

	/**
	 * representation of the game with profiles and utility value
	 */
	protected TreeMap<Profile,float[]> matrixOfGame = new TreeMap<>();
	
	/**
	 * the number of profile possible
	 */
	protected int dimension;

	/**
	 * @param nb_attacker
	 * @param nb_defender
	 * @param lambda
	 * @param possibleActions
	 * @param attMethod
	 * @param defMethod
	 */
	public GSG_SNF(int nb_attacker, int nb_defender, float[] lambda, ArrayList<ArrayList<Integer>> possibleActions, MethodForAttacker attMethod, MethodForDefender defMethod) {
		super(nb_attacker, nb_defender, possibleActions, attMethod, defMethod);
		this.lambda = lambda;
		this.dimension = 1;
		for ( ArrayList<Integer> list_actions : possibleActions) {
			this.dimension = this.dimension * list_actions.size();
		}
	}

	@Override
	public void computeUtilities() {
		
		ArrayList<Integer> playersAction = new ArrayList<Integer>();
		
	    for (int k=0; k < this.nb_player; k++) {
	    	playersAction.add(this.possibleActions.get(k).get(0));
	    }
	    
		for (int i=0; i < this.dimension; i++) {
			
			if (i != 0) {
				
				int changeAction = this.dimension/this.possibleActions.get(0).size();
			    for (int k=0; k < this.nb_player; k++) {
			    	
					if (Math.floorMod(i,changeAction) == 0) {
						
						int choiceOf_k = playersAction.get(k);
						int curentIndex = this.possibleActions.get(k).indexOf(choiceOf_k);
						playersAction.set(k, this.possibleActions.get(k).get(curentIndex+1));
						
					    for (int l=k+1; l < this.nb_player; l++) {
					    	playersAction.set(l,this.possibleActions.get(l).get(0));
					    }
					    break;
					}
					
					else {
						if ( k < this.nb_player-1 ) {
							changeAction = changeAction/this.possibleActions.get(k+1).size();
						}
					}
			    }
			}
		    
			int[] p = new int[nb_player];
			float[] utilities = new float[nb_player];
			for (int j=0; j < this.nb_player; j++) {
				p[j] = playersAction.get(j);
				
				if (j < this.nb_attacker) {
					utilities[j] = this.attMethod.computeVal(playersAction,this.lambda,this.nb_attacker,j);
				}
				else {
					utilities[j] = this.defMethod.computeVal(playersAction,this.lambda,this.nb_attacker,j);
				}
			}
			
			Profile profile = new Profile(p);
			this.matrixOfGame.put(profile, utilities);
		}
	}

	@Override
	public float getUtility(Profile profile, int joueur) {
		for ( Profile p : this.matrixOfGame.keySet()) {
			if (p.equals(profile)) {
				return this.matrixOfGame.get(p)[joueur];
			}
		}	
		return 0;
	}

	@Override
	public void writeInFile(String filename) {
		try {
			  
			  File file = new File(filename);

			  if (!file.exists()) {
				  file.createNewFile();
			  }
			  
			  FileWriter fw = new FileWriter(file.getAbsoluteFile());
			  BufferedWriter bw = new BufferedWriter(fw);
			  
			  String content;
			  
			  content = this.nb_player + "\n";
			  bw.write(content);
			  
			  Iterator<Profile> it1 = this.matrixOfGame.keySet().iterator();
			  Iterator<float[]> it2 = this.matrixOfGame.values().iterator();
			  
			  while (it1.hasNext() && it2.hasNext()) {
				  
				  content = "";
				  Profile tab1 = it1.next();
				  float[] tab2 = it2.next();
				  
				  for (int i=0; i<tab1.getActions().length; i++) {
					  content += tab1.getActions()[i];
					  content += " ";
				  }
				  
				  content += " : ";
				  for (int j=0; j<tab2.length; j++) {
					  content += tab2[j];
					  content += " ";
				  }
				  
				  content += "\n";
				  bw.write(content);
			  }
			  
			  bw.close();
			} 
		
		catch (IOException e) {e.printStackTrace();}
		
	}
	
	/**
	 * @return Profiles
	 */
	public ArrayList<int[]> getProfiles() {
		ArrayList<int[]> res = new ArrayList<>();
		for ( Profile p : this.matrixOfGame.keySet() ) {
			res.add(p.getActions());
		}
		return res;
	}

	/**
	 * @return Utilities value
	 */
	public ArrayList<float[]> getUtilities() {
		ArrayList<float[]> res = new ArrayList<>();
		for ( float[] u : this.matrixOfGame.values() ) {
			res.add(u);
		}
		return res;
	}
	
	/**
	 * @return the matrixOfGame
	 */
	public TreeMap<Profile, float[]> getMatrixOfGame() {
		return matrixOfGame;
	}

}
