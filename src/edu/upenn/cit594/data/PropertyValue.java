package edu.upenn.cit594.data;

public class PropertyValue {
	private String marketValue;
	private String livableArea;
	private String zipcode;
	
	public PropertyValue(String marketValue, String livableArea, String zipcode) {
		this.marketValue = marketValue;
		this.livableArea = livableArea;
		this.zipcode = zipcode;
	}

	public String getMarketValue() {
		return marketValue;
	}

	public String getLivableArea() {
		return livableArea;
	}

	public String getZipcode() {
		return zipcode;
	}
	
	@Override
	public String toString() {
		return "PropertyValue [marketValue=" + marketValue + ", livableArea=" + livableArea + ", zipcode=" + zipcode
				+ "]";
	}
	
}
