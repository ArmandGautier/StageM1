package Transformations;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Games.BEL_GSG;
import Tools.Profile;

public abstract class Transformation {
	
	/**
	 * the representation of the game
	 */
	protected Map<Integer, Map<Profile,float[]>> game = new HashMap<>();
	/**
	 * a list of list who give all players who play in a local game
	 */
	protected ArrayList<ArrayList<Integer>> player_by_game = new ArrayList<ArrayList<Integer>>();

	/**
	 * @param bel_GSG
	 * Compute all utilities using a transformation
	 */
	public abstract void computeUtilities(BEL_GSG parentGame);

	/**
	 * @param filename
	 */
	public void writeInFile(String filename, int nbPlayer) {
		
		try {
			  
			  File file = new File(filename);

			  if (!file.exists()) {
				  file.createNewFile();
			  }
			  
			  FileWriter fw = new FileWriter(file.getAbsoluteFile());
			  BufferedWriter bw = new BufferedWriter(fw);
			  
			  String content;
			  content = nbPlayer + "\n";
			  bw.write(content);
			  
			  for (Integer index : this.game.keySet()) {
				  
				  content = "Local game number " + index + "\n";
				  bw.write(content);
				  
				  content = "";
				  
				  for ( Integer i : this.player_by_game.get(index)) {
					  content += i + " ";
				  }
				  content += "\n";
				  bw.write(content);
				  
				  content = "";
				  
				  Iterator<Profile> it1 = this.game.get(index).keySet().iterator();
				  Iterator<float[]> it2 = this.game.get(index).values().iterator();
				  
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
				  
			  } 
			  
			  bw.close();
			  
		}
			
		catch (IOException e) {e.printStackTrace();}
	}
	
	/**
	 * @param profile
	 * @param joueur
	 * @param nbPlayer
	 * @return the utility of a given player for a given Profile
	 */
	public float getUtility(Profile profile, int joueur, int nbPlayer) {
		float utility = 0;
		for (Integer index : this.game.keySet()) {
			if ( this.player_by_game.get(index).contains(joueur) ) {
				Profile reduceProfile = getReduceProfile(profile,index,nbPlayer);
				utility += this.game.get(index).get(reduceProfile)[joueur];
			}
		}
		return utility;
	}

	/**
	 * @param profile
	 * @param indexLocalGame
	 * @param nbPlayer
	 * @return the Profile considering only players who play in the local game number "indexLocalGame"
	 */
	private Profile getReduceProfile(Profile profile, int indexLocalGame, int nbPlayer) {
		int[] reduceProfile = new int[this.player_by_game.get(indexLocalGame).size()];
		int indexOfreduceProfile = 0;
		int indexOfprofile = 0;
		for (int player=0; player<nbPlayer; player++) {
			if (this.player_by_game.get(indexLocalGame).contains(player)) {
				reduceProfile[indexOfreduceProfile] = profile.getActions()[indexOfprofile];
				indexOfreduceProfile++;
			}
			indexOfprofile++;
		}
		Profile res = new Profile(reduceProfile);
		return res;
	}
	
	
	
	// probably temporary
	
	/**
	 * @return the game
	 */
	public Map<Integer, Map<Profile, float[]>> getGame() {
		return game;
	}

	/**
	 * @return the player_by_game
	 */
	public ArrayList<ArrayList<Integer>> getPlayer_by_game() {
		return player_by_game;
	}

	public Map<Integer, ArrayList<int[]>> getProfilesForCplex() {
		Map<Integer, ArrayList<int[]>> gamesProfiles = new HashMap<>();
		for (Integer localGame : this.game.keySet()) {
			ArrayList<int[]> res = new ArrayList<>();
			for ( Profile p : this.game.get(localGame).keySet()) {
				res.add(p.getActions());
			}
			gamesProfiles.put(localGame, res);
		}
		return gamesProfiles;
	}
	
	public Map<Integer, ArrayList<float[]>> getUtilitiesForCplex() {
		Map<Integer, ArrayList<float[]>> gamesUtilities = new HashMap<>();
		for (Integer localGame : this.game.keySet()) {
			ArrayList<float[]> res = new ArrayList<>();
			for ( float[] u : this.game.get(localGame).values() ) {
				res.add(u);
			}
			gamesUtilities.put(localGame, res);
		}
		return gamesUtilities;
	}

}
