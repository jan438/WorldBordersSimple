package com.mylab;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class WorldBordersSimple {

	static ArrayList<Feature> features;
	static Feature feature;
	static Geometry geometry;
	
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
			Iterator<Feature> it = features.iterator();
			while (it.hasNext()) {
				feature = it.next();
				System.out.println(feature.getProperties());
				System.out.println(feature.getGeometry());
			}
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
