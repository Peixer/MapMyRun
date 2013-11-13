package de.dhbw.contents;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

import de.dhbw.container.R;
import de.dhbw.database.Coordinates;
import de.dhbw.database.DataBaseHandler;
import de.dhbw.tracking.GPSTracker;

public class LiveTrackingFragment extends SherlockFragment {

	private GPSTracker gps;
    int i;
    private String[] mGridItems = {"Dauer","Kilometer","Noch","Irgendein","Feature","Keine","Ahnung","Was"};
	public LiveTrackingFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// inflate and return the layout
		View v = inflater.inflate(R.layout.live_tracking_fragment, container,
				false);
		
		v.findViewById(R.id.mapview).setVisibility(View.GONE);

		Button trackingButton = (Button) v.findViewById(R.id.tracking);
		trackingButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				changeTrackingState(view);
			}
		});
		
		// Set List
		
		for (i=0; i<mGridItems.length; i++)
		{
			
			int viewId = getResources().getIdentifier("workout_element_"+i, "id", getActivity().getPackageName());
			View listElement = v.findViewById(viewId);
			((TextView) listElement.findViewById(R.id.live_tracking_element_value_text)).setText("0:00");
			((ImageView) listElement.findViewById(R.id.live_tracking_element_value_icon)).setImageResource(R.drawable.ic_launcher);
						
			((TextView) listElement.findViewById(R.id.live_tracking_element_name)).setText(mGridItems[i]);
				
			if (i >= 6)
				break;
		}

		return v;

	}

	public void changeTrackingState(View view) {
		if (view.getTag() == null) {
			gps = new GPSTracker(getActivity());
			view.setTag(1);
			((View)view.getParent()).findViewById(R.id.mapview).setVisibility(View.VISIBLE);
			((TextView) view).setText(getString(R.string.button_workout_stop));
		} else if ((Integer) view.getTag() == 1) {
			if (gps.canGetLocation()) {
				
				DataBaseHandler db = new DataBaseHandler(getActivity());
				List <Coordinates> listContents = new ArrayList<Coordinates>();
				listContents = db.getAllCoordinatePairs();
				
				for (i = 0; i < listContents.size(); i++) {
					Toast.makeText(
							getActivity(),
							"Your Location is - \nLon: "
									+ listContents.get(i).get_longitude()
									+ "\nLat: "
									+ listContents.get(i).get_latitude()
									+ "\nAlt: "
									+ gps.getElevationFromGoogleMaps(
											listContents.get(i).get_longitude(),
											listContents.get(i).get_latitude())
									+ "\nTime: "
									+ listContents.get(i).get_timestamp(),
							Toast.LENGTH_LONG).show();
				}
			}else {
				gps.showSettingsAlert();
			}
			view.setTag(2);
			((TextView) view).setText(getString(R.string.button_workout_analyse));
		} else if ((Integer) view.getTag() == 2) {
			gps.stopUsingGPS();
			getToTrackingEvaluation();
		}
	}

	public void getToTracking(SherlockFragment single_evaluation) {
		getActivity().getSupportFragmentManager().beginTransaction()
				.add(R.id.currentFragment, single_evaluation).commit();
	}

	public void getToTrackingEvaluation() {
		getActivity()
				.getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.currentFragment,
						EvaluationViewPager.newInstance(),
						EvaluationViewPager.TAG).addToBackStack(null).commit();
	}

	public void getToTotalEvaluation(SherlockFragment total_evaluation) {
		if (getActivity().getSupportFragmentManager().getBackStackEntryCount() == 0) {
			getActivity().getSupportFragmentManager().beginTransaction()
					.replace(R.id.currentFragment, total_evaluation)
					.addToBackStack(null).commit();
		} else {
			getActivity().getSupportFragmentManager().beginTransaction()
					.replace(R.id.currentFragment, total_evaluation).commit();
		}
	}

}