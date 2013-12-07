package de.dhbw.contents;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.dhbw.container.R;

public class WorkoutDetailAdapter extends ArrayAdapter <WorkoutDetail>{
	private Context mContext;
	
	//Liste zur Befüllung des (Workoutdetail-)Adapters
	private ArrayList <WorkoutDetail> workoutDetails;
	
	//Konstruktor
	public WorkoutDetailAdapter(Context context, int resource, ArrayList <WorkoutDetail> workoutDetails) {
		super(context, resource, workoutDetails);
		this.mContext = context;
		this.setWorkoutDetails(workoutDetails);
	}
	
	//Getter für Detailauswertungsliste
	public ArrayList <WorkoutDetail> getWorkoutDetails() {
		return workoutDetails;
	}
	
	//Setter für Detailauswertungsliste
	public void setWorkoutDetails(ArrayList <WorkoutDetail> workoutDetails) {
		this.workoutDetails = workoutDetails;
	}
	
	
	
	 @Override
     public View getView(int position, View convertView, ViewGroup parent) {
             View v = convertView;
             if (v == null) {
            	 
            	 //Einzelne Detailauswertungszeilen laden
                 LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                 v = vi.inflate(R.layout.evaluation_row, null);
             }
             WorkoutDetail wd = workoutDetails.get(position);
             if (wd != null) {
            	 
            	 	 //Kategorieüberschrift in der Detailauswertungsliste
                     TextView textKey = (TextView) v.findViewById(R.id.key);
                     
                     //Kategoriewert in der Detailauswertungsliste
                     TextView textValue = (TextView) v.findViewById(R.id.value);
                     if (textKey != null) {
                    	   //Kategorieüberschrift initialisiern
                           textKey.setText(wd.getDetailKey());                            }
                     if(textValue != null){
                    	   //KategorieWert initialsirien
                           textValue.setText(wd.getDetailValue());
                     }
             }
             return v;
     }
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

