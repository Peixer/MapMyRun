package de.dhbw.contents;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.dhbw.container.R;

public class WokoutAdapter extends ArrayAdapter <WorkoutDetail>{
	private Context mContext;
	private ArrayList <WorkoutDetail> workoutDetails;
	public WokoutAdapter(Context context, int resource, ArrayList <WorkoutDetail> workoutDetails) {
		super(context, resource, workoutDetails);
		this.mContext = context;
		this.setWorkoutDetails(workoutDetails);
	}
	public ArrayList <WorkoutDetail> getWorkoutDetails() {
		return workoutDetails;
	}
	public void setWorkoutDetails(ArrayList <WorkoutDetail> workoutDetails) {
		this.workoutDetails = workoutDetails;
	}
	
	
	
	 @Override
     public View getView(int position, View convertView, ViewGroup parent) {
             View v = convertView;
             if (v == null) {
                 LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                 v = vi.inflate(R.layout.evaluation_row, null);
             }
             WorkoutDetail wd = workoutDetails.get(position);
             if (wd != null) {
                     TextView textKey = (TextView) v.findViewById(R.id.key);
                     TextView textValue = (TextView) v.findViewById(R.id.value);
                     if (textKey != null) {
                           textKey.setText(wd.getDetailKey());                            }
                     if(textValue != null){
                           textValue.setText(wd.getDetailValue());
                     }
             }
             return v;
     }
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

