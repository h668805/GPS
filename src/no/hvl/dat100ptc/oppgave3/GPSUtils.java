package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max;

		max = da[0];

		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}

		return max;
	}

	public static double findMin(double[] da) {

		double min;

		min = da[0];

		for (double d : da) {
			if (min < d) {
				min = d;
			}
		}

		return min;
	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {
		double[] lat = new double[gpspoints.length];
		
		for (int i = 0; i < gpspoints.length; i++) {
			if (gpspoints[i] != null) {
				lat[i] = gpspoints[i].getLatitude();
			}
		}
		
		return lat;
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {
		double[] longitudes = new double[gpspoints.length];
		
		for (int i = 0; i < gpspoints.length; i++) {
			if (gpspoints[i] != null) {
				longitudes[i] = gpspoints[i].getLongitude();
			}
		}
		
		return longitudes;
	}

	private static int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double a, c, d;
		double latitude1, longitude1, latitude2, longitude2;
		
		latitude1 = Math.toRadians(gpspoint1.getLatitude());
		latitude2 = Math.toRadians(gpspoint2.getLatitude());
		double lat = latitude2-latitude1;
		longitude1 = Math.toRadians(gpspoint1.getLongitude());
		longitude2 = Math.toRadians(gpspoint2.getLongitude());
		double lon = longitude2-longitude1;
		
		a = Math.pow(Math.sin(lat/2), 2) + Math.cos(latitude1)*Math.cos(latitude2)*Math.pow(sin(lon/2), 2);
		
		c = 2 * Math.atan2(sqrt(a), sqrt(1-a));
		
		d = R * c;
		
		return d;
	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {
		int secs = gpspoint2.getTime()-gpspoint1.getTime();
		
		double speed = distance(gpspoint1, gpspoint2) / secs;

		return speed;
	}

	public static String formatTime(int secs) {
		String TIMESEP = ":";

		return String.format("%10s", String.format("%12d%s%d%s%d", secs/3600, TIMESEP, (secs%3600)/60, TIMESEP, (secs%3600)%60));
	}

	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {
		return String.format("%10s", "" + Math.round(d*100)/100.0);
	}
}
