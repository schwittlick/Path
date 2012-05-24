import java.util.Calendar;

import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PVector;

public class PathPainter extends PApplet {

	float mapGeoLeft = 13.42f;
	float mapGeoRight = 13.43f;

	float mapGeoTop = 52.47f;
	float mapGeoBottom = 52.474f;

	float mapGeoMaxDepth = 0;
	float mapGeoMinDepth = 100;

	float mapScreenWidth, mapScreenHeight;

	Table table[] = new Table[9];

	PeasyCam cam;

	public void setup() {
		size(1000, 700, OPENGL);
		lights();
		cam = new PeasyCam(this, 500);
		background(255);
		stroke(0);
		strokeWeight(2);

		mapScreenWidth = width;
		mapScreenHeight = height;

		table[0] = new Table(this, "Hermannstrasse_Berlin.txt");
		table[1] = new Table(this, "Hasenheide_Berlin.txt");
		table[2] = new Table(this, "Karl-Marx-Strasse_Berlin.txt");
		table[3] = new Table(this, "Mehringdamm_Berlin.txt");
		table[4] = new Table(this, "Hermannplatz_Berlin.txt");
		table[5] = new Table(this, "Urbanstrasse_Berlin.txt");
		table[6] = new Table(this, "Sonnenallee_Berlin.txt");
		table[7] = new Table(this, "Kottbusser_Damm_Berlin.txt");
		table[8] = new Table(this, "Gneisenaustrasse_Berlin.txt");

		calculateDimensions();
		println(mapGeoLeft);
		println(mapGeoRight);
		println(mapGeoTop);
		println(mapGeoBottom);
	}

	void calculateDimensions() {
		for (int j = 0; j < table.length; j++) {
			for (int i = 0; i < table[j].getRowCount(); i++) {
				if (table[j].getFloat(i, 3) > 1.0) {
					if (table[j].getFloat(i, 4) > 1.0) {
						if (table[j].getFloat(i, 3) < mapGeoLeft) {
							mapGeoLeft = table[j].getFloat(i, 3);
						}
						if (table[j].getFloat(i, 3) > mapGeoRight) {
							mapGeoRight = table[j].getFloat(i, 3);
						}
						if (table[j].getFloat(i, 4) < mapGeoTop) {
							mapGeoTop = table[j].getFloat(i, 4);
						}
						if (table[j].getFloat(i, 4) > mapGeoBottom) {
							mapGeoBottom = table[j].getFloat(i, 4);
						}
						if (table[j].getFloat(i, 5) > mapGeoMaxDepth) {
							mapGeoMaxDepth = table[j].getFloat(i, 5);
						}
						if (table[j].getFloat(i, 5) < mapGeoMinDepth) {
							mapGeoMinDepth = table[j].getFloat(i, 5);
						}
					}
				}
			}
		}
	}

	public void draw() {
		fill(0, 0);
		rect(0, 0, 1, 1);
		background(255);
		drawAxis();
		noStroke();

		beginShape(TRIANGLE_STRIP);
		fill(0, 0, 255, 140);
		vertex(0, 0);
		vertex(0, -2000);
		vertex(2000, 0);
		vertex(2000, -2000);
		endShape();
		noFill();
		
		for (int j = 0; j < table.length; j++) {
			for (int i = 0; i < table[j].getRowCount(); i++) {
				PVector map = geoToPixel(new PVector(table[j].getFloat(i, 3),
						table[j].getFloat(i, 4)));
				map.z = table[j].getFloat(i, 5);
				map.y *= -1;
				
				strokeWeight(4);
				pushMatrix();
				stroke(0, 40);
				//translate(map.x, map.y, map.z);
				line(map.x, map.y, map.z, map.x, map.y, 0);
				strokeWeight(2);
				stroke(0, 255);
				point(map.x, map.y, map.z);
				popMatrix();
				// println(map.x+"  "+map.y);
				// popMatrix();
			}
		}
		// noLoop();
	}

	public PVector geoToPixel(PVector geoLocation) {
		return new PVector(mapScreenWidth * (geoLocation.x - mapGeoLeft)
				/ (mapGeoRight - mapGeoLeft), mapScreenHeight - mapScreenHeight
				* (geoLocation.y - mapGeoBottom) / (mapGeoTop - mapGeoBottom));
	}

	void drawAxis() {
		strokeWeight(1);
		stroke(255, 0, 0);
		line(0, 0, 0, 50, 0, 0);
		stroke(0, 255, 0);
		line(0, 0, 0, 0, 50, 0);
		stroke(0, 0, 255);
		line(0, 0, 0, 0, 0, 50);
	}

	public void keyPressed() {
		if (key == 's') {
			saveFrame("hermannplatz"
					+ "_"
					+ String.format("%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS",
							Calendar.getInstance()) + ".png");
		}
	}
}
