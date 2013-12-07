package de.dhbw.contents;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

import de.dhbw.container.R;
import de.dhbw.database.DataBaseHandler;
import de.dhbw.database.Workout;

public class EvaluationSummaryFragment extends SherlockFragment{
	
	private ListView lv;
	private WorkoutDetailAdapter adapter;
	
	//Liste zur Initialisierung der (Workoutsdetail-)Adapters
	private ArrayList <WorkoutDetail> workoutDetails = new ArrayList<WorkoutDetail>();
	private DataBaseHandler db;
	private Context mContext;
	private int numberOfWorkouts;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
 
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
 
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	mContext = getActivity();
    	db = new DataBaseHandler(mContext);
    	
    	//Workoutanzahl aus DB abrufen
    	numberOfWorkouts = db.getWorkoutsCount();
    	
    	//Letztes Workout abrufen
    	Workout workout = db.getWorkout(numberOfWorkouts);
    	
        View view = inflater.inflate(R.layout.evaluation_summary_fragment, container, false);
        
        //Detailsauswertung Dauer
        WorkoutDetail detailDuration = new WorkoutDetail("Dauer", workout.getDuration());
        //Detailsauswertung Distanz
        WorkoutDetail detailDistance = new WorkoutDetail("Distanz (km)", workout.get_distance().toString());
        //Detailsauswertung Geschwindigkeit
        WorkoutDetail detailPace = new WorkoutDetail("Geschwindigkeit (km/h)", workout.getPace().toString());
        //Detailsauswertung Hoehenmeter aufwaerts
        WorkoutDetail detailElevation = new WorkoutDetail("Hoehenmeter aufwaerts", workout.getElevationUpwards().toString());
        //Detailsauswertung Hoehenmeter aufwaerts
        WorkoutDetail detailDescent = new WorkoutDetail("Hoehenmeter abwaerts", workout.getElevationDownwards().toString());
        //Detailsauswertung verbrannte Kalorien
        WorkoutDetail detailCaloriesBurned = new WorkoutDetail("Verbrannte Kalorien", workout.getCaloriesBurned().toString());
        
        //Liste zur Initialisierung des Workoutsadapters mit Daten befuellen
        workoutDetails.add(detailDuration);
        workoutDetails.add(detailDistance);
        workoutDetails.add(detailPace);
        workoutDetails.add(detailElevation);
        workoutDetails.add(detailDescent);
        workoutDetails.add(detailCaloriesBurned);
        
        //ListView laden
        lv = (ListView) view.findViewById(R.id.evaluation);
        
        //(Detailauswertung-)Adapter initialisieren
        adapter = new WorkoutDetailAdapter(mContext, R.id.evaluation, workoutDetails);
        
        //ListView mit Daten befüllen
        lv.setAdapter(adapter);
        return view;
    }
}
