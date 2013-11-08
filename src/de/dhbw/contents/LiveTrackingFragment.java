package de.dhbw.contents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.SherlockFragment;





import de.dhbw.container.R;

public class LiveTrackingFragment extends SherlockFragment {


	public LiveTrackingFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// inflat and return the layout
		View rootView = inflater.inflate(R.layout.live_tracking_fragment, container,
				false);
		return rootView;

	}
}