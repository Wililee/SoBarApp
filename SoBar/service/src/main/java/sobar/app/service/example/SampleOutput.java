package sobar.app.service.example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;

import sobar.app.service.Utils;
import sobar.app.service.algorithm.Graph;
import sobar.app.service.algorithm.ShortestHamiltonianPath;
import sobar.app.service.model.Business;
import sobar.app.service.model.BusinessAttributes;


public class SampleOutput {

	
	public static ArrayList<Business> getBars(){
		ArrayList<Business> businesses = new ArrayList<>();
        FileInputStream inputStream = null;
        Scanner sc = null;

        Gson gson = new Gson();
        ArrayList<String> categories = new ArrayList<>();

        try {
        	
        	// Load Categories to match by
        	inputStream = new FileInputStream("data/categories.txt");
            sc = new Scanner(inputStream, "UTF-8");
            
            while (sc.hasNextLine()) {
            	categories.add(sc.nextLine());
            }
            
            sc.close();
        	
            // Load businesses
            inputStream = new FileInputStream("data/business.json");
            sc = new Scanner(inputStream, "UTF-8");

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Business b = gson.fromJson(line, Business.class);
                BusinessAttributes attributes = gson.fromJson(line, BusinessAttributes.class);
                b.setAttributes(attributes);
                
                boolean hasAlcohol = false;
                boolean matchesCategory = false;
                
                if (b.getAttribute("Alcohol") != null) {
                	String s = b.getAttribute("Alcohol").getAsString();
                	if (!s.toLowerCase().contains("none")) {
                    	hasAlcohol = true;
                	}
                }
                
                for (String s : categories) {
                	if (b.getCategories() != null) {
                		if (b.getCategories().contains(s)) {
                			matchesCategory = true;
                			break;
                		}
                	}
                }
                
                if (hasAlcohol || matchesCategory) {
                	businesses.add(b);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (sc != null) {
                sc.close();
            }
        }


        return businesses;
		
		
	}
	
	public static void WriteToFile(ArrayList<String> contents) {
		try {
			FileWriter f = new FileWriter("output.txt");
			for(String s : contents)
				f.write(s + "\n");
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		
		
		//gets the info in the bag
		FileInputStream inputStream = null;
        Scanner sc = null;

        Gson gson = new Gson();
        ArrayList<Business> businesses = new ArrayList<>();
        ArrayList<String> categories = new ArrayList<>();

        try {
        	
        	// Load Categories to match by
        	inputStream = new FileInputStream("data/categories.txt");
            sc = new Scanner(inputStream, "UTF-8");
            
            while (sc.hasNextLine()) {
            	categories.add(sc.nextLine());
            }
            
            sc.close();
        	
            // Load businesses
            inputStream = new FileInputStream("data/business.json");
            sc = new Scanner(inputStream, "UTF-8");

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Business b = gson.fromJson(line, Business.class);
                BusinessAttributes attributes = gson.fromJson(line, BusinessAttributes.class);
                b.setAttributes(attributes);
                
                boolean hasAlcohol = false;
                boolean matchesCategory = false;
                
                if (b.getAttribute("Alcohol") != null) {
                	String s = b.getAttribute("Alcohol").getAsString();
                	if (!s.toLowerCase().contains("none")) {
                    	hasAlcohol = true;
                	}
                }
                
                for (String s : categories) {
                	if (b.getCategories() != null) {
                		if (b.getCategories().contains(s)) {
                			matchesCategory = true;
                			break;
                		}
                	}
                }
                
//                if (hasAlcohol || matchesCategory) {
                	businesses.add(b);
//                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (sc != null) {
                sc.close();
            }
        }

		
        ArrayList<String> contents = new ArrayList<>();
		
		//creates the sample bars (business)
		ArrayList<Business> bars = new ArrayList<>();
		bars.add(businesses.get(22));
		bars.add(businesses.get(77));
		bars.add(businesses.get(719));
		bars.add(businesses.get(226));
		bars.add(businesses.get(708));
		
		//creates the graph
		Graph G = new Graph();
		//adds the bars to the graph
		for(Business b : bars)
			G.addVertex(b);
		
		//finds the shortest ham path
		ShortestHamiltonianPath shp = new ShortestHamiltonianPath(G, G.getBars().get(0));
		
		
		//shortest
		ArrayList<Business> shortestPath = shp.findShortest();
		
		contents.add("Below is a sample of a shortest hamiltonian path between a few bars (going top down)");
		
		//displays the found path
		contents.add(String.format("%-25s | %-15s | %-15s | %-15s | %-15s \r", "Name" , "City" , "State/Prov" , "Latitude" , "Longitude"));
		contents.add(String.format("%-25s | %-15s | %-15s | %-15s | %-15s \r", "____" , "____" , "__________" , "________" , "_________"));
		for(Business b : shortestPath) 
			contents.add(String.format("%-25s | %-15s | %-15s | %-15s | %-15s \r", b.getName() , b.getCity() , b.getState() , b.getLatitude() , b.getLongitude()));
		
		double dist = 0;
		for(int i = 0; i < shortestPath.size() - 1; i ++)
			dist += shp.distTo(shortestPath.get(i), shortestPath.get(i+1));
		
		
		contents.add(String.format("Total Distance Traveled : %.3f km", dist/1000));

		//DISPLAYS THE DIFFERENT BARS IN LOCATION
		
		double lon = -115.1699;
		double lat = 36.144;
		double radius = 700;
		ArrayList<String> pref = new ArrayList<>();

		
		ArrayList<Business> nearby = new ArrayList<>();
		ArrayList<Business> closeBars = getBars();
		
		
		
        for (Business b : closeBars) {
            if (Utils.dist(lat, lon, b.getLatitude(), b.getLongitude()) <= radius) {
                        nearby.add(b);
            }
        }

        contents.add("\n\n");

        contents.add("Below is an example of finding the nearby bars (within 700m) at the geolocation lat : 36.144  lon : -115.1699 or Vegas");
        contents.add(String.format("%-25s | %-15s | %-15s | %-15s | %-15s \r", "Name" , "City" , "State/Prov" , "Latitude" , "Longitude"));
        contents.add(String.format("%-25s | %-15s | %-15s | %-15s | %-15s \r", "____" , "____" , "__________" , "________" , "_________"));
		
		for(Business b : nearby)
			contents.add(String.format("%-25s | %-15s | %-15s | %-15s | %-15s \r", b.getName() , b.getCity() , b.getState() , b.getLatitude() , b.getLongitude()));
		
		contents.add("\n\n");
		
		contents.add("Below is an example of finding the nearby bars at that same location but with preferences");
		pref.add("Food");
        ArrayList<Business> nearbyWithPref = new ArrayList<>();
		for (Business b : nearby) {
                for (String s : pref) {
                    if (b.getCategories().toLowerCase().contains(s.toLowerCase())) {
                    	nearbyWithPref.add(b);
                    }
                }
        }
        
		
		contents.add(String.format("%-25s | %-15s | %-15s | %-15s | %-15s \r", "Name" , "City" , "State/Prov" , "Latitude" , "Longitude"));
		contents.add(String.format("%-25s | %-15s | %-15s | %-15s | %-15s \r", "____" , "____" , "__________" , "________" , "_________"));
		for(Business b : nearbyWithPref)
			contents.add(String.format("%-25s | %-15s | %-15s | %-15s | %-15s \r", b.getName() , b.getCity() , b.getState() , b.getLatitude() , b.getLongitude()));
		
       WriteToFile(contents);
		
	}

}
