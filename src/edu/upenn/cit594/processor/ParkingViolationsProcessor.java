package edu.upenn.cit594.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.datamanagement.ParkingViolationsReader;

public class ParkingViolationsProcessor {
	
	protected ParkingViolationsReader parkingViolationsReader;
	protected PopulationProcessor populationProcessor;
	protected List<ParkingViolation> allViolations;
	protected Map<String, Integer> populationMap;
	
	//This map will serve as memo so answer does not need to be recomputed 
	private Map<String, Double> totalFinesPerCapitaMemo;
	private Map<String, Double> totalFinesForZipcodeMemo;
	
	
	
	public ParkingViolationsProcessor(ParkingViolationsReader parkingViolationsReader, PopulationProcessor populationProcessor) {
		super();
		this.parkingViolationsReader = parkingViolationsReader;
		this.populationProcessor = populationProcessor;
		this.allViolations = parkingViolationsReader.getAllViolations();
		this.populationMap = populationProcessor.getPopulationMap();
		this.totalFinesForZipcodeMemo = new HashMap<>();
	} 
	
	public Map<String, Double> totalFinesPerCapita() {
		
		//check if output is already computed and stored in memo
		if (totalFinesPerCapitaMemo != null) {
			return totalFinesPerCapitaMemo;
		}
		
		//if not in memo compute value
		Map<String, Double> zipcodeFinesMap = new HashMap<>();
		Map<String, Double> output = new TreeMap<>();
 
		for (ParkingViolation pv : allViolations) {
			String zipcode = pv.getZipcode();
			String state = pv.getState();

			if (!zipcode.isEmpty() && state.equals("PA")) {
				double fine = pv.getFine();

				if (zipcodeFinesMap.containsKey(zipcode)) {
					double current = zipcodeFinesMap.get(zipcode);
					zipcodeFinesMap.put(zipcode, current+=fine);	
				} else {
					zipcodeFinesMap.put(zipcode, fine);
				}
			}
		}

		for (String zipcode : zipcodeFinesMap.keySet()) {
			if (populationMap.containsKey(zipcode)) {
				int population = populationMap.get(zipcode);
				double aggregateFines = zipcodeFinesMap.get(zipcode);
				if (population != 0 && aggregateFines != 0) {
					double average = aggregateFines/population;
						output.put(zipcode, average);
				}	
			}
		}
		
		totalFinesPerCapitaMemo = output;
		
		return output;
	}

	//used in additional feature
	public double totalValueOfFinesForZipcode(String zipcode) {	
		
		//check if value exists in memo
		if (totalFinesForZipcodeMemo.containsKey(zipcode)) {
			return totalFinesForZipcodeMemo.get(zipcode);
		}
		
		double total = 0;	
		for (ParkingViolation pv : allViolations) {
			if (pv.getZipcode().equals(zipcode)) {
				total += pv.getFine();
			}
		}
		
		totalFinesForZipcodeMemo.put(zipcode, total);
		
		return total;
	}
	

}
