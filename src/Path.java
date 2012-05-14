import processing.core.PApplet;

@SuppressWarnings("serial")
public class Path extends PApplet{
	GoogleElevation elevation;
	GoogleGeoCode geocode;

	float[] elevations = new float[57];
	public void setup() {
		size(800, 800, P3D);
		elevation = new GoogleElevation();
		geocode = new GoogleGeoCode(this);
	}

	public void draw() {
		float[] lonlat;
		println("Loading Data....");
		for (int i = 0; i < elevations.length; i++) {
			lonlat = geocode.getLonLat("Leinestraße "+i+" 12049 Berlin");
			elevations[i] = elevation.getElevation(lonlat[1], lonlat[0]);
			println(i+"/"+(elevations.length-1));
		}
		println("...done.");
		for (int i = 0; i < elevations.length; i++) {
			println("Leinestraße "+i+" "+elevations[i]);
		}
		noLoop();
	}
}
