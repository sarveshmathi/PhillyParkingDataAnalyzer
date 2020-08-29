package edu.upenn.cit594.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.upenn.cit594.data.PropertyValue;
import edu.upenn.cit594.datamanagement.PropertyValuesReader;

public class PropertiesProcessor {
	protected PropertyValuesReader propertyValuesReader;
	protected PopulationProcessor populationProcessor;
	protected List<PropertyValue> allProperties;
	protected Map<String, Integer> populationMap;

	//Memo for total market value and living area because they are used in multiple operations
	protected Map<String, Double> zipToTotalMarketValueMemo; 
	protected Map<String, Double> zipToTotalLivingAreaMemo;
	
	//Memo for average market value and average living area, in case query for a zip code is repeated
	protected Map<String, Double> zipToAverageMarketValueMemo; 
	protected Map<String, Double> zipToAverageLivingAreaMemo;
	
	//Memo for totalResidentialMarketValuePerCapita, in case query for a zip code is repeated
	protected Map<String, Double> zipToResidentialMarketValuePerCapitaMemo;
	
	//Memo for additional feature 
	protected String zipWithLowestMarketValuePerCapitaMemo;

	public PropertiesProcessor(PropertyValuesReader propertyValuesReader, PopulationProcessor populationProcessor) {
		super();
		this.propertyValuesReader = propertyValuesReader;
		this.populationProcessor = populationProcessor;
		this.allProperties = propertyValuesReader.getAllPropertyValues();
		this.populationMap = populationProcessor.getPopulationMap();

		zipToTotalMarketValueMemo = new HashMap<>();
		zipToTotalLivingAreaMemo = new HashMap<>();
		zipToAverageMarketValueMemo = new HashMap<>();
		zipToAverageLivingAreaMemo = new HashMap<>();
		zipToResidentialMarketValuePerCapitaMemo = new HashMap<>();
		zipWithLowestMarketValuePerCapitaMemo = "";
	}

	public double averageMarketValue(String zipcode) {	
		return average(zipcode, new MarketAreaStrategy() );
	}

	public double averageLivingArea(String zipcode) {
		return average(zipcode, new LivingAreaStrategy());
	}


	private double average(String zipcode, PropertiesAverageStrategy pas) {
		
		//check if it exists in memo
		if (pas instanceof MarketAreaStrategy) {
			if (zipToAverageMarketValueMemo.containsKey(zipcode)) {		
				return zipToAverageMarketValueMemo.get(zipcode);
			}
		} else if (pas instanceof LivingAreaStrategy) {
			if (zipToAverageLivingAreaMemo.containsKey(zipcode)) {
				return zipToAverageLivingAreaMemo.get(zipcode);
			}
		}

		int count = 0;
		double total = 0;
		double output = 0;
		
		for (PropertyValue pv : allProperties) {
			String zip = pv.getZipcode();
			if (zip.equals(zipcode)) {		
				try {
					double value = Double.parseDouble(pas.getValue(pv));
					total += value;
					count++;
				} catch (NumberFormatException ne){
					//do nothing
				}
			}
		}

		if (total == 0 || count == 0) {
			return 0;
		}

		output = total/count;
		
		//add the total values to memo
		if (pas instanceof MarketAreaStrategy) {
			zipToTotalMarketValueMemo.put(zipcode, total);
			zipToAverageMarketValueMemo.put(zipcode, output);
		} else if (pas instanceof LivingAreaStrategy) {
			zipToTotalLivingAreaMemo.put(zipcode, total);
			zipToAverageLivingAreaMemo.put(zipcode, output);
		}

		return output;
	}

	public double totalResidentialMarketValuePerCapita(String zipcode) {
		
		//check if it exits in memo
		if (zipToResidentialMarketValuePerCapitaMemo.containsKey(zipcode)) {
			
			return zipToResidentialMarketValuePerCapitaMemo.get(zipcode);
		}

		if (!populationMap.containsKey(zipcode)) {
			return 0;
		}

		int population = populationMap.get(zipcode);

		if (population == 0) {
			return 0;
		}

		double total = 0;

		//check if total exists in memo
		if (zipToTotalMarketValueMemo.containsKey(zipcode)) {
			total = zipToTotalMarketValueMemo.get(zipcode);
		} else {
			for (PropertyValue pv : allProperties) {
				if (zipcode.equals(pv.getZipcode())) {
					try {
						double marketValue = Double.parseDouble(pv.getMarketValue());
						total += marketValue;
					} catch (NumberFormatException ne) {
						//do nothing
					}
				}
			}
		}


		if (total == 0) {
			return 0;
		}

		//put answer in memo
		zipToResidentialMarketValuePerCapitaMemo.put(zipcode, total/population);
		
		return total/population;
	}
	
	//used in additional feature
	public String zipcodeWithLowestMarketValuePerCapita() {	
		
		if (!zipWithLowestMarketValuePerCapitaMemo.equals("")) {
			return zipWithLowestMarketValuePerCapitaMemo;
		}
		
		double min = Double.MAX_VALUE;
		String minZipcode = "";
		Map<String, Double> map = new HashMap<>();
		
		for (PropertyValue pv : allProperties) {
			String zipcode = pv.getZipcode();
			if (populationMap.containsKey(zipcode)) {
				
				//check memo if total market value for zip code already exists
				if (zipToTotalMarketValueMemo.containsKey(zipcode)) {
					double totalMarketValue = zipToTotalMarketValueMemo.get(zipcode);
					map.put(zipcode, totalMarketValue);
				} else {
					try {
						double marketValue = Double.parseDouble(pv.getMarketValue());
						if (map.containsKey(zipcode)) {
							double current = map.get(zipcode);
							current += marketValue;
							map.put(zipcode, current);
						} else {
							map.put(zipcode, marketValue);
						}
					} catch (NumberFormatException ne) {
						// do nothing
					}
				}
				
			}
		}
		
		for (String zipcode : map.keySet()) {
			double mvPerCapita = map.get(zipcode)/populationMap.get(zipcode);
			if (mvPerCapita < min) {
				min = mvPerCapita;
				minZipcode = zipcode;
			}
		}
		
		zipWithLowestMarketValuePerCapitaMemo = minZipcode;
		
		return minZipcode;
	}


}

