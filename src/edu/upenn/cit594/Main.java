package edu.upenn.cit594;

import java.io.File;
import edu.upenn.cit594.datamanagement.ParkingViolationsCSVReader;
import edu.upenn.cit594.datamanagement.ParkingViolationsJSONReader;
import edu.upenn.cit594.datamanagement.ParkingViolationsReader;
import edu.upenn.cit594.datamanagement.PopulationDataReader;
import edu.upenn.cit594.datamanagement.PopulationDataTextReader;
import edu.upenn.cit594.datamanagement.PropertyValuesCSVReader;
import edu.upenn.cit594.datamanagement.PropertyValuesReader;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.*;
import edu.upenn.cit594.ui​.UserInterface;

public class Main {

	public static void main(String[] args) {

		if (args.length != 5) {
			System.out.println("Incorrect arguments.");
			System.exit(0);
		}

		if (!args[0].equals("csv") && !args[0].equals("json")) {
			System.out.println("Incorrect file format specified.");
			System.exit(0);
		}

		String parkingViolationsFileFormat = args[0];

		String parkingViolationsFileName = args[1]; 

		String propertyValuesFileName = args[2];

		String populationFileName = args[3]; 

		String logFileName = args[4];

		Logger.filename = logFileName;
		
		Logger.instantiateLogger(); //creates Logger instance

		String logStart = Long.toString(System.currentTimeMillis()) + " " + parkingViolationsFileFormat + " " + parkingViolationsFileName
				+ " " + propertyValuesFileName + " " + populationFileName + " " + logFileName;
		
		Logger.getInstance().log(logStart); 

		if (!areFilesValid(parkingViolationsFileName, propertyValuesFileName, populationFileName)) {
			System.out.println("One or more files do not exit or cannot be read.");
			System.exit(0);
		}

		initDependencies(parkingViolationsFileFormat, parkingViolationsFileName, propertyValuesFileName, populationFileName);
	}

	private static boolean areFilesValid(String parkingViolationsFileName, String propertyValuesFileName, 
			String populationFileName) { 

		File parkingViolationsFile = new File(parkingViolationsFileName);
		
		Logger.getInstance().log(Long.toString(System.currentTimeMillis()) + " " + parkingViolationsFileName);

		File propertyValuesFile = new File(propertyValuesFileName);
		
		Logger.getInstance().log(Long.toString(System.currentTimeMillis()) + " " + propertyValuesFileName);

		File populationFile = new File(populationFileName);
		
		Logger.getInstance().log(Long.toString(System.currentTimeMillis()) + " " + populationFileName);

		if (!parkingViolationsFile.exists() || !parkingViolationsFile.canRead()) {
			return false;
		}

		if (!propertyValuesFile.exists() || !propertyValuesFile.canRead()) {		
			return false;
		}

		if (!populationFile.exists() || !populationFile.canRead()) {	
			return false;
		}

		return true;
	}

	private static void initDependencies(String parkingViolationsFileFormat, String parkingViolationsFileName, String propertyValuesFileName, 
			String populationFileName) {
		
		ParkingViolationsReader parkingViolationReader = null;

		if (parkingViolationsFileFormat.equals("csv")) {	

			parkingViolationReader = new ParkingViolationsCSVReader(parkingViolationsFileName);	

		} else { 

			parkingViolationReader = new ParkingViolationsJSONReader(parkingViolationsFileName);

		}	

		PropertyValuesReader propertyValuesReader = new PropertyValuesCSVReader(propertyValuesFileName);
		
		PopulationDataReader populationDataReader = new PopulationDataTextReader(populationFileName);
		
		PopulationProcessor populationProcessor = new PopulationProcessor(populationDataReader);
		
		ParkingViolationsProcessor parkingViolationsProcessor = new ParkingViolationsProcessor(parkingViolationReader, populationProcessor);
		
		PropertiesProcessor propertiesProcessor = new PropertiesProcessor(propertyValuesReader, populationProcessor);

		UserInterface ui = new UserInterface(populationProcessor, parkingViolationsProcessor, propertiesProcessor); 

		ui.start();
	}


}
