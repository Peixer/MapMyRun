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
	private WokoutAdapter adapter;
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
    	
    	//get the number of all Workouts in the database
    	numberOfWorkouts = db.getWorkoutsCount();
    	
    	//get the latest Workout 
    	Workout workout = db.getWorkout(numberOfWorkouts-1);
    	
        View view = inflater.inflate(R.layout.evaluation_summary_fragment, container, false);
        WorkoutDetail detailDuration = new WorkoutDetail("Dauer", workout.getDuration());
        WorkoutDetail detailDistance = new WorkoutDetail("Distanz", workout.get_distance().toString());
        WorkoutDetail detailPace = new WorkoutDetail("Geschwindigkeit", workout.getPace().toString());
        WorkoutDetail detailElevation = new WorkoutDetail("Hoehenmeter aufwaerts", workout.getElevationUpwards().toString());
        WorkoutDetail detailDescent = new WorkoutDetail("Hoehenmeter abwaerts", workout.getElevationDownwards().toString());
        WorkoutDetail detailCaloriesBurned = new WorkoutDetail("Kalorien", workout.getCaloriesBurned().toString());
        workoutDetails.add(detailDuration);
        workoutDetails.add(detailDistance);
        workoutDetails.add(detailPace);
        workoutDetails.add(detailElevation);
        workoutDetails.add(detailDescent);
        workoutDetails.add(detailCaloriesBurned);
        
        lv = (ListView) view.findViewById(R.id.evaluation);
        adapter = new WokoutAdapter(mContext, R.id.evaluation, workoutDetails);
        lv.setAdapter(adapter);
        return view;
    }
}
