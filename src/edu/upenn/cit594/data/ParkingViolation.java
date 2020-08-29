package edu.upenn.cit594.data;

public class ParkingViolation {
	private String date;
	private long fine;
	private String description;
	private String vehicleId;
	private String state;
	private String violationId;
	private String zipcode;
	
	public ParkingViolation(String date, long fine, String description, String vehicleId, String state,
			String violationId, String zipcode) {
		this.date = date;
		this.fine = fine;
		this.description = description;
		this.vehicleId = vehicleId;
		this.state = state;
		this.violationId = violationId;
		this.zipcode = zipcode;
	}

	public String getDate() {
		return date;
	}

	public long getFine() {
		return fine;
	}

	public String getDescription() {
		return description;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public String getState() {
		return state;
	}

	public String getViolationId() {
		return violationId;
	}

	public String getZipcode() {
		return zipcode;
	}
	
	@Override
	public String toString() {
		return "ParkingViolation [date=" + date + ", fine=" + fine + ", description=" + description + ", vehicleId="
				+ vehicleId + ", state=" + state + ", violationId=" + violationId + ", zipcode=" + zipcode + "]";
	}
	
		
}
