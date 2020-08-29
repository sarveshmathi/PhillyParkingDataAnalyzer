package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.PropertyValue;

public class LivingAreaStrategy implements PropertiesAverageStrategy {

	@Override
	public String getValue(PropertyValue pv) {
		// TODO Auto-generated method stub
		return pv.getLivableArea();
	}

}
