import processing.core.PApplet;

public class GoogleGeocodeThread extends Thread {

	boolean running;
	int wait;
	int counter;

	GoogleGeoCode geocode;
	float[][] lonlat;

	// location info
	String streetName;
	int startNr;
	int endNr;
	String city;

	public GoogleGeocodeThread(PApplet p, int waiting, String streetName,
			int startNr, int endNr, String city) {
		this.running = false;
		this.counter = startNr;

		geocode = new GoogleGeoCode(p);
		lonlat = new float[endNr - startNr + 1][2];

		this.wait = waiting;
		this.streetName = streetName;
		this.startNr = startNr;
		this.endNr = endNr;
		this.city = city;
	}

	public void start() {
		running = true;
		System.out
				.println("GoogleElevation Thread started (will execute every "
						+ wait + " milliseconds.)");
		super.start();
	}

	public void run() {
		while (running & counter <= endNr) {
			lonlat[counter - startNr] = geocode.getLonLat(streetName + "+"
					+ counter + "+" + city);
			PApplet.println(streetName + "+" + this.counter + "+" + city);
			this.counter++;
			try {
				sleep(wait);
			} catch (Exception e) {
				PApplet.println(e);
			}
		}
	}

	public void quit() {
		PApplet.println("Thread quit.");
		running = false;
		interrupt();
	}

	public float[][] getlonlats() {
		return lonlat;
	}

}
