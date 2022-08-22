package Mains;

import java.io.IOException;
import java.util.ArrayList;

import Model_cplex.Daskalakis_model;
import Model_cplex.Trabelsi_model;
import Tools.Reader;

public class TestReader {
	
	public void testReadSNFGame() throws IOException {
		
		Reader r = new Reader();
		ArrayList<Object> res = r.readSNFGame("Test1.txt");
		ArrayList<int[]> profiles = (ArrayList<int[]>) res.get(0);
		ArrayList<float[]> utilities = (ArrayList<float[]>) res.get(1);
		
		Daskalakis_model daska = new Daskalakis_model(profiles,utilities);
		Trabelsi_model trabel = new Trabelsi_model(profiles,utilities);
		daska.construct_model();
		trabel.construct_model();
		System.out.println(daska.equilibrium()+" "+trabel.equilibrium());
		
		daska.print_results();
		trabel.print_results();
	}
	
	public static void main(String[] args) throws IOException {
		
		//testReadSNFGame();
		//testReadHypergraphicalGame();
		
	}

}
