package de.dhbw.database;

public class Coordinates {
	 //private variables
    int _id;
    Float _longitude;
    Float _latitude;
    
    //Constructor 
    
    public Coordinates(int id, Float longitude, Float latitude){
    	this._id = id;
    	this._longitude = longitude;
    	this._latitude = latitude;
    }

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public Float get_longitude() {
		return _longitude;
	}

	public void set_longitude(Float _longitude) {
		this._longitude = _longitude;
	}

	public Float get_latitude() {
		return _latitude;
	}

	public void set_latitude(Float _latitude) {
		this._latitude = _latitude;
	}
    
    
    
    
}
