/**
 * 
 */
package Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import GSG.Bel_GSG;
import GSG.Bel_GSG_Conditionned_Transform;
import GSG.Bel_GSG_Direct_Transform;
import GSG.Bel_GSG_SNF;
import GSG.Bel_GSG_TBM_Transform;
import GSG.GSG;
import GSG.GSG_SNF;
import GSG.GSG_hypergraphique;
import GSG.Generateur;
import GSG.Node;
import GSG.Universe;
import Model_cplex.Gilpin_model;
import Model_cplex.Trabelsi_for_hypergraphical_games;
import Model_cplex.Trabelsi_model;
import Model_cplex.Trabelsi_without_objective;
import GSG.GSG_MF;
import Test_model.Test_model;
import Verif.Verif;

/**
 * @author agautier
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) {
	
		Generateur g = new Generateur();
		
		ArrayList<Universe> universes = g.generateUniverseForBel_Gsg(3, 2, 2, 1, 10);
		
		String uti_att = "Team-poach and bribe";
		String uti_def = "captor";
		String method = "CEU";
		
		//ArrayList<Bel_GSG_SNF> gsg = g.generate_Bel_GSG_SNF(universes, uti_att, uti_def, method);
		ArrayList<Bel_GSG_Direct_Transform> dTransfo = g.generate_Bel_GSG_Direct_Transform(universes, uti_att, uti_def, method);
		ArrayList<Bel_GSG_Conditionned_Transform> cTransfo = g.Bel_GSG_Conditionned_Transform(universes, uti_att, uti_def, method);
		//ArrayList<Bel_GSG_TBM_Transform> tbmTransfo = g.Bel_GSG_TBM_Transform(universes, uti_att, uti_def);
		
		try {
		    File file = new File("Test_condVSdir_CEU_6.txt");
		    File file2 = new File("Info_jeux_Test_condVSdir_CEU_6.txt");
		    String content = "";
	
		    if (!file.exists()) {
		     file.createNewFile();
		    }
		    
		    if (!file2.exists()) {
			     file2.createNewFile();
			    }
	
		    FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		    BufferedWriter bw = new BufferedWriter(fw);
		    
		    FileWriter fw2 = new FileWriter(file2.getAbsoluteFile(),true);
		    BufferedWriter bw2 = new BufferedWriter(fw2);
		    
		    content = " indice_jeu, tps_dtransfo, tps_ctransfo, equi_dt, equi_ct\n";
		    bw.write(content);
		    
			//double time1 = 0;
			double time2 = 0;
			double time3 = 0;
			//double time4 = 0;
			int size = 0;
			int ind_jeux_local;
			boolean ok2;
			boolean ok3;
		    
			for (int i=0; i<10; i++) {
				   
				System.out.println("JEUX "+i);
				
				//ArrayList<int[]> profils = gsg.get(i).getProfiles();
				//ArrayList<float[]> utilites = gsg.get(i).getUtilities();
				Map<Integer, ArrayList<int[]>> profils2 = dTransfo.get(i).getProfils();
				Map<Integer, ArrayList<float[]>> utilites2 = dTransfo.get(i).getUtilites();
				Map<Integer, ArrayList<int[]>> profils3 = cTransfo.get(i).getProfils();
				Map<Integer, ArrayList<float[]>> utilites3 = cTransfo.get(i).getUtilites();
				//Map<Integer, ArrayList<int[]>> profils4 = tbmTransfo.get(i).getProfils();
				//Map<Integer, ArrayList<float[]>> utilites4 = tbmTransfo.get(i).getUtilites();
				
				ArrayList<ArrayList<Integer>> player_by_game2 = dTransfo.get(i).getPlayer_by_game();
				ArrayList<ArrayList<Integer>> player_by_game3 = cTransfo.get(i).getPlayer_by_game();
				//ArrayList<ArrayList<Integer>> player_by_game4 = tbmTransfo.get(i).getPlayer_by_game();
				
				size = dTransfo.get(i).getNodes().size();
				
				ok2 = true;
				for ( ArrayList<Integer> nb_player : player_by_game2) {
					if (nb_player.size() > 8) {
						ok2 = false;
						break;
					}
				}
				ok3 = true;
				for ( ArrayList<Integer> nb_player : player_by_game3) {
					if (nb_player.size() > 8) {
						ok3 = false;
						break;
					}
				}
				
				if (ok2) {
				
					//Trabelsi_model trabel = new Trabelsi_model(profils,utilites);
					Trabelsi_for_hypergraphical_games trabel2 = new Trabelsi_for_hypergraphical_games(profils2,utilites2,player_by_game2,size);
					Trabelsi_for_hypergraphical_games trabel3 = new Trabelsi_for_hypergraphical_games(profils3,utilites3,player_by_game3,size);
					//Trabelsi_for_hypergraphical_games trabel4 = new Trabelsi_for_hypergraphical_games(profils4,utilites4,player_by_game4,size);
					
					//trabel.construct_model();
					trabel2.construct_model();
					trabel3.construct_model();
					//trabel4.construct_model();
					
					//time1 += trabel.get_solving_time();
					time2 = trabel2.get_solving_time();
					time3 = trabel3.get_solving_time();
					//time4 += trabel4.get_solving_time();
					
					boolean equi1 = trabel2.equilibrium();
					boolean equi2 = trabel3.equilibrium();
					
					content = "Jeux" + i + ", "+time2+", "+time3+", "+equi1+", "+equi2+", "+"\n";
					bw.write(content);
					
				}
				
				else {
					if (ok3) {
						Trabelsi_for_hypergraphical_games trabel3 = new Trabelsi_for_hypergraphical_games(profils3,utilites3,player_by_game3,size);
						trabel3.construct_model();
						time3 = trabel3.get_solving_time();
						boolean equi2 = trabel3.equilibrium();
						
						content = "Jeux" + i + ", "+"Not compute"+", "+time3+", "+"Not compute"+", "+equi2+", "+"\n";
						bw.write(content);
					}
					else {
						content = "Jeux" + i + ", jeux local trop important, pas calculé\n";
						bw.write(content);
					}
				}
				
				bw2.write("Jeux " +i+"\n\n");
				bw2.write(universes.get(i).toString());
				bw2.write("\n nombre de joueurs créés : "+size+"\n");
				ind_jeux_local = 0;
				bw2.write("Nombre de joueur dans les jeux locaux de la transfo direct : \n");
				for ( ArrayList<Integer> nb_player : player_by_game2) {
					bw2.write("nombre de joueur dans le jeux local "+ind_jeux_local+" : "+nb_player.size()+"\n");
					ind_jeux_local++;
				}
				ind_jeux_local = 0;
			    bw2.write("Nombre de joueur dans les jeux locaux de la transfo conditionnée : \n");
			    for ( ArrayList<Integer> nb_player : player_by_game3) {
			    	bw2.write("nombre de joueur dans le jeux local "+ind_jeux_local+" : "+nb_player.size()+"\n");
			    	ind_jeux_local++;
				}
				bw2.write("\n\n");
			}
			bw.close();
			bw2.close();
		}
		catch (IOException e) {
			   e.printStackTrace();
			  }
		
	}
}
