package de.dhbw.contents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.SherlockFragment;
<<<<<<< HEAD
import com.google.android.gms.maps.MapView;
=======
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
>>>>>>> c08deecc2aea8c282d014c0f6fa314c4c1b8622f

import de.dhbw.container.R;

public class LiveTrackingFragment extends SherlockFragment {
<<<<<<< HEAD
	MapView m;
=======
	private MapView mMapView;

>>>>>>> c08deecc2aea8c282d014c0f6fa314c4c1b8622f
	public LiveTrackingFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// inflat and return the layout
		View v = inflater.inflate(R.layout.live_tracking_fragment, container,
				false);
<<<<<<< HEAD
		m = (MapView) rootView.findViewById(R.id.mapView);
		m.onCreate(savedInstanceState);
		
		
		return rootView;
=======

		
		return v;

>>>>>>> c08deecc2aea8c282d014c0f6fa314c4c1b8622f
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