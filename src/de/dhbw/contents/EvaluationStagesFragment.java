package de.dhbw.contents;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

import de.dhbw.container.R;
import de.dhbw.tracking.DistanceSegment;

@SuppressLint("ValidFragment")
public class EvaluationStagesFragment extends SherlockFragment{
    
	private List<DistanceSegment> mDistanceSegmentList;
	
	public EvaluationStagesFragment(List<DistanceSegment> distanceSegmentList) {
		
		mDistanceSegmentList = distanceSegmentList;
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
        View view = inflater.inflate(R.layout.evaluation_stages_fragment, container, false);
        
        List<String> listElements = new ArrayList<String>();
        listElements.add("Distance!Duration!Speed");
        for (DistanceSegment ds : mDistanceSegmentList)
        	listElements.add(ds.getDistance() + "!" + ds.getDuration() + "!" + ds.getSpeed());
        
        ListView listView = (ListView) view.findViewById(R.id.stages_list);
        listView.setAdapter(new CustomListAdapter(getActivity(), R.layout.evaluation_stages_fragment, listElements));
        
        return view;
    }
    
    private class CustomListAdapter extends ArrayAdapter<String> {

    	List<String> objects = new ArrayList<String>();
    	
		public CustomListAdapter(Context context, int resource,
				List<String> objects) {
			super(context, resource, objects);
		    this.objects = objects;
		}
		
    	@Override
    	public View getView(int position, View convertView, ViewGroup parent) {
    		
    		LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    		View view = inflater.inflate(R.layout.stages_list_row, null);
    		
    		String[] element = objects.get(position).split("!");
    		
    		((TextView) view.findViewById(R.id.stages_distance)).setText(element[0]);
    		((TextView) view.findViewById(R.id.stages_duration)).setText(element[1]);
    		((TextView) view.findViewById(R.id.stages_speed)).setText(element[2]);
    		
    		view.setBackgroundResource(R.drawable.background_border);
    		
    		return view;
    		//return super.getView(position, convertView, parent);
    	}   	
    }
    
}
