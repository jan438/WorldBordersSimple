package com.mylab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Geometry {

	private String type = "type";
	private ArrayList<Feature> coordinates = new ArrayList<Feature>();

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<Feature> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(ArrayList<Feature> coordinates) {
		this.coordinates = coordinates;
	}

}
