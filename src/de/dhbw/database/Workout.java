package de.dhbw.database;

public class Workout {
	 //private variables
    int _id;
    Float _duration;
    Float _pace;
    Float _elevation_downwards;
    Float _elevation_upwards;
    Float _calories_burned;
    
     
    // Empty constructor
    public Workout(){
         
    }
    // constructor
    public Workout(int id, 
    		       Float duration, 
    		       Float pace, 
    		       Float elevation_downwards, 
    		       Float elevation_upwards, 
    		       Float calories_burned){
        this._id = id;
        this._duration = duration;
        this._pace = pace;
        this._elevation_downwards = elevation_downwards;
        this._elevation_upwards = elevation_upwards;
        this._calories_burned = calories_burned;
    }
     
    // constructor
    public Workout(Float duration, Float pace){
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
     
    public Float getElevationDownwards() {
		return _elevation_downwards;
	}
	public void setElevationDownwards(Float _elevation_downwards) {
		this._elevation_downwards = _elevation_downwards;
	}
	public Float getElevationUpwards() {
		return _elevation_upwards;
	}
	public void setElevationUpwards(Float _elevation_upwards) {
		this._elevation_upwards = _elevation_upwards;
	}
	public Float getCaloriesBurned() {
		return _calories_burned;
	}
	public void setCaloriesBurned(Float _calories_burned) {
		this._calories_burned = _calories_burned;
	}
	// getting duration
    public Float getDuration(){
        return this._duration;
    }
     
    // setting duration
    public void setDuration(Float duration){
        this._duration = duration;
    }
     
    // getting phone number
    public Float getPace(){
        return this._pace;
    }
     
    // setting phone number
    public void setPace(Float pace){
        this._pace = pace;
    }
}
