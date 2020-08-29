package edu.upenn.cit594.ui​;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Scanner;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.*;

public class UserInterface {

	protected PopulationProcessor populationProcessor;
	protected ParkingViolationsProcessor parkingViolationsProcessor;
	protected PropertiesProcessor propertiesProcessor;
	protected Scanner in;
	 
	

	public UserInterface(PopulationProcessor populationProcessor, ParkingViolationsProcessor parkingViolationsProcessor,
			PropertiesProcessor propertiesProcessor) {
		super();
		this.populationProcessor = populationProcessor;
		this.parkingViolationsProcessor = parkingViolationsProcessor;
		this.propertiesProcessor = propertiesProcessor;
		this.in = new Scanner(System.in);
	}

	public void start() {

		printOptions();

		while (in.hasNextInt()) {

			int choice = in.nextInt();
			in.nextLine();

			Logger.getInstance().log(Long.toString(System.currentTimeMillis()) + " " + choice);

			if (choice == 0) {
				System.out.println("Exiting program");
				System.exit(0);	
			} else if (choice == 1) {
				System.out.println(populationProcessor.totalPopulationForAllZipCodes());
			} else if (choice == 2) {
				Map<String, Double> totalFinesPerCapita = parkingViolationsProcessor.totalFinesPerCapita();
				for (String zipcode : totalFinesPerCapita.keySet()) {
					System.out.println(zipcode + " " + formattedDouble(totalFinesPerCapita.get(zipcode)));
				}
			} else if (choice == 3 || choice == 4 || choice == 5) {
				System.out.println("Please enter a zipcode: ");
				if (in.hasNextLine()) {
					String zipcode = in.nextLine();
					Logger.getInstance().log(Long.toString(System.currentTimeMillis()) + " " + zipcode);
					if (choice == 3) {
						System.out.println((int) propertiesProcessor.averageMarketValue(zipcode));
					} else if (choice == 4) {
						System.out.println((int) propertiesProcessor.averageLivingArea(zipcode));	
					} else if (choice == 5) {
						System.out.println((int) propertiesProcessor.totalResidentialMarketValuePerCapita(zipcode));	
					}
				}
			} else if (choice == 6) {
				String zipcode = propertiesProcessor.zipcodeWithLowestMarketValuePerCapita();
				int totalFines = (int) parkingViolationsProcessor.totalValueOfFinesForZipcode(zipcode);
				System.out.println(zipcode + " " + totalFines);
			} else {
				System.out.println("Invalid choice. Exiting Program.");
				System.exit(0);	
			}

			printOptions();

		} 


		in.close(); 
	}

	//truncates double to 4 digits after decimal points and adds 0 padding in the end if necessary
	private String formattedDouble(double d) {
		DecimalFormat format = new DecimalFormat("#.####");
		format.setRoundingMode(RoundingMode.DOWN);
		StringBuilder sb = new StringBuilder(format.format(d));
		if(sb.indexOf(".") != -1){
			while(sb.substring(sb.indexOf(".")).length() < 5) {
				sb.append('0');
			}
		}
		return sb.toString();
	}

	//program options
	public void printOptions() {
		System.out.println("Select an option between 0-6:");
		System.out.println("0. Exit");
		System.out.println("1. Total Population For All Zipcodes");
		System.out.println("2. Total Fines Per Capita");
		System.out.println("3. Average Market Value By Zip");
		System.out.println("4. Average Total Livable Area By Zip");
		System.out.println("5. Total Residential Market Value Per Capita By Zip");
		System.out.println("6. Total Value Of Fines For Zipcode With Lowest Market Value Per Capita");	
	}

}
