package com.mylab;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
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
	static int line_count;
	static int count;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws ParseException {
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.forLanguageTag("en_US"));
		DecimalFormat df = (DecimalFormat)nf;
		String pattern = "##.#####";
		df.applyPattern(pattern);
		try {
			PrintWriter writer = new PrintWriter("netherlands_borders.js", "UTF-8");
			InputStream inputstream = new FileInputStream(
					"/home/jan/Downloads/TM_WORLD/TM_WORLD.json");
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
				if (name.equals("Netherlands")) {
					type = (String) geometry.get("type");
					System.out.println("Geometry type " + name + " : " + type );
					if (type.equals("MultiPolygon")) {
						coordinates = (ArrayList<String>) geometry
								.get("coordinates");
						line_count = coordinates.size();
						System.out.println("Number of polygons: "
								+ line_count);
						Iterator<String> poly_it = coordinates.iterator();
						count = 0;
						writer.print("var myLines = [{ ");
						while (poly_it.hasNext()) {
							Object polygon_object = poly_it.next();
							ArrayList<String> latlng = (ArrayList<String>) polygon_object;
							Iterator<String> po = latlng.iterator();
							writer.print(" 'type': 'LineString', 'coordinates': ");
							while (po.hasNext()) {
								List<LngLat> polygon = new ArrayList<LngLat>();
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
									LngLat lnglat = new LngLat(dlon,dlat);
									polygon.add(lnglat);
								}
								writer.println(polygon);
								if (count < line_count - 1) { 
									writer.print("}, {");
								}
								else {
									writer.print("}];");
								}
							}
							count++;
						}
					}
				}
				feature_count++;
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
