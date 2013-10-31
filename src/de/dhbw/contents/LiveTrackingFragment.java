package de.dhbw.contents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.SherlockFragment;

import de.dhbw.container.R;
import de.dhbw.container.R.layout;

public class LiveTrackingFragment extends SherlockFragment {
	public static final String ARG_PLANET_NUMBER = "planet_number";

	public LiveTrackingFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.live_tracking_fragment, container,
				false);
		return rootView;
	}
}