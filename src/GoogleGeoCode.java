import processing.core.PApplet;
import processing.xml.XMLElement;

public class GoogleGeoCode {
	PApplet parent;

	String googleGeocodeLink;

	public GoogleGeoCode(PApplet p) {
		this.parent = p;
	}

	public float[] getLonLat(String adress) {
		float[] lonlat = new float[2];
		googleGeocodeLink = "http://maps.googleapis.com/maps/api/geocode/xml?address="
				+ adress.replaceAll("\\s+", "+") + "&sensor=false";
		XMLElement mainXML = getMainXML();
		lonlat = getLongitude(mainXML);
		return lonlat;
	}

	private float[] getLongitude(XMLElement mainXML)
			throws ArrayIndexOutOfBoundsException {
		float lon[] = new float[2];
		try {
			XMLElement status = mainXML.getChild(0);
			if (status.getContent().equals("OK")) {
				XMLElement addressExisting = mainXML
						.getChild("result/partial_match");
				if (addressExisting == null) {
					XMLElement tmp = mainXML
							.getChild("result/geometry/location/lng");
					lon[0] = Float.parseFloat(tmp.getContent());
					tmp = mainXML.getChild("result/geometry/location/lat");
					lon[1] = Float.parseFloat(tmp.getContent());

				} else {
					PApplet.println("adress doesnt exist");
					lon[0] = 9999.0f;
					lon[1] = 9999.0f;
				}

			} else {
				throw new ArrayIndexOutOfBoundsException(status.getContent());
			}
		} catch (ArrayIndexOutOfBoundsException a) {
			PApplet.println(a
					+ " check https://developers.google.com/maps/documentation/geocoding/ under topic 'Status Codes'");
		}
		return lon;
	}

	private float getLatitude(XMLElement mainXML)
			throws ArrayIndexOutOfBoundsException {
		float latitude = 0.0f;
		try {
			XMLElement status = mainXML.getChild(0);
			if (status.getContent().equals("OK")) {
				XMLElement tmp = mainXML
						.getChild("result/geometry/location/lat");
				latitude = Float.parseFloat(tmp.getContent());
			} else {
				throw new ArrayIndexOutOfBoundsException(status.getContent());
			}
		} catch (ArrayIndexOutOfBoundsException a) {
			PApplet.println(a
					+ " check https://developers.google.com/maps/documentation/geocoding/ under topic 'Status Codes'");
		}
		return latitude;
	}

	private XMLElement getMainXML() {
		XMLElement xml = new XMLElement();
		try {
			xml = new XMLElement(parent, googleGeocodeLink);
		} catch (NullPointerException n) {
			PApplet.println(n + " not available");
		}
		return xml;
	}
}
