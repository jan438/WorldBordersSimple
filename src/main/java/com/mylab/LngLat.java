package com.mylab;

import org.geojson.LngLatAlt;

public class LngLat extends LngLatAlt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double longitude;
	private double latitude;
	
	public LngLat() {
	}
	
	public LngLat(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return "[" + longitude + ", " + latitude + "]";
	}
}
