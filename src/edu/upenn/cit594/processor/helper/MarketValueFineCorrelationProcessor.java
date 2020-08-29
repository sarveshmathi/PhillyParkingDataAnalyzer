package edu.upenn.cit594.processor.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.PropertyValue;

public class MarketValueFineCorrelationProcessor {
	
	protected List<ParkingViolation> allViolations; 
	protected List<PropertyValue> allProperties;
	protected Map<String, Integer> populationMap;
	
	
	
	
	public MarketValueFineCorrelationProcessor(List<ParkingViolation> allViolations, List<PropertyValue> allProperties,
			Map<String, Integer> populationMap) {
		super();
		this.allViolations = allViolations;
		this.allProperties = allProperties;
		this.populationMap = populationMap;
	}
	
	


//	public double getMarketValueFineCorrelation() {
//		Map<String, Double> zipMarketValueMap = new HashMap<>();
//		Map<String, Double> zipFineMap = new HashMap<>();
//		
//		for (PropertyValue pv : allProperties) {
//			String zipcode = pv.getZipcode();
//			if (zipMarketValueMap.containsKey(zipcode)) {
//				double currentTotal = zipMarketValueMap.get(zipcode);
//				try {
//					int marketValue = Integer.parseInt(pv.getMarketValue());
//					currentTotal += marketValue;
//					zipMarketValueMap.put(zipcode, currentTotal);
//				} catch(NumberFormatException ne) {
//					//do nothing
//				}
//			} else {
//				try {
//					double marketValue = Integer.parseInt(pv.getMarketValue());
//					zipMarketValueMap.put(zipcode, marketValue);
//				} catch(NumberFormatException ne) {
//					//do nothing
//				}
//			}
//		}
//		
//		for (ParkingViolation pv : allViolations) {
//			String zipcode = pv.getZipcode();
//			if (zipMarketValueMap.containsKey(zipcode)) {
//				double fine = pv.getFine();
//				if (zipFineMap.containsKey(zipcode)) {
//					double currentTotal = zipFineMap.get(zipcode);
//					currentTotal += fine;
//					zipFineMap.put(zipcode, currentTotal);
//				} else {
//					zipFineMap.put(zipcode, fine);
//				}
//			}
//		}
//		 
//		 ArrayList<Double> marketValues = new ArrayList<>();
//		 ArrayList<Double> fines = new ArrayList<>();
//		 
//		 for (String zipcode : zipFineMap.keySet()) {
//			 double fine = zipFineMap.get(zipcode);
//			 double marketValue = zipMarketValueMap.get(zipcode);
//			 fines.add(fine);
//			 marketValues.add(marketValue);
//		 }		 
//		 
//		 double correlation = correlationCoefficient(marketValues, fines);
//		 
//		 return correlation;
//	}
//	
//	public double correlationCoefficient(ArrayList<Double> xs, ArrayList<Double> ys) {
//	    //TODO: check here that arrays are not null, of the same length etc
//
//	    double sx = 0.0;
//	    double sy = 0.0;
//	    double sxx = 0.0;
//	    double syy = 0.0;
//	    double sxy = 0.0;
//
//	    int n = xs.size();
//
//	    for(int i = 0; i < n; ++i) {
//	      double x = xs.get(i);
//	      double y = ys.get(i);
//
//	      sx += x;
//	      sy += y;
//	      sxx += x * x;
//	      syy += y * y;
//	      sxy += x * y;
//	    }
//
//	    // covariation 
//	    double cov = sxy / n - sx * sy / n / n;
//	    // standard error of x
//	    double sigmax = Math.sqrt(sxx / n -  sx * sx / n / n);
//	    // standard error of y
//	    double sigmay = Math.sqrt(syy / n -  sy * sy / n / n);
//
//	    // correlation is just a normalized covariation 
//	    return cov / sigmax / sigmay;
//	  }
	

}
