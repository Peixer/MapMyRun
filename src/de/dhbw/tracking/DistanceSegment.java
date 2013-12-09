package de.dhbw.tracking;

public class DistanceSegment {

	//Members
	
	private String distance;
	private String duration;
	private String speed;
	
	//Constructors
	
	public DistanceSegment() {
	}
	
	public DistanceSegment(String distance, String duration, String speed) {
		setDistance(distance);
		setDuration(duration);
		setSpeed(speed);
	}
	
	//Methods

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}
	
	public String toString() {
		return "Distance: " + distance + ", Duration: " + duration + ", Speed: " + speed;
	}
	
	public String[] toStringArray()
	{
		String[] list = {distance, duration, speed};
		return list;
	}
	
	public void fromStringArray(String[] stringArray)
	{
		setDistance(stringArray[0]);
		setDuration(stringArray[1]);
		setSpeed(stringArray[2]);
	}
}
