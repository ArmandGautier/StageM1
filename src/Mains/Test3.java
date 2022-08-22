package Mains;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import Games.BEL_GSG;
import Methods.Captor;
import Methods.MethodForAttacker;
import Methods.MethodForDefender;
import Methods.SharePoachAndBribe;
import Model_cplex.Trabelsi_for_hypergraphical_games;
import Tools.Generateur;
import Tools.Reader;
import Tools.Universe;
import Transformations.Conditionned_Transform;
import Transformations.Direct_Transform;
import Transformations.Transformation;
import XEUs.CEU;
import XEUs.XEU;

public class Test3 {

	public static void main(String[] args) {
		Generateur g = new Generateur();
		
		int indice_fichier = 0;
		for (int nb_location = 3; nb_location<4; nb_location++) {
			for (int nb_herd = 2; nb_herd<4; nb_herd++) {
				for (int nb_attacker = 2; nb_attacker<4; nb_attacker++) {
					for (int nb_defender = 2; nb_defender<4; nb_defender++) {
						for (int i=0; i<50; i++) {
							
							Universe universe = g.generateUniverseForBel_Gsg(nb_location, nb_herd, nb_attacker, nb_defender);
							
							MethodForAttacker attMethod = new SharePoachAndBribe(1);
							MethodForDefender defMethod = new Captor(1);
							XEU xeu = new CEU();
							Transformation Dtransfo = new Direct_Transform();
							Transformation Ctransfo = new Conditionned_Transform();
							
							try {
							    File file = new File("ExpeTime"+indice_fichier+".csv");
							    File file2 = new File("Info_jeux_Expe"+indice_fichier+".txt");
							    File file3 = new File("ExpeInfo"+indice_fichier+".csv");
							    String content = "";
						
							    if (!file.exists()) {
							      file.createNewFile();
							    }
							    
							    if (!file2.exists()) {
							      file2.createNewFile();
								}
							    
							    if (!file3.exists()) {
							      file3.createNewFile();
								}
						
							    FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
							    BufferedWriter bw = new BufferedWriter(fw);
							    
							    FileWriter fw2 = new FileWriter(file2.getAbsoluteFile(),true);
							    BufferedWriter bw2 = new BufferedWriter(fw2);
							    
							    FileWriter fw3 = new FileWriter(file3.getAbsoluteFile(),true);
							    BufferedWriter bw3 = new BufferedWriter(fw3);
							    
							    if (i==0) {
								    content = " indice_jeu, tps_dtransfo, tps_ctransfo, equi_dt, equi_ct, classe\n";
								    bw.write(content);
								    content = " t_moy_elt_foc, t_max_elt_foc, nb_gps, nb_depl_herd, moy_arc_observe, max_arc_observe, nb_joueurs, nb_jeux_loc_dt, moy_j_by_g_dt, max_j_by_g_dt, nb_jeux_loc_ct, moy_j_by_g_ct, max_j_by_g_ct";
								    bw3.write(content);
							    }
							    
								double time2 = 0;
								double time3 = 0;
								int size = 0;
								int ind_jeux_local;
								boolean ok2;
								boolean ok3;
									   
								System.out.println("JEUX "+i);
								
								BEL_GSG belGSG = g.generate_BEL_GSG(universe, attMethod, defMethod, xeu, Dtransfo);
								belGSG.computeUtilities();
								belGSG.writeInFile("Dtransfo.txt");
								belGSG.setTransfo(Ctransfo);
								belGSG.computeUtilities();
								belGSG.writeInFile("Ctransfo.txt");

								size = belGSG.getNodes().size();
								belGSG = null;
								
								float moy1 = 0;
								float moy2 = 0;
								int max1 = 0;
								int max2 = 0;
								
								Reader reader = new Reader();
								
								ArrayList<Object> resDtransfo = reader.readHypergraphiqueGame("Dtransfo.txt");
								ArrayList<Object> resCtransfo = reader.readHypergraphiqueGame("CTransfo.txt");
								
								Map<Integer, ArrayList<int[]>> profilsDtransfo = (Map<Integer, ArrayList<int[]>>) resDtransfo.get(0);
								Map<Integer, ArrayList<float[]>> utilitesDtransfo = (Map<Integer, ArrayList<float[]>>) resDtransfo.get(1);
								Map<Integer, ArrayList<int[]>> profilsCtransfo = (Map<Integer, ArrayList<int[]>>) resCtransfo.get(0);
								Map<Integer, ArrayList<float[]>> utilitesCtransfo = (Map<Integer, ArrayList<float[]>>) resCtransfo.get(1);
								
								ArrayList<ArrayList<Integer>> player_by_gameDtransfo = (ArrayList<ArrayList<Integer>>) resDtransfo.get(2);
								ArrayList<ArrayList<Integer>> player_by_gameCtransfo = (ArrayList<ArrayList<Integer>>) resCtransfo.get(2);
								
								ok2 = true;
								for ( ArrayList<Integer> nb_player : player_by_gameDtransfo) {
									moy1 += nb_player.size();
									if (nb_player.size() > max1) {
										max1 = nb_player.size();
									}
									if (nb_player.size() > 11-nb_location) {
										ok2 = false;
									}
								}
								moy1 = moy1/player_by_gameDtransfo.size();
								
								ok3 = true;
								for ( ArrayList<Integer> nb_player : player_by_gameCtransfo) {
									moy2 += nb_player.size();
									if (nb_player.size() > max2) {
										max2 = nb_player.size();
									}
									if (nb_player.size() > 11-nb_location) {
										ok3 = false;
									}
								}
								moy2 = moy2/player_by_gameCtransfo.size();
								
								bw2.write("Jeux " +i+"\n\n");
								bw2.write(universe.toString());
								bw2.write("\n nombre de joueurs créés : "+size+"\n");
								ind_jeux_local = 0;
								bw2.write("Nombre de joueur dans les jeux locaux de la transfo direct : \n");
								for ( ArrayList<Integer> nb_player : player_by_gameDtransfo) {
									bw2.write("nombre de joueur dans le jeux local "+ind_jeux_local+" : "+nb_player.size()+"\n");
									ind_jeux_local++;
								}
								ind_jeux_local = 0;
							    bw2.write("Nombre de joueur dans les jeux locaux de la transfo conditionnée : \n");
							    for ( ArrayList<Integer> nb_player : player_by_gameCtransfo) {
							    	bw2.write("nombre de joueur dans le jeux local "+ind_jeux_local+" : "+nb_player.size()+"\n");
							    	ind_jeux_local++;
								}
								bw2.write("\n\n");
								
								if (ok2) {
									
									Trabelsi_for_hypergraphical_games trabel2 = new Trabelsi_for_hypergraphical_games(profilsDtransfo,utilitesDtransfo,player_by_gameDtransfo,size);
									Trabelsi_for_hypergraphical_games trabel3 = new Trabelsi_for_hypergraphical_games(profilsCtransfo,utilitesCtransfo,player_by_gameCtransfo,size);
									
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
									
									content = "Jeux" + i + ", "+time2+", "+time3+", "+equi1+", "+equi2+", "+classe+"\n";
									bw.write(content);
									
								}
								
								else {
									if (ok3) {
										Trabelsi_for_hypergraphical_games trabel3 = new Trabelsi_for_hypergraphical_games(profilsCtransfo,utilitesCtransfo,player_by_gameCtransfo,size);
										trabel3.construct_model();
										time3 = trabel3.get_solving_time();
										boolean equi2 = trabel3.equilibrium();
										
										content = "Jeux" + i + ", "+"Not compute"+", "+time3+", "+"Not compute"+", "+equi2+", "+1+"\n";
										bw.write(content);
									}
									else {
										content = "Jeux" + i + ", "+"Not compute"+", "+"Not compute"+", "+"Not compute"+", "+"Not compute"+", "+0+"\n";
										bw.write(content);
									}
								}
								
								bw3.write(universe.toCSV()+", "+size+", "+player_by_gameDtransfo.size()+", "+moy1+", "+max1+", "+player_by_gameCtransfo.size()+", "+moy2+", "+max2+"\n");
								bw.close();
								bw2.close();
								bw3.close();
							}
							
							catch (IOException e) {
								   e.printStackTrace();
								  }
						}
						indice_fichier++;
					}
				}
			}
		}

	}

}
