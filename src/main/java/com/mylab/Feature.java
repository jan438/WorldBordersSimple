package com.mylab;

import java.util.HashMap;
import java.util.Map;

public class Feature {

	private String type = "type";
	private Map<String, Object> properties = new HashMap<String, Object>();
	private Map<String, Object> geometry = new HashMap<String, Object>();
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public Map<String, Object> getGeometry() {
		return geometry;
	}

	public void setGeometry(Map<String, Object> geometry) {
		this.geometry = geometry;
	}

}
