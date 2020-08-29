package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import edu.upenn.cit594.data.PropertyValue;

public class PropertyValuesCSVReader implements PropertyValuesReader {
	
	protected String filename;
	private HashMap<String, Integer> headerFieldMap = new HashMap<>();
	
	public PropertyValuesCSVReader(String name) {
		filename = name;
	}
	
	@Override
	public List<PropertyValue> getAllPropertyValues() {
		
		List<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
		
		
		Scanner in = null;
		
		try {
			in = new Scanner(new File(filename));
			
			if (in.hasNextLine()) {
				String headerRow = in.nextLine();
				processHeaders(headerRow);
			}
			
			while(in.hasNextLine()) {
				
				String line = in.nextLine();
				
				PropertyValue propertyValue = processLine(line);
				
				propertyValues.add(propertyValue);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return propertyValues; 
	}

	private void processHeaders(String line) {
		String[] components = line.split(",");

		for (int i = 0; i < components.length; i++) {	
			String header = components[i];
			headerFieldMap.put(header, i); 
		}	
	}
	
	
	private PropertyValue processLine(String line) {
		
		//replaces commas found within quotes with a space
		StringBuilder sb = new StringBuilder(line);
		boolean inQuotes = false;
		for (int currentIndex = 0; currentIndex < sb.length(); currentIndex++) {
			
		    char currentChar = sb.charAt(currentIndex);
		    
		    if (currentChar == '\"') {
		    	inQuotes = !inQuotes; // toggle state
		    }
		    
		    if (currentChar == ',' && inQuotes) {
		        sb.setCharAt(currentIndex, ' '); // replace commas in fields with space
		    }
		}
		
		//splits the modified string based on commas
		String[] components = sb.toString().split(",");
		
		String marketValue = components[headerFieldMap.get("market_value")]; 
		
		String totalLivableArea = components[headerFieldMap.get("total_livable_area")];
		
		String zipcodeFull = components[headerFieldMap.get("zip_code")];
		
		String zipcode;
		
		if (zipcodeFull.length() >= 5) {
			zipcode = zipcodeFull.substring(0, 5);
		} else {
			zipcode = ""; //invalid zip
		}
		
		PropertyValue pv = new PropertyValue(marketValue, totalLivableArea, zipcode);
		
		return pv;
	}



	
	

}
