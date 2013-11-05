package de.dhbw.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHandler extends SQLiteOpenHelper{

	   public DataBaseHandler(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	   
	   public DataBaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "workoutsManager";
 
    // Workouts table name
    private static final String TABLE_WORKOUTS = "workouts";
    // Achievement table name
    private static final String TABLE_ACHIEVEMENTS = "achievements";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    	//Workouts
	    private static final String KEY_DURATION = "duration";
	    private static final String KEY_PACE = "pace";
	    private static final String KEY_ELEVATION_DOWNWARDS = "elevation_downwards";
	    private static final String KEY_ELEVATION_UPWARDS = "elevation_upwards";
	    private static final String CALORIES_BURNED = "calories_burned";
	    //Achievements
	    private static final String KEY_NAME = "name";
	    private static final String KEY_DESCRIPTION = "description";
	    private static final String KEY_IMAGENAME = "imagename";
	    private static final String KEY_REQUIREMENT_NUMBER = "requirednumber";
	    private static final String KEY_REQUIREMENT_UNIT = "unit";
    
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WORKOUTS_TABLE = "CREATE TABLE " + TABLE_WORKOUTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DURATION + " REAL,"
                + KEY_PACE + " REAL" + KEY_ELEVATION_DOWNWARDS + 
                " REAL" + KEY_ELEVATION_UPWARDS + " REAL"
                + CALORIES_BURNED + " REAL" + ")";
        Log.d("Workout Query", CREATE_WORKOUTS_TABLE);
        db.execSQL(CREATE_WORKOUTS_TABLE);
        
        String CREATE_ACHIEVEMENT_TABLE = "CREATE TABLE " + TABLE_ACHIEVEMENTS + "(";
    	String[][] spalten = {{KEY_ID,"INTEGER PRIMARY KEY"},{KEY_NAME,"TEXT"},{KEY_DESCRIPTION,"TEXT"},{KEY_IMAGENAME,"TEXT"},
    						{KEY_REQUIREMENT_NUMBER,"INTEGER"},{KEY_REQUIREMENT_UNIT,"REAL"}};
    	for (String[] spalte : spalten)
    		CREATE_ACHIEVEMENT_TABLE += spalte[0] + " " + spalte[1] + ", ";	
    	CREATE_ACHIEVEMENT_TABLE = CREATE_ACHIEVEMENT_TABLE.substring(0, CREATE_ACHIEVEMENT_TABLE.length()-2) + ");";
    	Log.d("Achievements Query", CREATE_ACHIEVEMENT_TABLE);
        db.execSQL(CREATE_ACHIEVEMENT_TABLE);
        
        addAchievement(new Achievement(0, "Name1", "Beschreibung1", "ic_questionmark", "km", 5));
        addAchievement(new Achievement(1, "Name2", "Beschreibung2", "ic_questionmark", "km", 10));
        addAchievement(new Achievement(2, "Name3", "Beschreibung3", "ic_questionmark", "km", 20));
        addAchievement(new Achievement(3, "Name4", "Beschreibung4", "ic_questionmark", "km", 50));
        addAchievement(new Achievement(4, "Name5", "Beschreibung5", "ic_questionmark", "km", 75));
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACHIEVEMENTS);
        
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
    
    //TODO: Achievement-Funktionen
    
    /*
        KEY_NAME
	    KEY_DESCRIPTION
	    KEY_IMAGENAME
	    KEY_REQUIREMENT_NUMBER
	    KEY_REQUIREMENT_UNIT
     */
 
    // Adding new achievement
    public void addAchievement(Achievement achievement) {
    	
    	SQLiteDatabase db = this.getWritableDatabase();
	 
    	ContentValues values = new ContentValues();
	    values.put(KEY_NAME, achievement.getName());
	    values.put(KEY_DESCRIPTION, achievement.getDescription());
	    values.put(KEY_IMAGENAME, achievement.getImageName());
	    values.put(KEY_REQUIREMENT_NUMBER, achievement.getRequiredNumber());
	    values.put(KEY_REQUIREMENT_UNIT, achievement.getRequiredUnit());	    
	    
	    // Inserting Row
	    db.insert(TABLE_ACHIEVEMENTS, null, values);
	    db.close(); // Closing database connection
    }
    
    // Get number of achievements
    public int getAchievementCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ACHIEVEMENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
	 
        return cursor.getCount();
	}
    
    // Get achievement from id
    public Achievement getAchievement(int id) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	  
	    Cursor cursor = db.query(TABLE_WORKOUTS, null, KEY_ID + "= ?", new String[] {String.valueOf(id)}, null, null, null, null);
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	    Achievement achievement = new Achievement(
	    		Integer.parseInt(cursor.getString(0)),
	            cursor.getString(1), 
	            cursor.getString(2), 
	            cursor.getString(3), 
	            cursor.getString(4),
	            Integer.parseInt(cursor.getString(5)));
	    // return achievement
	    return achievement;
	}
}

