package de.dhbw.contents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.android.gms.maps.MapView;


import de.dhbw.container.R;

public class LiveTrackingFragment extends SherlockFragment{
	MapView m;

	public LiveTrackingFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// inflat and return the layout
		View v = inflater.inflate(R.layout.live_tracking_fragment, container,
				false);

		m = (MapView) v.findViewById(R.id.mapView);
		m.onCreate(savedInstanceState);
		
		
		return v;

	}
	
	@Override
    public void onResume() {
        super.onResume();
        m.onResume();
    }
		     
    @Override
    public void onPause() {
        super.onPause();
        m.onPause();
    }
     
    @Override
    public void onDestroy() {
        super.onDestroy();
        m.onDestroy();
    }
     
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        m.onLowMemory();
    }
}