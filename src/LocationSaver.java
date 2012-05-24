import java.io.PrintWriter;

import processing.core.PApplet;

@SuppressWarnings("serial")
public class LocationSaver {
	PApplet p;
	
	GoogleElevation elevation;

	GoogleGeocodeThread geocodeThread;
	GoogleElevationThread elevationThread;

	String streetName = "Gneisenaustrasse";
	String cityName = "Berlin";
	int startNr = 1;
	int endNr = 120;

	float[][] lonlat;
	float[] elevations;

	PrintWriter output;

	public LocationSaver(PApplet p) {
		this.p = p;
		/*
		streetNames[0] = "Hermannstrasse";
		streetNames[1] = "Hasenheide";
		streetNames[2] = "Karl-Marx-Strasse";
		streetNames[3] = "Hermannplatz";
		streetNames[4] = "Urbanstrasse";
		streetNames[5] = "Sonnenallee";
		streetNames[6] = "Kottbusser Damm";
		*/
		geocodeThread = new GoogleGeocodeThread(p, 500, streetName, startNr,
				endNr, cityName);
		geocodeThread.start();
		lonlat = geocodeThread.getlonlats();
		
		elevationThread = new GoogleElevationThread(lonlat, 500);
		elevationThread.start();
		elevations = elevationThread.getElevations();
		
		saveLocDataToFiles();
	}
	
	void saveLocDataToFiles(){
		output = p.createWriter(streetName + "_" + cityName + ".txt");
		output.println("#street,housenr,city,long,lat,elevation");
		for (int i = 0; i < lonlat.length; i++) {
			p.println(streetName+" " + (i + startNr) + " " + lonlat[i][0] + " "
					+ lonlat[i][1]+","+elevations[i]);
			//longlat order messed up.
			
			// if an adress doesnt exist the lon&lat value is 9999.0
			if (lonlat[i][0] < 999 && lonlat[i][1] < 999) {
				output.println(streetName + ","+(i+1)+","+ cityName + "," + lonlat[i][0]
						+ "," + lonlat[i][1]+","+elevations[i]);
			}
		}
		
		output.flush(); // Write the remaining data
		output.close(); // Finish the file
	}
}
