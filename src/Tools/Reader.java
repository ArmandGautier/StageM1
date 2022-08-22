package Tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Reader {
	
	public ArrayList<Object> readSNFGame(String filename) throws IOException {
		  File doc = new File(filename);

		  BufferedReader obj = new BufferedReader(new FileReader(doc));

		  ArrayList<int[]> profiles = new ArrayList<int[]>();
		  ArrayList<float[]> utilities = new ArrayList<float[]>();
		  ArrayList<Object> res = new ArrayList<Object>();
		  
		  String line;
		  int indexLine = 0;
		  int nbPlayer = 0;
		  while ((line = obj.readLine()) != null) {
			  if ( indexLine == 0) {
				  nbPlayer = Integer.valueOf(line);
				  indexLine++;
			  }
			  else {
				  String copyLine = String.copyValueOf(line.toCharArray());
				  int[] profile = new int[nbPlayer];
				  float[] utility = new float[nbPlayer];
				  
				  int indexNextSpace = copyLine.indexOf(" ");
				  for (int j=0; j<nbPlayer; j++) {
					  profile[j] = Integer.valueOf(copyLine.substring(0, indexNextSpace));
					  copyLine = copyLine.substring(indexNextSpace+1);
					  indexNextSpace = copyLine.indexOf(" ");
				  }
				  
				  int indexDoublePoint = copyLine.indexOf(":");
				  copyLine = copyLine.substring(indexDoublePoint+1);
				  indexNextSpace = copyLine.indexOf(" ");
				  copyLine = copyLine.substring(indexNextSpace+1);
				  indexNextSpace = copyLine.indexOf(" ");
				  
				  for (int j=0; j<nbPlayer; j++) {
					  utility[j] = Float.valueOf(copyLine.substring(0, indexNextSpace));
					  copyLine = copyLine.substring(indexNextSpace+1);
					  indexNextSpace = copyLine.indexOf(" ");
				  }
				  
				  profiles.add(profile);
				  utilities.add(utility);
			  }
		  }
		  obj.close();
		  
		  res.add(profiles);
		  res.add(utilities);
		  return res;
	}
	
	// A tester
	
	public ArrayList<Object> readHypergraphiqueGame(String filename) throws IOException {
		  File doc = new File(filename);

		  BufferedReader obj = new BufferedReader(new FileReader(doc));

		  ArrayList<int[]> profiles = new ArrayList<int[]>();
		  ArrayList<float[]> utilities = new ArrayList<float[]>();
		  ArrayList<Integer> localGamePlayer = new ArrayList<Integer>();
		  Map<Integer, ArrayList<int[]>> gamesProfiles = new HashMap<>();
		  Map<Integer, ArrayList<float[]>> gamesUtilities = new HashMap<>();
		  ArrayList<ArrayList<Integer>> playerByGame = new ArrayList<ArrayList<Integer>>();
		  ArrayList<Object> res = new ArrayList<Object>();
		  
		  String line;
		  int indexLine = 0;
		  int nbPlayer = 0;
		  int indexGame = 0;
		  
		  while ((line = obj.readLine()) != null) {
			  if ( indexLine == 0) {
				  nbPlayer = Integer.valueOf(line);
				  indexLine++;
			  }
			  else {
				  
				  if ( !line.contains(":")) {
					  if ( line.contains("Local") ) {
						  
						  gamesProfiles.put(indexGame, profiles);
						  gamesUtilities.put(indexGame, utilities);
						  playerByGame.add(localGamePlayer);
						  
						  indexGame++;
						  
						  profiles = new ArrayList<int[]>();
						  utilities = new ArrayList<float[]>();
						  localGamePlayer = new ArrayList<Integer>();
					  }
					  else {
						  int index=0;
						  for (int i=0; i<line.length();i++) {
							  if ( line.charAt(i) == ' ') {
								  localGamePlayer.add(Integer.valueOf(line.substring(index, i)));
								  index=i+1;
							  }
						  }
					  }
				  }
				  
				  else {
					  
					  String copyLine = String.copyValueOf(line.toCharArray());
					  int[] profile = new int[nbPlayer];
					  float[] utility = new float[nbPlayer];
					  
					  int indexNextSpace = copyLine.indexOf(" ");
					  for (int j=0; j<localGamePlayer.size(); j++) {
						  profile[j] = Integer.valueOf(copyLine.substring(0, indexNextSpace));
						  copyLine = copyLine.substring(indexNextSpace+1);
						  indexNextSpace = copyLine.indexOf(" ");
					  }
					  
					  int indexDoublePoint = copyLine.indexOf(":");
					  copyLine = copyLine.substring(indexDoublePoint+1);
					  indexNextSpace = copyLine.indexOf(" ");
					  copyLine = copyLine.substring(indexNextSpace+1);
					  indexNextSpace = copyLine.indexOf(" ");
					  
					  for (int j=0; j<localGamePlayer.size(); j++) {
						  utility[j] = Float.valueOf(copyLine.substring(0, indexNextSpace));
						  copyLine = copyLine.substring(indexNextSpace+1);
						  indexNextSpace = copyLine.indexOf(" ");
					  }
					  
					  profiles.add(profile);
					  utilities.add(utility);

				  }
				  
				  indexLine++;
			  }
		  }
		  obj.close();
		  
		  res.add(gamesProfiles);
		  res.add(gamesUtilities);
		  res.add(playerByGame);
		  return res;
	}
}
