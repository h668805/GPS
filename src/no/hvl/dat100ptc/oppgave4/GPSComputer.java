package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {

	private GPSPoint[] gpspoints;

	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}

	// beregn total distances (i meter)
	public double totalDistance() {
		double distance = 0;

		for (int i = 1; i < gpspoints.length; i++) {
			if (gpspoints[i] != null && gpspoints[i - 1] != null)
				distance += GPSUtils.distance(gpspoints[i - 1], gpspoints[i]);
		}

		return distance;
	}

	// beregn totale høydemeter (i meter)
	public double totalElevation() {

		double elevation = 0;

		for (int i = 1; i < gpspoints.length; i++) {
			if (gpspoints[i] != null && gpspoints[i - 1] != null)
				if (0 < gpspoints[i].getElevation() - gpspoints[i - 1].getElevation())
					elevation += gpspoints[i].getElevation() - gpspoints[i - 1].getElevation();
		}

		return elevation;
	}

	// beregn total tiden for hele turen (i sekunder)
	public int totalTime() {
		for (int i = 0; i < gpspoints.length; i++) {
			if (gpspoints[i] == null)
				return gpspoints[i - 1].getTime() - gpspoints[0].getTime();
		}

		return gpspoints[gpspoints.length - 1].getTime() - gpspoints[0].getTime();
	}

	// beregn gjennomsnitshastighets mellom hver av gps punktene

	public double[] speeds() {
		double[] speeds = new double[gpspoints.length - 1];

		for (int i = 0; i < speeds.length; i++) {
			if (gpspoints[i] != null && gpspoints[i + 1] != null)
				speeds[i] += GPSUtils.speed(gpspoints[i], gpspoints[i + 1]);
		}

		return speeds;
	}

	public double maxSpeed() {

		double[] speeds = speeds();
		double maxspeed = speeds[0];

		for (double s : speeds) {
			if (maxspeed < s)
				maxspeed = s;
		}

		return maxspeed;
	}

	public double averageSpeed() {
		double[] speeds = speeds();
		double average = 0;

		for (double s : speeds) {
			average += s;
		}

		return average / speeds.length;
	}

	/*
	 * bicycling, <10 mph, leisure, to work or for pleasure 4.0 bicycling, general
	 * 8.0 bicycling, 10-11.9 mph, leisure, slow, light effort 6.0 bicycling,
	 * 12-13.9 mph, leisure, moderate effort 8.0 bicycling, 14-15.9 mph, racing or
	 * leisure, fast, vigorous effort 10.0 bicycling, 16-19 mph, racing/not drafting
	 * or >19 mph drafting, very fast, racing general 12.0 bicycling, >20 mph,
	 * racing, not drafting 16.0
	 */

	// conversion factor m/s to miles per hour
	public static double MS = 2.236936;

	// beregn kcal gitt weight og tid der kjøres med en gitt hastighet
	public double kcal(double weight, int secs, double speed) {

		// MET: Metabolic equivalent of task angir (kcal x kg-1 x h-1)
		double met = 0;
		double speedmph = speed * MS;

		if (speedmph < 10.0)
			met = 4;
		else if (speedmph < 12.0)
			met = 6;
		else if (speedmph < 14.0)
			met = 8;
		else if (speedmph < 16.0)
			met = 10;
		else if (speedmph < 20.0)
			met = 12;
		else
			met = 16;

		return met * weight * (secs / 3600.0);
	}

	public double totalKcal(double weight) {
		double totalkcal = 0;
		double[] speeds = speeds();

		for (int i = 0; i < speeds.length; i++) {
			if (gpspoints[i] != null && gpspoints[i + 1] != null)
				totalkcal += kcal(weight, gpspoints[i + 1].getTime() - gpspoints[i].getTime(), speeds[i]);
		}

		return totalkcal;
	}

	private static double WEIGHT = 80.0;

	public void displayStatistics() {
		System.out.println("==============================================");

		System.out.println(String.format("%15-s: %s", "Total Time", GPSUtils.formatTime(totalTime())));

		System.out.println(
				String.format("%15-s: %s km", "Total Distance", GPSUtils.formatDouble(totalDistance() / 1000.0)));

		System.out.println(String.format("%15-s: %s m", "Total elevation", GPSUtils.formatDouble(totalElevation())));

		System.out.println(String.format("%15-s: %s km/h", "Max Speed", GPSUtils.formatDouble(maxSpeed())));

		System.out.println(String.format("%15-s: %s km/h", "Average Speed", GPSUtils.formatDouble(averageSpeed())));

		System.out.println(String.format("%15-s: %s kcal", "Energy", GPSUtils.formatDouble(totalKcal(WEIGHT))));

		System.out.println("==============================================");
	}
}
