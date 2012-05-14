import processing.core.PApplet;

@SuppressWarnings("serial")
public class Path extends PApplet{
	GoogleElevation elevation;
	
	GoogleGeocodeThread geocodeThread;

	int startNr = 10;
	int endNr = 20;
	
	float[] elevations = new float[57];
	public void setup() {
		size(800, 800, P3D);
		elevation = new GoogleElevation();
		
		geocodeThread = new GoogleGeocodeThread(this, 10000, "Leinestrasse", startNr, endNr, "Berlin");
		geocodeThread.start();
		geocodeThread.run();
	}

	public void draw() {
		
		float[][] lonlat = geocodeThread.getlonlats();
		for(int i=0; i<lonlat.length; i++){
			println("Leinestraße "+(i+startNr)+" "+lonlat[i][0]);
			println("Leinestraße "+(i+startNr)+" "+lonlat[i][1]);
		}
		noLoop();
	}
}
