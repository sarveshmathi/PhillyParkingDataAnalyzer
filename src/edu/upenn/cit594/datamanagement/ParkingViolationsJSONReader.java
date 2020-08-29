package edu.upenn.cit594.datamanagement;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.upenn.cit594.data.ParkingViolation;


public class ParkingViolationsJSONReader implements ParkingViolationsReader {
	
	protected String filename;
	
	public ParkingViolationsJSONReader(String name) {
		filename = name;
	}
	
	@Override
	public List<ParkingViolation> getAllViolations() {
		
		List<ParkingViolation> violations = new ArrayList<ParkingViolation>();
		
		JSONParser parser = new JSONParser();
		
		try {
			
			JSONArray violationObjects = (JSONArray) parser.parse(new FileReader(filename));
			
			Iterator<JSONObject> iter = violationObjects.iterator();
			
			while (iter.hasNext()) {
				
				ParkingViolation violation =  processJSONObject((JSONObject) iter.next());
		
				violations.add(violation);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return violations;
	}

	
	
	private ParkingViolation processJSONObject(JSONObject object) {
		
	
		String timestamp = (String) object.get("date");
		
		long fine = (long) object.get("fine");
		
		String description = (String) object.get("violation");
		
		String vehicleId = (String) object.get("plate_id");;
		
		String state = (String) object.get("state");
		
		long violationIdInt = (long) object.get("ticket_number");
		 
		String violationId = Long.toString(violationIdInt);
		
		String zipcode = (String) object.get("zip_code");
		
		ParkingViolation violation = new ParkingViolation(timestamp, fine, description, vehicleId, state, violationId, zipcode);
		
		return violation;
		
	}

	
	


}
