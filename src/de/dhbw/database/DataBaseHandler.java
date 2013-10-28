package de.dhbw.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHandler extends SQLiteOpenHelper{

	   public DataBaseHandler(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "workoutsManager";
 
    // Workouts table name
    private static final String TABLE_WORKOUTS = "workouts";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_PACE = "pace";
    private static final String KEY_ELEVATION_DOWNWARDS = "elevation_downwards";
    private static final String KEY_ELEVATION_UPWARDS = "elevation_upwards";
    private static final String CALORIES_BURNED = "calories_burned";
    
    
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WORKOUTS_TABLE = "CREATE TABLE " + TABLE_WORKOUTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DURATION + " REAL,"
                + KEY_PACE + " REAL" + KEY_ELEVATION_DOWNWARDS + 
                " REAL" + KEY_ELEVATION_UPWARDS + " REAL"
                + CALORIES_BURNED + " REAL" + ")";
        db.execSQL(CREATE_WORKOUTS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUTS);
 
        // Create tables again
        onCreate(db);
    }
    
 // Adding new workout
    public void addWorkout(Workout workout) {
    	
    	SQLiteDatabase db = this.getWritableDatabase();
	 
    	ContentValues values = new ContentValues();
	    values.put(KEY_DURATION, workout.getDuration()); 
	    values.put(KEY_PACE, workout.getPace()); 
	    values.put(KEY_ELEVATION_DOWNWARDS, workout.getElevationDownwards()); 
	    values.put(KEY_ELEVATION_UPWARDS, workout.getElevationUpwards()); 
	    values.put(CALORIES_BURNED, workout.getCaloriesBurned()); 
	    
	    
	    // Inserting Row
	    db.insert(TABLE_WORKOUTS, null, values);
	    db.close(); // Closing database connection
    }
     
    // Getting single workout
    public Workout getWorkout(int id) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	  
	    Cursor cursor = db.query(TABLE_WORKOUTS, new String[] { KEY_ID,
	    		KEY_DURATION,
	    		KEY_PACE,
	    		KEY_ELEVATION_DOWNWARDS,
	    		KEY_ELEVATION_UPWARDS, 
	    		CALORIES_BURNED }, KEY_ID + "=?",
	            new String[] { String.valueOf(id) }, null, null, null, null);
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	    Workout workout = new Workout(
	    		Integer.parseInt(cursor.getString(0)),
	            Float.parseFloat(cursor.getString(1)), 
	            Float.parseFloat(cursor.getString(2)), 
	            Float.parseFloat(cursor.getString(3)), 
	            Float.parseFloat(cursor.getString(4)),
	            Float.parseFloat(cursor.getString(5)));
	    // return workout
	    return workout;
		}
     
    // Getting All Workouts
    	 public List <Workout> getAllWorkouts() {
    	    List <Workout> workoutList = new ArrayList<Workout>();
    	    // Select All Query
    	    String selectQuery = "SELECT  * FROM " + TABLE_WORKOUTS;
    	 
    	    SQLiteDatabase db = this.getWritableDatabase();
    	    Cursor cursor = db.rawQuery(selectQuery, null);
    	 
    	    // looping through all rows and adding to list
    	    if (cursor.moveToFirst()) {
    	        do {
    	            Workout workout = new Workout();
    	            workout.setID(Integer.parseInt(cursor.getString(0)));
    	            workout.setDuration(Float.parseFloat(cursor.getString(1)));
    	            workout.setPace(Float.parseFloat(cursor.getString(2)));
    	            workout.setElevationDownwards(Float.parseFloat(cursor.getString(3)));
    	            workout.setElevationUpwards(Float.parseFloat(cursor.getString(4)));
    	            workout.setCaloriesBurned(Float.parseFloat(cursor.getString(5)));
    	            // Adding workout to list
    	            workoutList.add(workout);
    	        } while (cursor.moveToNext());
    	    }
    	 
    	    // return workout list
    	    return workoutList;
		}
     
    // Getting workouts Count
    public int getWorkoutsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_WORKOUTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
	 
        return cursor.getCount();
		}
    
    
    // Updating single workout
    public int updateWorkout(Workout workout) {
    	SQLiteDatabase db = this.getWritableDatabase();
	  
	    ContentValues values = new ContentValues();
	    values.put(KEY_DURATION, workout.getDuration());
	    values.put(KEY_PACE, workout.getPace());
	    values.put(KEY_ELEVATION_DOWNWARDS, workout.getElevationDownwards());
	    values.put(KEY_ELEVATION_UPWARDS, workout.getElevationUpwards());
	    values.put(CALORIES_BURNED, workout.getCaloriesBurned());
	    
	    // updating row
	    return db.update(TABLE_WORKOUTS, values, KEY_ID + " = ?",
	            new String[] { String.valueOf(workout.getID()) });
		}
     
    // Deleting single contact
    public void deleteWorkout(Workout workout) {
    	SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_WORKOUTS, KEY_ID + " = ?",
	            new String[] { String.valueOf(workout.getID()) });
	    db.close();
    }
}

