package com.mylab;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.geojson.LngLatAlt;

public class WorldBordersSimple {

	static ArrayList<Feature> features;
	static LinkedHashMap<String, Object> geometry;
	static Feature feature;
	static String name;
	static ArrayList<String> coordinates;
	static String type;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws ParseException {
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.forLanguageTag("en_US"));
		DecimalFormat df = (DecimalFormat)nf;
		String pattern = "##.#####";
		df.applyPattern(pattern);
		try {
			PrintWriter writer = new PrintWriter("netherlands_borders.txt", "UTF-8");
			InputStream inputstream = new FileInputStream(
					"/home/jan/Downloads/TM_WORLD_SIMPLE/TM_WORLD_SIMPLE.json");
			FeatureCollection featureCollection = new FeatureCollection();
			ObjectMapper mapper = new ObjectMapper();
			featureCollection = mapper.readValue(inputstream,
					FeatureCollection.class);
			inputstream.close();
			System.out.println(featureCollection.getType());
			features = featureCollection.getFeatures();
			System.out.println(features.size());
			Iterator<Feature> fit = features.iterator();
			int feature_count = 0;
			while (fit.hasNext()) {
				feature = fit.next();
				Map<String, String> properties = feature.getProperties();
				name = properties.get("NAME");
				geometry = (LinkedHashMap<String, Object>) feature
						.getGeometry();
				System.out.println("FCount: " + feature_count + " : " + name);
				if (name.equals("Netherlands")) {
					type = (String) geometry.get("type");
					System.out.println("Geometry type: " + type);
					if (type.equals("MultiPolygon")) {
						coordinates = (ArrayList<String>) geometry
								.get("coordinates");
						System.out.println("Number of polygons: "
								+ coordinates.size());
						Iterator<String> poly_it = coordinates.iterator();
						int poly_count = 0;
						while (poly_it.hasNext()) {
							Object polygon = poly_it.next();
							ArrayList<String> latlng = (ArrayList<String>) polygon;
							Iterator<String> po = latlng.iterator();
							int pcount = 0;
							while (po.hasNext()) {
								Object po_object = po.next();
								ArrayList<String> po_array = (ArrayList<String>) po_object;
								Iterator<String> longlat = po_array.iterator();
								while (longlat.hasNext()) {
									Object point = longlat.next();
									String s = point.toString();
									s = s.substring(1, s.length()-1);
									String[] parts = s.split(",");
									String longitude = parts[0];
									String latitude = parts[1];
									double dlon = Double.valueOf(longitude.trim()).doubleValue();
									double dlat = Double.valueOf(latitude.trim()).doubleValue();
									System.out.println("Poly: " + poly_count
											+ " Point: " + longitude + ", " + latitude);
									LngLatAlt lnglat = new LngLatAlt(dlon,dlat);
									writer.println(point);
									pcount++;
								}
							}
							poly_count++;
						}
					}
					if (type.equals("Polygon")) {
						coordinates = (ArrayList<String>) geometry
								.get("coordinates");
						System.out.println("Number of polygons: "
								+ coordinates.size());
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
								System.out.println("Point: " + s);
								writer.println(s);
								pcount++;
							}
							ccount++;
						}
						System.out.println("Total coordinates: " + ccount);
					}
				}
				feature_count++;
				/* if (feature_count==2) break; */
			}
			writer.close();
			System.out.println("Total features: " + feature_count);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
