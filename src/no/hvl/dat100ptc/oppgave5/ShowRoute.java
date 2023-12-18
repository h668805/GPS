package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;

	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		showRouteMap(MARGIN + MAPYSIZE);

		showStatistics();
	}

	// antall x-pixels per lengdegrad
	public double xstep() {

		double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));

		double xstep = MAPXSIZE / (Math.abs(maxlon - minlon));

		return xstep;
	}

	// antall y-pixels per breddegrad
	public double ystep() {
		double maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		double ystep = MAPXSIZE / (Math.abs(maxlat - minlat));

		return ystep;
	}

	public void showRouteMap(int ybase) {
		setColor(0, 255, 0);

		double[] longitudes = GPSUtils.getLongitudes(gpspoints);
		double[] latitudes = GPSUtils.getLatitudes(gpspoints);

		for (int i = 0; i < gpspoints.length; i++) {
//			if (gpspoints[i] != null && gpspoints[i + 1] != null) {
//				nyx = x + gpspoints[i + 1].getLongitude() - gpspoints[i].getLongitude();
//				drawLine((int) x, (int) (ybase - gpspoints[i].getLatitude()), (int) nyx,
//						(int) (ybase - gpspoints[i + 1].getLatitude()));
//				x = nyx;
//			}
			fillCircle((int)longitudes[i], (int)latitudes[i], 3);
			
//			System.out.println("" + gpspoints[i].getLongitude() + gpspoints[i].getLatitude());
		}
	}

	public void showStatistics() {

		int TEXTDISTANCE = 20;

		setColor(0,0,0);
		setFont("Courier",12);
		
		int x = MARGIN;
		int y = TEXTDISTANCE;
		
		drawString(String.format("%-17s: %s", "Total Time", GPSUtils.formatTime(gpscomputer.totalTime())), x, y);
		
		y += TEXTDISTANCE;
		
		drawString(String.format("%-15s: %s km", "Total Distance", GPSUtils.formatDouble(gpscomputer.totalDistance() / 1000.0)), x, y);

		y += TEXTDISTANCE;

		drawString(String.format("%-15s: %s m", "Total elevation", GPSUtils.formatDouble(gpscomputer.totalElevation())), x, y);
		
		y += TEXTDISTANCE;

		drawString(String.format("%-15s: %s km/h", "Max Speed", GPSUtils.formatDouble(gpscomputer.maxSpeed())), x, y);
		
		y += TEXTDISTANCE;

		drawString(String.format("%-15s: %s km/h", "Average Speed", GPSUtils.formatDouble(gpscomputer.averageSpeed())), x, y);
		
		y += TEXTDISTANCE;

		drawString(String.format("%-15s: %s kcal", "Energy", GPSUtils.formatDouble(gpscomputer.totalKcal(80))), x, y);
		
	}

}
