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
		int nb_location = 3;
		ArrayList<Universe> universes = g.generateUniverseForBel_Gsg(nb_location, 3, 2, 2, 50);
		String uti_att = "Team-poach and bribe";
		String uti_def = "captor";
		String method = "CEU";
		
		try {
		    File file = new File("Test6.csv");
		    File file2 = new File("Info_jeux_Test6.txt");
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
		    
		    content = " indice_jeu, tps_dtransfo, tps_ctransfo, equi_dt, equi_ct, classe,";
		    content += " t_moy_elt_foc, t_max_elt_foc, nb_gps, nb_depl_herd, moy_arc_observe, max_arc_observe, nb_joueurs, nb_jeux_loc_dt, moy_j_by_g_dt, max_j_by_g_dt, nb_jeux_loc_ct, moy_j_by_g_ct, max_j_by_g_ct\n";
		    bw.write(content);
		    
			double time2 = 0;
			double time3 = 0;
			int size = 0;
			int ind_jeux_local;
			boolean ok2;
			boolean ok3;
		    
			for (int i=0; i<50; i++) {
				   
				System.out.println("JEUX "+i);
				
				Bel_GSG_Direct_Transform dTransfo = g.generate_Bel_GSG_Direct_Transform(universes.get(i), uti_att, uti_def, method);
				Bel_GSG_Conditionned_Transform cTransfo = g.generate_Bel_GSG_Conditionned_Transform(universes.get(i), uti_att, uti_def, method);
				
				Map<Integer, ArrayList<int[]>> profils2 = dTransfo.getProfils();
				Map<Integer, ArrayList<float[]>> utilites2 = dTransfo.getUtilites();
				Map<Integer, ArrayList<int[]>> profils3 = cTransfo.getProfils();
				Map<Integer, ArrayList<float[]>> utilites3 = cTransfo.getUtilites();
				
				ArrayList<ArrayList<Integer>> player_by_game2 = dTransfo.getPlayer_by_game();
				ArrayList<ArrayList<Integer>> player_by_game3 = cTransfo.getPlayer_by_game();
				
				size = dTransfo.getNodes().size();
				
				float moy1 = 0;
				float moy2 = 0;
				int max1 = 0;
				int max2 = 0;
				
				ok2 = true;
				for ( ArrayList<Integer> nb_player : player_by_game2) {
					moy1 += nb_player.size();
					if (nb_player.size() > max1) {
						max1 = nb_player.size();
					}
					if (nb_player.size() > 11-nb_location) {
						ok2 = false;
					}
				}
				moy1 = moy1/player_by_game2.size();
				
				ok3 = true;
				for ( ArrayList<Integer> nb_player : player_by_game3) {
					moy2 += nb_player.size();
					if (nb_player.size() > max2) {
						max2 = nb_player.size();
					}
					if (nb_player.size() > 11-nb_location) {
						ok3 = false;
					}
				}
				moy2 = moy2/player_by_game3.size();
				
				if (ok2) {
					
					Trabelsi_for_hypergraphical_games trabel2 = new Trabelsi_for_hypergraphical_games(profils2,utilites2,player_by_game2,size);
					Trabelsi_for_hypergraphical_games trabel3 = new Trabelsi_for_hypergraphical_games(profils3,utilites3,player_by_game3,size);
					
					trabel2.construct_model();
					trabel3.construct_model();
					
					time2 = trabel2.get_solving_time();
					time3 = trabel3.get_solving_time();
					int classe;
					
					if (time2 > 2*time3) {
						classe = 4;
					}
					else {
						if ( time3 > 2*time2) {
							classe = 3;
						}
						else {
							classe = 2;
						}
					}
					
					boolean equi1 = trabel2.equilibrium();
					boolean equi2 = trabel3.equilibrium();
					
					content = "Jeux" + i + ", "+time2+", "+time3+", "+equi1+", "+equi2+", "+classe+", ";
					bw.write(content);
					
				}
				
				else {
					if (ok3) {
						Trabelsi_for_hypergraphical_games trabel3 = new Trabelsi_for_hypergraphical_games(profils3,utilites3,player_by_game3,size);
						trabel3.construct_model();
						time3 = trabel3.get_solving_time();
						boolean equi2 = trabel3.equilibrium();
						
						content = "Jeux" + i + ", "+"Not compute"+", "+time3+", "+"Not compute"+", "+equi2+", "+1+", ";
						bw.write(content);
					}
					else {
						content = "Jeux" + i + ", "+"Not compute"+", "+"Not compute"+", "+"Not compute"+", "+"Not compute"+", "+0+", ";
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
				bw.write(universes.get(i).toCSV()+", "+size+", "+player_by_game2.size()+", "+moy1+", "+max1+", "+player_by_game3.size()+", "+moy2+", "+max2+"\n");
			}
			bw.close();
			bw2.close();
		}
		catch (IOException e) {
			   e.printStackTrace();
			  }
		
	}
}
