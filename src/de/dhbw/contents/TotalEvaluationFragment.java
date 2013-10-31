package de.dhbw.contents;

import com.actionbarsherlock.app.SherlockFragment;

import de.dhbw.container.R;
import de.dhbw.container.R.layout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TotalEvaluationFragment extends SherlockFragment{
	public TotalEvaluationFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.total_evaluation_fragment, container,
				false);
		return rootView;
	}
}
