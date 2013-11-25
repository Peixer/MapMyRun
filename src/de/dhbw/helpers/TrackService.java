package de.dhbw.helpers;

import java.text.DecimalFormat;
import java.util.List;

import de.dhbw.database.Coordinates;

public class TrackService {

	public TrackService() {

	}

	
	//calculates the distance of the tracked route
	public static double calcDistance(List<Coordinates> listContents) {
		int R = 6371;
		double distance = 0;
		int i;
		if (listContents.size()>1){
			for (i = 0; i < (listContents.size() - 1); i++) {
				
				double dLat = Math.toRadians(listContents.get(i + 1).get_latitude()
						- listContents.get(i).get_latitude());
				double dLon = Math.toRadians(listContents.get(i + 1)
						.get_longitude() - listContents.get(i).get_longitude());
				double a = Math.sin(dLat / 2)
						* Math.sin(dLat / 2)
						+ Math.cos(Math.toRadians(listContents.get(i)
								.get_latitude()))
								* Math.cos(Math.toRadians(listContents.get(i + 1)
										.get_latitude())) * Math.sin(dLon / 2)
										* Math.sin(dLon / 2);
				double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
				double d = R * c;
				distance = distance + d;
			}
		}
		return roundNumber(distance, 3);
	}
	
	
	public static double roundNumber(double distance, int stellen){
		double factor = Math.pow(10, stellen); 
		double gerundeteZahl = Math.round(distance * factor) / factor;
		return gerundeteZahl;
	}
	
	//calculates the sum of total elevations
	
	public static double calcElevation(List<Coordinates> listContents){
		double ascend = 0;
		int i;
		for (i = 0; i < (listContents.size()-1); i++) {
			if (listContents.get(i+1).get_altitude() > listContents.get(i).get_altitude()){
				ascend = ascend + (listContents.get(i+1).get_altitude() - listContents.get(i).get_altitude());
			}
		}
		return roundNumber(ascend, 3);
	}
	
	//calculates the sum of total descent
	
	public static double calcDescent(List<Coordinates> listContents){
		double descent = 0;
		int i;
		for (i = 0; i < (listContents.size()-1); i++) {
			if (listContents.get(i+1).get_altitude() < listContents.get(i).get_altitude()){
				descent = descent + (listContents.get(i).get_altitude() - listContents.get(i+1).get_altitude());
			}
		}
		return roundNumber(descent, 3);
	}
	
	//calculates duration
	
	public static String calcDuration(List<Coordinates> listContents){
		if (listContents.size() > 1){
			int numberOfSamples = listContents.size();
			long firstTimeStamp = listContents.get(0).get_timestamp();
			long lastTimeStamp = listContents.get(numberOfSamples-1).get_timestamp();
			long duration = lastTimeStamp - firstTimeStamp;
			long secondInMillis = 1000;
			long minuteInMillis = secondInMillis * 60;
			long hourInMillis = minuteInMillis * 60;
			long dayInMillis = hourInMillis * 24;
			long yearInMillis = dayInMillis * 365;
			
			long elapsedYears = duration/yearInMillis;
			duration = duration % yearInMillis;
			long elapsedDays = duration/dayInMillis;
			duration = duration % dayInMillis;
			long elapsedHours = duration/hourInMillis;
			duration = duration % hourInMillis;
			long elapsedMinutes = duration/minuteInMillis;
			duration = duration % minuteInMillis;
			long elapsedSeconds = duration/secondInMillis;
			
			DecimalFormat df = new DecimalFormat("00"); //Zweistellige Ausgabe
			
			return df.format(elapsedHours) + ":" + df.format(elapsedMinutes) + ":" + df.format(elapsedSeconds);
		}else {
			return "00:00:00";
		}
	}
	
	//calculates pace
	public static double calcPace(List<Coordinates> listContents){
		
	 double elapsedHours = 0;
		
		if (listContents.size() > 1){
			int numberOfSamples = listContents.size();
			double firstTimeStamp = listContents.get(0).get_timestamp();
			double lastTimeStamp = listContents.get(numberOfSamples-1).get_timestamp();
			double duration = lastTimeStamp - firstTimeStamp;
			double secondInMillis = 1000;
			double minuteInMillis = secondInMillis * 60;
			double hourInMillis = minuteInMillis * 60;
			
			//in hours
			elapsedHours = duration/hourInMillis;
		}
	   //in km
	   double distance = calcDistance(listContents);	
	   
	   //pace in km/h
	   return roundNumber((distance/elapsedHours), 3);
	}
	
	
	
	
	//calculates calories burned	
	public static double calcCaloriesBurned(List<Coordinates> listContents){
	 //an average weight of 75 kg is assumed
	 double caloriesBurned = calcDistance(listContents)*75;
	 return caloriesBurned;
	}
	
	
	
}
