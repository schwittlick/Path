import it.units.GoogleCommon.GeocodeException;
import it.units.GoogleCommon.Location;
import it.units.GoogleElevation.ElevationRequestor;
import it.units.GoogleElevation.ElevationResponse;
import it.units.examples.Main;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GoogleElevation {
	float lat;
	float lon;

	Location location;
	ElevationRequestor requestor;
	ElevationResponse elevationResponse;

	public GoogleElevation() {
		location = new Location();
		requestor = new ElevationRequestor();
	}

	public float getElevation(float lat, float lon) {
		float elevation = 0.0f;
		location.setLat(lat);
		location.setLng(lon);
		try {
			elevationResponse = requestor.getElevation(location);
			String[] split = elevationResponse.toString().split("[,]");
			elevation = Float.parseFloat(split[2].replaceAll(";", ""));
		} catch (GeocodeException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE,
					"Error fetching Elevation", ex);
		}
		return elevation;
	}

}
