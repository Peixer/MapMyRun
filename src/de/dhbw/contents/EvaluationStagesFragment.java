package de.dhbw.contents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

import de.dhbw.container.R;

public class EvaluationStagesFragment extends SherlockFragment{
    
	
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
        TextView textView = (TextView) view.findViewById(R.id.detailsText1);
        textView.setText("Testing");
        return view;
    }
}
