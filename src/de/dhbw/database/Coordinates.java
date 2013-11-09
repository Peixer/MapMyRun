package de.dhbw.database;

public class Coordinates {
	 //private variables
    int _id;
    Double _longitude;
    Double _latitude;
    
    //Constructors
    
    public Coordinates(){
    	
    }
    
    public Coordinates(int id, Double longitude, Double latitude){
    	this._id = id;
    	this._longitude = longitude;
    	this._latitude = latitude;
    }

    public Coordinates(Double longitude, Double latitude){
    	this._longitude = longitude;
    	this._latitude = latitude;
    }

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public Double get_longitude() {
		return _longitude;
	}

	public void set_longitude(Double _longitude) {
		this._longitude = _longitude;
	}

	public Double get_latitude() {
		return _latitude;
	}

	public void set_latitude(Double _latitude) {
		this._latitude = _latitude;
	}
    
    
    
    
}
