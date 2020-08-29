package edu.upenn.cit594.processor;

import java.util.Map;
import edu.upenn.cit594.datamanagement.PopulationDataReader;


public class PopulationProcessor {
	
	protected PopulationDataReader populationDataReader;
	protected Map<String, Integer> populationMap;
	
	//This variable is a memo, in case the operation is repeated
	protected int totalPopulationForAllZipCodesMemo = -1;
	
	public PopulationProcessor(PopulationDataReader populationDataReader) {
		super();
		this.populationDataReader = populationDataReader;
		this.populationMap = populationDataReader.getAllPopulations();
	}
	
	
	public Map<String, Integer> getPopulationMap() {
		return populationMap;
	}


	public int totalPopulationForAllZipCodes() {
		
		//check if value was already computed and stored in memo
		if (totalPopulationForAllZipCodesMemo != -1) {
			return totalPopulationForAllZipCodesMemo;
		}
		
		//if not in memo, compute value
		int total = 0;
		
		for (String zipcode : populationMap.keySet()) {
			total += populationMap.get(zipcode);
		}
		
		totalPopulationForAllZipCodesMemo = total;

		return total; 
	}	
	
}

