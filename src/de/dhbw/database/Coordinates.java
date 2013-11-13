
package de.dhbw.database;

public class Coordinates {
	 //private variables
    int _id;
    Double _longitude;
    Double _latitude;
    Double _altitude;
    long _timestamp;
    
    //Constructors
    
    public Coordinates(){
    	
    }
    
    public Coordinates(int id, Double longitude, Double latitude, Double altitude, long timestamp){
    	this._id = id;
    	this._longitude = longitude;
    	this._latitude = latitude;
    	this._altitude = altitude;
    	this._timestamp = timestamp;
    }

    public Coordinates(Double longitude, Double latitude, Double altitude, long timestamp){
    	this._longitude = longitude;
    	this._latitude = latitude;
    	this._altitude = altitude;
    	this._timestamp = timestamp;
    }

	public long get_timestamp() {
		return _timestamp;
	}

	public void set_timestamp(long _timestamp) {
		this._timestamp = _timestamp;
	}

	public Double get_altitude() {
		return _altitude;
	}

	public void set_altitude(Double _altitude) {
		this._altitude = _altitude;
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