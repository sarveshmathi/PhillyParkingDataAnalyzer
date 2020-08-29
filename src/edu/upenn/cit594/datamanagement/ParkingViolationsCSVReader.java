package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.upenn.cit594.data.ParkingViolation;


public class ParkingViolationsCSVReader implements ParkingViolationsReader {

	protected String filename;

	public ParkingViolationsCSVReader(String name) {
		filename = name; 
	}

	@Override
	public List<ParkingViolation> getAllViolations() {

		List<ParkingViolation> violations = new ArrayList<ParkingViolation>();

		Scanner in = null;

		try {

			in = new Scanner(new File(filename));

			while (in.hasNextLine()) {
				String line = in.nextLine();
				ParkingViolation violation = processLine(line);
				violations.add(violation);
			}

		} catch(Exception e){

			throw new IllegalStateException(e);

		} finally {

			in.close(); 

		}

		return violations;
	}



	private ParkingViolation processLine(String line) {

		String[] components = line.split(",");

		String timestamp = components[0];
		
		long fine = Long.parseLong(components[1]);
		
		String description = components[2];
		
		String vehicleId = components[3];
		
		String state = components[4];
		
		String violationId = components[5];
		
		String zipcode = "";
		
		if (components.length == 7) {
			zipcode = components[6];
		}
		
		
		

		ParkingViolation violation = new ParkingViolation(timestamp, fine, description, vehicleId, state, violationId, zipcode);
		
		return violation;
	}




}
