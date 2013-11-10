package de.dhbw.helpers;

import java.util.List;

import de.dhbw.database.Coordinates;

public class TrackService {

	public TrackService() {

	}

	
	//calculates the distance of the tracked route
	public double calcDistance(List<Coordinates> listContents) {
		int R = 6371;
		double distance = 0;
		int i;
		for (i = 0; i < listContents.size(); i++) {
			
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
		return distance;
	}
	
	
	//calculates the sum of total elevations
	
	public double calcElevation(List<Coordinates> listContents){
		double ascend = 0;
		int i;
		for (i = 0; i < listContents.size(); i++) {
			if (listContents.get(i+1).get_altitude() > listContents.get(i).get_altitude()){
				ascend = ascend + (listContents.get(i+1).get_altitude() - listContents.get(i).get_altitude());
			}
		}
		return ascend;
	}
	
	//calculates the sum of total descent
	
	public double calcDescent(List<Coordinates> listContents){
		double descent = 0;
		int i;
		for (i = 0; i < listContents.size(); i++) {
			if (listContents.get(i+1).get_altitude() < listContents.get(i).get_altitude()){
				descent = descent + (listContents.get(i).get_altitude() - listContents.get(i+1).get_altitude());
			}
		}
		return descent;
	}
	
	//TODO calculates duration
	//TODO calculates space
	//TODO calculates calories burned	
	
	
}
