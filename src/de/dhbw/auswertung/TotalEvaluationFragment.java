package de.dhbw.auswertung;

import java.util.ArrayList;
import java.util.List;

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

public class TotalEvaluationFragment extends SherlockFragment{
	
	
	private ListView lv;
	private WorkoutTotalEvaluationAdapter adapter;
	private List <Workout> workouts = new ArrayList<Workout>();
	private DataBaseHandler db;
	private Context mContext;
	
	public TotalEvaluationFragment() {
		// Empty constructor required for fragment subclasses
	}
	
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
    	
    	
    	workouts = db.getAllWorkouts();
    	
		View rootView = inflater.inflate(R.layout.total_evaluation_fragment, container,
				false);
		
        lv = (ListView) rootView.findViewById(R.id.total_evaluation);
        adapter = new WorkoutTotalEvaluationAdapter(mContext, R.id.total_evaluation, workouts);
        lv.setAdapter(adapter);
		return rootView;
	}
}
