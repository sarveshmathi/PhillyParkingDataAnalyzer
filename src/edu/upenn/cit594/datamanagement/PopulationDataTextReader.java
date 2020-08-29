package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;



public class PopulationDataTextReader implements PopulationDataReader {
	
	protected String filename;
	
	public PopulationDataTextReader(String name) {
		filename = name; 
	}
	
	@Override
	public Map<String, Integer> getAllPopulations() {
		
		Map<String, Integer> populations = new HashMap<>();
		
		Scanner in = null;
		
		try {
			
			in = new Scanner(new File(filename));
			
			while (in.hasNextLine()) {
				String line = in.nextLine();
				
				String[] components = line.split(" ");
				
				String zipcode = components[0];
				
				int population = Integer.parseInt(components[1]);
				
				populations.put(zipcode, population);
				
			}
			
		} catch(Exception e){
			
			throw new IllegalStateException(e);
			
		} finally {
			
			in.close(); 
			
		}
		
		return populations;
	}
	


}
