import processing.core.PApplet;

@SuppressWarnings("serial")
public class Path extends PApplet{
	GoogleElevation elevation;
	
	GoogleGeocodeThread geocodeThread;

	int startNr = 10;
	int endNr = 20;
	
	float[][] lonlat;
	
	public void setup() {
		size(800, 800, P3D);
		elevation = new GoogleElevation();
		
		geocodeThread = new GoogleGeocodeThread(this, 2000, "leinestrasse", startNr, endNr, "Berlin");
		geocodeThread.start();
		
		lonlat = geocodeThread.getlonlats();
	}

	public void draw() {
		
		
		for(int i=0; i<lonlat.length; i++){
			println("Leinestraße "+(i+startNr)+" "+lonlat[i][0]);
			println("Leinestraße "+(i+startNr)+" "+lonlat[i][1]);
		}
		noLoop();
	}
}
