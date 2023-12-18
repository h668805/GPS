package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;

import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

	public static double findMax(double[] da) {
		double max = da[0];

		for (double d : da) {
			if (max < d) {
				max = d;
			}
		}

		return max;
	}

	public static double findMin(double[] da) {
		double min = da[0];;

		for (double d : da) {
			if (d < min) {
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
		double latitude1 = toRadians(gpspoint1.getLatitude());
		double latitude2 = toRadians(gpspoint2.getLatitude());
		
		double latitude = latitude2-latitude1;
		
		double longitude1 = toRadians(gpspoint1.getLongitude());
		double longitude2 = toRadians(gpspoint2.getLongitude());
		double longitude = longitude2-longitude1;
		
		double a = pow(sin(latitude/2), 2) + cos(latitude1)*cos(latitude2)*pow(sin(longitude/2), 2);
		
		double c = 2 * atan2(sqrt(a), sqrt(1-a));
		
		return R * c;
	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {
		int secs = gpspoint2.getTime()-gpspoint1.getTime();
		
		double speed = distance(gpspoint1, gpspoint2) / secs;

		return speed;
	}

	public static String formatTime(int secs) {
		String TIMESEP = ":";

		return String.format("%10s", String.format("%2d%s%2d%s%2d", secs/3600, TIMESEP, (secs%3600)/60, TIMESEP, (secs%3600)%60));
	}

	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {
		return String.format("%" + TEXTWIDTH + ".2f", d);
	}
}