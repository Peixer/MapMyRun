package de.dhbw.helpers;

import java.text.DecimalFormat;
import java.util.List;

import de.dhbw.database.Coordinates;

public class TrackService {

	//leerer Konstruktor
	public TrackService() {

	}

	//Distanz zwischen zwei Punkten berechnen
	public static double calcTwoPointsDistance(Coordinates c1, Coordinates c2) {
		int R = 6371;
		double distance = 0;
		double dLat = Math.toRadians(c2.get_latitude() - c1.get_latitude());
		double dLon = Math.toRadians(c2.get_longitude() - c1.get_longitude());
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(c1.get_latitude()))
				* Math.cos(Math.toRadians(c2.get_latitude()))
				* Math.sin(dLon / 2) * Math.sin(dLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = R * c;
		distance = distance + d;

		return roundNumber(distance, 3);
	}
	
	//Distanz aus der gesamten Listen berechnen
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
	
	//Ergebnisse abrunden mit Variablen Nachkommastellen
	public static double roundNumber(double distance, int stellen){
		double factor = Math.pow(10, stellen); 
		double gerundeteZahl = Math.round(distance * factor) / factor;
		return gerundeteZahl;
	}
	
	//Summe Hoehenmeter aufwaerts
	public static double calcElevation(List<Coordinates> listContents){
		double ascend = 0;
		int i;
		for (i = 0; i < (listContents.size()-1); i++) {
			//falls aktuelle Seehoehe groesser als Seehoehe davor
			if (listContents.get(i+1).get_altitude() > listContents.get(i).get_altitude()){
				ascend = ascend + (listContents.get(i+1).get_altitude() - listContents.get(i).get_altitude());
			}
		}
		return roundNumber(ascend, 3);
	}
	
	//Summe Hoehenmeter abwaerts
	public static double calcDescent(List<Coordinates> listContents){
		double descent = 0;
		int i;
		for (i = 0; i < (listContents.size()-1); i++) {
			//falls aktuelle Seehoehe kleiner als Seehoehe davor
			if (listContents.get(i+1).get_altitude() < listContents.get(i).get_altitude()){
				descent = descent + (listContents.get(i).get_altitude() - listContents.get(i+1).get_altitude());
			}
		}
		return roundNumber(descent, 3);
	}
	
	//Dauer berechnen 
	public static String calcDuration(List<Coordinates> listContents){
		if (listContents.size() > 1){
			int numberOfSamples = listContents.size();
			long firstTimeStamp = listContents.get(0).get_timestamp();
			long lastTimeStamp = listContents.get(numberOfSamples-1).get_timestamp();
			
			//Dauer als Differenz zwischen erstem und letzen Zeitstempel in Millisekunden
			long duration = lastTimeStamp - firstTimeStamp;
			
			//Millisekunden in Sekunden 
			long secondInMillis = 1000;
			
			//Sekunden in Minuten 
			long minuteInMillis = secondInMillis * 60;
			
			//Minuten in Stunden 
			long hourInMillis = minuteInMillis * 60;
			
			//Vergangene Stunden
			long elapsedHours = duration/hourInMillis;
			duration = duration % hourInMillis;
			long elapsedMinutes = duration/minuteInMillis;
			
			//Vergangene Minuten
			duration = duration % minuteInMillis;
			
			//Vergangene Sekunden
			long elapsedSeconds = duration/secondInMillis;
			
			DecimalFormat df = new DecimalFormat("00"); //Zweistellige Ausgabe
			
			
			//Formatierte Zeitausgabe
			return df.format(elapsedHours) + ":" + df.format(elapsedMinutes) + ":" + df.format(elapsedSeconds);
		}else {
			return "00:00:00";
		}
	}
	
	//Durchschnittsgeschwindigkeit berechnen
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
			
			//vergangene Zeit in Stunden
			elapsedHours = duration/hourInMillis;
		}
	   //zurueckgelegte Km
	   double distance = calcDistance(listContents);	
	   
	   //Durchschnittsgeschwindigkeit in km/h
	   return roundNumber((distance/elapsedHours), 3);
	}
	
	
	
	
	//Verbrannte Kalorien berechnen 	
	public static double calcCaloriesBurned(List<Coordinates> listContents){
	 //Annahme eines durchschnittlichen Gewichts von 75 kg
	 double caloriesBurned = calcDistance(listContents)*75;
	 return roundNumber(caloriesBurned, 3);
	}
	
	
	
}
