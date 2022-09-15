package Mains;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import Model_cplex.Daskalakis_model;
import Model_cplex.Trabelsi_for_hypergraphical_games;
import Model_cplex.Trabelsi_model;
import Tools.Reader;

public class TestReader {
	
	public void testReadSNFGame() throws IOException {
		
		Reader r = new Reader();
		ArrayList<Object> res = r.readSNFGame("Test1.txt");
		ArrayList<int[]> profiles = (ArrayList<int[]>) res.get(0);
		ArrayList<float[]> utilities = (ArrayList<float[]>) res.get(1);
		
		for ( int[] tab : profiles) {
			for (int j=0; j<tab.length; j++) {
				System.out.print(tab[j]+" ");
			}
			System.out.println();
		}
		
		for ( float[] tab : utilities) {
			for (int j=0; j<tab.length; j++) {
				System.out.print(tab[j]+" ");
			}
			System.out.println();
		}
		
		Daskalakis_model daska = new Daskalakis_model(profiles,utilities);
		Trabelsi_model trabel = new Trabelsi_model(profiles,utilities);
		daska.construct_model();
		trabel.construct_model();
		System.out.println(daska.equilibrium()+" "+trabel.equilibrium());
		
		daska.print_results();
		trabel.print_results();
	}
	
	public void testReadHypergraphicalGame() throws IOException {
		
		Reader r = new Reader();
		ArrayList<Object> resDtransfo = r.readHypergraphiqueGame("DtransfoTest2.txt");
		ArrayList<Object> resCtransfo = r.readHypergraphiqueGame("CtransfoTest2.txt");
		
		Map<Integer, ArrayList<int[]>> profilsDtransfo = (Map<Integer, ArrayList<int[]>>) resDtransfo.get(0);
		Map<Integer, ArrayList<float[]>> utilitesDtransfo = (Map<Integer, ArrayList<float[]>>) resDtransfo.get(1);
		Map<Integer, ArrayList<int[]>> profilsCtransfo = (Map<Integer, ArrayList<int[]>>) resCtransfo.get(0);
		Map<Integer, ArrayList<float[]>> utilitesCtransfo = (Map<Integer, ArrayList<float[]>>) resCtransfo.get(1);
		
		ArrayList<ArrayList<Integer>> player_by_gameDtransfo = (ArrayList<ArrayList<Integer>>) resDtransfo.get(2);
		ArrayList<ArrayList<Integer>> player_by_gameCtransfo = (ArrayList<ArrayList<Integer>>) resCtransfo.get(2);
		
		int size = (int) resDtransfo.get(3);
		
		/**
		for ( Integer i : profilsDtransfo.keySet()) {
			System.out.println("Jeux"+i);
			for ( int[] tab : profilsDtransfo.get(i)) {
				for (int j=0; j<tab.length; j++) {
					System.out.print(tab[j]+" ");
				}
				System.out.println();
			}
		}
		
		for ( Integer i : utilitesDtransfo.keySet()) {
			System.out.println("Jeux"+i);
			for ( float[] tab : utilitesDtransfo.get(i)) {
				for (int j=0; j<tab.length; j++) {
					System.out.print(tab[j]+" ");
				}
				System.out.println();
			}
		}
		
		for ( ArrayList<Integer> a : player_by_gameDtransfo ) {
			System.out.println("List Player :");
			for ( Integer tab : a ) {
				System.out.println(tab);
			}
		}
		
		System.out.println("nbPlayer "+size);
		*/
		
		Trabelsi_for_hypergraphical_games trabel1 = new Trabelsi_for_hypergraphical_games(profilsDtransfo,utilitesDtransfo,player_by_gameDtransfo,size);
		Trabelsi_for_hypergraphical_games trabel2 = new Trabelsi_for_hypergraphical_games(profilsCtransfo,utilitesCtransfo,player_by_gameCtransfo,size);
		trabel1.construct_model();
		trabel2.construct_model();
		System.out.println(trabel1.equilibrium()+" "+trabel2.equilibrium());
		
		//trabel1.print_results();
		//trabel2.print_results();
	}
	
	public static void main(String[] args) throws IOException {
		
		TestReader t = new TestReader();
		//t.testReadSNFGame();
		t.testReadHypergraphicalGame();
		
	}

}
