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
    private static final int DATABASE_VERSION = 10;
 
    // Database Name
    private static final String DATABASE_NAME = "workoutsManager";
 
    // Workouts table name
    private static final String TABLE_WORKOUTS = "workouts";
    //Coordinates table name
    private static final String TABLE_COORDINATES = "coordinates";
    // Achievement table name
    private static final String TABLE_ACHIEVEMENTS = "achievements";
 
 
    private static final String KEY_ID = "id";
    	//Workouts
	    private static final String KEY_DURATION = "duration";
	    private static final String KEY_PACE = "pace";
	    private static final String KEY_ELEVATION_DOWNWARDS = "elevation_downwards";
	    private static final String KEY_ELEVATION_UPWARDS = "elevation_upwards";
	    private static final String CALORIES_BURNED = "calories_burned";
	    //Coordinates
	    private static final String KEY_LONGITUDE = "longitude";
	    private static final String KEY_LATITUDE = "latitude";
	    //Achievements
	    private static final String KEY_NAME = "name";
	    private static final String KEY_DESCRIPTION = "description";
	    private static final String KEY_IMAGENAME = "imagename";
	    private static final String KEY_REQUIREMENT_NUMBER = "requirednumber";
	    private static final String KEY_REQUIREMENT_UNIT = "unit";
	    private static final String KEY_ACHIEVED = "achieved";
    
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WORKOUTS_TABLE = "CREATE TABLE " + TABLE_WORKOUTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DURATION + " REAL,"
                + KEY_PACE + " REAL," + KEY_ELEVATION_DOWNWARDS + 
                " REAL," + KEY_ELEVATION_UPWARDS + " REAL,"
                + CALORIES_BURNED + " REAL" + ");";
        Log.d("Workout Query", CREATE_WORKOUTS_TABLE);
        db.execSQL(CREATE_WORKOUTS_TABLE);
        
        String CREATE_ACHIEVEMENT_TABLE = "CREATE TABLE " + TABLE_ACHIEVEMENTS + "(";
    	String[][] spalten = {{KEY_ID,"INTEGER PRIMARY KEY"},{KEY_NAME,"TEXT"},{KEY_DESCRIPTION,"TEXT"},{KEY_IMAGENAME,"TEXT"},
    						{KEY_REQUIREMENT_NUMBER,"INTEGER"},{KEY_REQUIREMENT_UNIT,"TEXT"},{KEY_ACHIEVED,"INTEGER"}};
    	for (String[] spalte : spalten)
    		CREATE_ACHIEVEMENT_TABLE += spalte[0] + " " + spalte[1] + ", ";	
    	CREATE_ACHIEVEMENT_TABLE = CREATE_ACHIEVEMENT_TABLE.substring(0, CREATE_ACHIEVEMENT_TABLE.length()-2) + ");";
    	Log.d("Achievements Query", CREATE_ACHIEVEMENT_TABLE);
        db.execSQL(CREATE_ACHIEVEMENT_TABLE);
        
        addAchievements(db);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUTS);
        db.execSQL("DROP TABLE " + TABLE_ACHIEVEMENTS);
        
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
    public void addAchievement(SQLiteDatabase db, Achievement achievement) {
    	ContentValues values = new ContentValues();
	    values.put(KEY_NAME, achievement.getName());
	    values.put(KEY_DESCRIPTION, achievement.getDescription());
	    values.put(KEY_IMAGENAME, achievement.getImageName());
	    values.put(KEY_REQUIREMENT_NUMBER, achievement.getRequiredNumber());
	    values.put(KEY_REQUIREMENT_UNIT, achievement.getRequiredUnit());
	    values.put(KEY_ACHIEVED, achievement.isAchieved());
	    // Inserting Row
	    db.insert(TABLE_ACHIEVEMENTS, null, values);
    }
    
    public void addAchievement(Achievement achievement) {
    	SQLiteDatabase db = getWritableDatabase();	
    	ContentValues values = new ContentValues();
	    values.put(KEY_NAME, achievement.getName());
	    values.put(KEY_DESCRIPTION, achievement.getDescription());
	    values.put(KEY_IMAGENAME, achievement.getImageName());
	    values.put(KEY_REQUIREMENT_NUMBER, achievement.getRequiredNumber());
	    values.put(KEY_REQUIREMENT_UNIT, achievement.getRequiredUnit());  
	    values.put(KEY_ACHIEVED, achievement.isAchieved());
	    // Inserting Row
	    db.insert(TABLE_ACHIEVEMENTS, null, values);
	    db.close(); // Closing database connection
    }
    
    // Get number of achievements
    public int getAchievementCount() {
        String countQuery = "SELECT * FROM " + TABLE_ACHIEVEMENTS;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        
        return count;
	}
    
 // Get number of achievements by unit
    public int getAchievementCount(String unit) {
        String countQuery = "SELECT * FROM " + TABLE_ACHIEVEMENTS + " WHERE " + KEY_REQUIREMENT_UNIT + "='" + unit + "'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
	 
        return count;
	}
    
    // Get achievement from id
    public Achievement getAchievement(int id) {
    	SQLiteDatabase db = getReadableDatabase();
    	  
	    Cursor cursor = db.query(TABLE_ACHIEVEMENTS, null, KEY_ID + "= ?", new String[] {String.valueOf(id)}, null, null, null, null);

	    if (cursor != null)
	        cursor.moveToFirst();
	    
	    Log.d("Database", "ID: " + id + ", CursorCount: " + cursor.getCount());
	 
	    Achievement achievement = new Achievement(
	    		Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID))),
	            cursor.getString(cursor.getColumnIndex(KEY_NAME)), 
	            cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)), 
	            cursor.getString(cursor.getColumnIndex(KEY_IMAGENAME)), 
	            cursor.getString(cursor.getColumnIndex(KEY_REQUIREMENT_UNIT)),
	            cursor.getInt(cursor.getColumnIndex(KEY_REQUIREMENT_NUMBER)));
	    
	    cursor.close();
	    db.close();
	    
	    return achievement; // return achievement
	}
    
    // Getting All Workouts
	public List <Achievement> getAllAchievements() {
	    List <Achievement> achievementList = new ArrayList<Achievement>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + TABLE_ACHIEVEMENTS;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
		            Achievement achievement = new Achievement();
		            achievement.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
		            achievement.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
		            achievement.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
		            achievement.setImageName(cursor.getString(cursor.getColumnIndex(KEY_IMAGENAME)));
		            achievement.setRequiredUnit(cursor.getString(cursor.getColumnIndex(KEY_REQUIREMENT_UNIT)));
		            achievement.setRequiredNumber(cursor.getInt(cursor.getColumnIndex(KEY_REQUIREMENT_NUMBER)));
		            // Adding achievement to list
		            achievementList.add(achievement);
	        } while (cursor.moveToNext());
	    }
	    
	    cursor.close();
	    db.close();
	 
	    // return achievement list
	    return achievementList;
	}
	
	// Getting All Workouts
		public List <Achievement> getAchievementsByUnit(String unit) {
		    List <Achievement> achievementList = new ArrayList<Achievement>();
		    // Select All Query
		    String selectQuery = "SELECT  * FROM " + TABLE_ACHIEVEMENTS + " WHERE " + KEY_REQUIREMENT_UNIT + "='"+unit+"'";
		 
		    SQLiteDatabase db = this.getWritableDatabase();
		    Cursor cursor = db.rawQuery(selectQuery, null);
		 
		    // looping through all rows and adding to list
		    if (cursor.moveToFirst()) {
		        do {
			            Achievement achievement = new Achievement();
			            achievement.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
			            achievement.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
			            achievement.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
			            achievement.setImageName(cursor.getString(cursor.getColumnIndex(KEY_IMAGENAME)));
			            achievement.setRequiredUnit(cursor.getString(cursor.getColumnIndex(KEY_REQUIREMENT_UNIT)));
			            achievement.setRequiredNumber(cursor.getInt(cursor.getColumnIndex(KEY_REQUIREMENT_NUMBER)));
			            // Adding achievement to list
			            achievementList.add(achievement);
		        } while (cursor.moveToNext());
		    }
		    
		    cursor.close();
		    db.close();
		 
		    // return achievement list
		    return achievementList;
		}
    
    private void addAchievements(SQLiteDatabase db)
    {
    	// total distance achievements
        addAchievement(db, new Achievement("Beginner", "Laufe insgesamt 5 Kilometer", "ic_questionmark", "tkm", 5));
        addAchievement(db, new Achievement("Aubameyang", "Laufe insgesamt 10 Kilometer", "ic_music_note", "tkm", 10));
        addAchievement(db, new Achievement("Marathon", "Laufe insgesamt 42 Kilometer", "ic_app", "tkm", 42));
        addAchievement(db, new Achievement("Runner", "Laufe insgesamt 50 Kilometer", "ic_trophy", "tkm", 50));
        addAchievement(db, new Achievement("Always running", "Laufe insgesamt 75 Kilometer", "ic_launcher", "tkm", 75));
        
        // total time achievements
        addAchievement(db, new Achievement("And here...we...go!", "Laufe insgesamt 5 Minuten", "ic_questionmark", "ts", 300));
        addAchievement(db, new Achievement("So far so good!", "Laufe insgesamt 30 Minuten", "ic_music_note", "ts", 1800));
        addAchievement(db, new Achievement("The first hour!", "Laufe insgesamt eine Stunde", "ic_app", "ts", 3600));
        addAchievement(db, new Achievement("Time goes by...", "Laufe insgesamt 5 Stunden", "ic_trophy", "ts", 18000));
        addAchievement(db, new Achievement("Indestructible!", "Laufe insgesamt 10 Stunden", "ic_launcher", "ts", 36000));
        
        // single distance achievements
        addAchievement(db, new Achievement("So it begins...", "Laufe einen Kilometer am St�ck", "ic_questionmark", "skm", 1));
        addAchievement(db, new Achievement("Double the distance!", "Laufe 2 Kilometer am St�ck", "ic_music_note", "skm", 2));
        addAchievement(db, new Achievement("High Five!", "Laufe 5 Kilometer am St�ck", "ic_app", "skm", 5));
        addAchievement(db, new Achievement("City Run", "Laufe 10 Kilometer am St�ck", "ic_trophy", "skm", 10));
        addAchievement(db, new Achievement("Run, Forrest, run!", "Laufe 20 Kilometer am St�ck", "ic_launcher", "skm", 20));
        addAchievement(db, new Achievement("You deserve a cookie.", "Laufe 30 Kilometer am St�ck", "ic_launcher", "skm", 30));
        addAchievement(db, new Achievement("Almost there...", "Laufe 40 Kilometer am St�ck", "ic_launcher", "skm", 40));
        addAchievement(db, new Achievement("Just ran a marathon.", "Laufe 42 Kilometer am St�ck", "ic_launcher", "skm", 42));
        
        // single time achievements
        addAchievement(db, new Achievement("The first experiences", "Laufe 1 Minute am St�ck", "ic_questionmark", "ss", 60));
        addAchievement(db, new Achievement("Training", "Laufe 10 Minuten am St�ck", "ic_music_note", "ss", 600));
        addAchievement(db, new Achievement("Walker", "Laufe 20 Minuten am St�ck", "ic_app", "ss", 1200));
        addAchievement(db, new Achievement("Runner", "Laufe 30 Minuten am St�ck", "ic_trophy", "ss", 1800));
        addAchievement(db, new Achievement("You are tired now...or are you?", "Laufe 1 Stunde am St�ck", "ic_launcher", "ss", 3600));
    }
}

