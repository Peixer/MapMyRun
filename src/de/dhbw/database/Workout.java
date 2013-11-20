package de.dhbw.database;

public class Workout {
	 //private variables
    int _id;
    String _duration;
    Double _pace;
    Double _elevation_downwards;
    Double _elevation_upwards;
    Double _calories_burned;
    Double _distance;
    
     
    // Empty constructor
    public Workout(){
         
    }
    // constructor
    public Workout(int id, 
    		       String duration, 
    		       Double pace, 
    		       Double elevation_downwards, 
    		       Double elevation_upwards, 
    		       Double calories_burned, 
    		       Double distance){
        this._id = id;
        this._duration = duration;
        this._pace = pace;
        this._elevation_downwards = elevation_downwards;
        this._elevation_upwards = elevation_upwards;
        this._calories_burned = calories_burned;
        this._distance = distance;
    }

	public Workout(String duration, Double pace,
			Double elevation_downwards, Double elevation_upwards,
			Double calories_burned, Double distance) {
		this._duration = duration;
		this._pace = pace;
		this._elevation_downwards = elevation_downwards;
		this._elevation_upwards = elevation_upwards;
		this._calories_burned = calories_burned;
		this._distance = distance;
	}
     
    public Double get_distance() {
		return _distance;
	}
	public void set_distance(Double _distance) {
		this._distance = _distance;
	}
	// constructor
    public Workout(String duration, Double pace){
		this._duration = duration;
	    this._pace = pace;
    }
    // getting ID
    public int getID(){
        return this._id;
    }
     
    // setting id
    public void setID(int id){
        this._id = id;
    }
     
    public Double getElevationDownwards() {
		return _elevation_downwards;
	}
	public void setElevationDownwards(Double _elevation_downwards) {
		this._elevation_downwards = _elevation_downwards;
	}
	public Double getElevationUpwards() {
		return _elevation_upwards;
	}
	public void setElevationUpwards(Double _elevation_upwards) {
		this._elevation_upwards = _elevation_upwards;
	}
	public Double getCaloriesBurned() {
		return _calories_burned;
	}
	public void setCaloriesBurned(Double _calories_burned) {
		this._calories_burned = _calories_burned;
	}
	// getting duration
    public String getDuration(){
        return this._duration;
    }
     
    // setting duration
    public void setDuration(String duration){
        this._duration = duration;
    }
     
    // getting phone number
    public Double getPace(){
        return this._pace;
    }
     
    // setting phone number
    public void setPace(Double pace){
        this._pace = pace;
    }
    
    public String toString()
    {
    	String string = "";
    	string += "ID: " + getID();
    	string += ", Duration: " + getDuration();
    	string += ", Pace: " + getPace();
    	string += ", Distance: " + getPace();
        return string;
    }
}
