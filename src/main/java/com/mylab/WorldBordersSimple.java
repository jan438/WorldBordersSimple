package com.mylab;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class WorldBordersSimple {

	static ArrayList<Feature> features;
	static LinkedHashMap<String, Object> geometry;
	static Feature feature;
	static String name;
	static ArrayList<String> coordinates;
	static String type;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		try {
			InputStream inputstream = new FileInputStream(
					"/home/jan/Downloads/TM_WORLD_SIMPLE/TM_WORLD_SIMPLE.json");
			FeatureCollection featureCollection = new FeatureCollection();
			ObjectMapper mapper = new ObjectMapper();
			featureCollection = mapper.readValue(inputstream, FeatureCollection.class);
			inputstream.close();
			System.out.println(featureCollection.getType());
			features = featureCollection.getFeatures();
			System.out.println(features.size());
			Iterator<Feature> fit = features.iterator();
			int fcount = 0;
			while (fit.hasNext()) {
				feature = fit.next();
				Map<String, String> properties = feature.getProperties();
				name = properties.get("NAME");
				geometry = (LinkedHashMap<String, Object>) feature.getGeometry();
				System.out.println("FCount: " + fcount + " : " + name);
				type = (String) geometry.get("type");
				System.out.println("Geometry type: " + type);
				if (type.equals("MultiPolygon")) {
					coordinates = (ArrayList<String>) geometry.get("coordinates");
					System.out.println("Number of polygons: " + coordinates.size());
					Iterator<String> cit = coordinates.iterator();
					int ccount = 0;
					while (cit.hasNext()) {
						Object coordinate = cit.next();
						ArrayList<String> latlng = (ArrayList<String>) coordinate;
						Iterator<String> po = latlng.iterator();
						int pcount = 0;
						while (po.hasNext()) {
							Object s = po.next();
							ArrayList<String> pp = (ArrayList<String>) s;
							Iterator<String> kk = pp.iterator();
							while (kk.hasNext()) {
								Object point = kk.next();
								System.out.println("Point: " + point);
							}
							pcount++;
						}
						ccount++;
					}
					System.out.println("Total coordinates: " + ccount);
				}
				if (type.equals("Polygon")) {
					coordinates = (ArrayList<String>) geometry.get("coordinates");
					System.out.println("Number of polygons: " + coordinates.size());
					Iterator<String> cit = coordinates.iterator();
					int ccount = 0;
					while (cit.hasNext()) {
						Object coordinate = cit.next();
						ArrayList<String> latlng = (ArrayList<String>) coordinate;
						Iterator<String> po = latlng.iterator();
						int pcount = 0;
						while (po.hasNext()) {
							Object s = po.next();
							ArrayList<String> pp = (ArrayList<String>) s;
							Iterator<String> kk = pp.iterator();
							while (kk.hasNext()) {
								Object point = kk.next();
								System.out.println("Point: " + point);
							}
							pcount++;
						}
						ccount++;
					}
					System.out.println("Total coordinates: " + ccount);
				}
				fcount++;
				if (fcount==2) break;
			}
			System.out.println("Total features: " + fcount);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
