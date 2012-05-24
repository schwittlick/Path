import processing.core.PApplet;

public class GoogleElevationThread extends Thread {

	float[][] lonlat;
	float[] elevation;

	boolean running;
	int wait;

	GoogleElevation googleElevation;

	public GoogleElevationThread(float[][] lonlat, int waiting) {
		this.lonlat = lonlat;
		elevation = new float[lonlat.length];

		this.running = false;
		this.wait = waiting;

		googleElevation = new GoogleElevation();

	}

	public void start() {
		running = true;
		System.out
				.println("GoogleElevation Thread started (will execute every "
						+ wait + " milliseconds.)");
		//super.start();
		run();
	}

	public void run() {
		PApplet.println(lonlat.length);
		for (int i = 0; i < lonlat.length;i++) {
			float lon = lonlat[i][1];
			float lat = lonlat[i][0];
			if ((lat < 1.0 && lon < 1.0) || (lat > 9998.0 && lon > 9998.0)) {
				// no elecation data
				elevation[i] = -1000;
			} else {
				elevation[i] = googleElevation.getElevation(lon, lat);
				PApplet.println("elevationthread: "+i + "/" + (lonlat.length-1));
				PApplet.println("elevation: "+elevation[i]);
				try {
					sleep(wait);
				} catch (Exception e) {
					PApplet.println(e);
				}
			}
		}

	}

	public void quit() {
		PApplet.println("GoogleGeocode thread quit.");
		running = false;
		interrupt();
	}

	public float[] getElevations() {
		return elevation;
	}
}
