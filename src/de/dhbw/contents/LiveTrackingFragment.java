package de.dhbw.contents;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

import de.dhbw.achievement.AchievementFragment.SavedTabsListAdapter;
import de.dhbw.container.R;
import de.dhbw.database.Coordinates;
import de.dhbw.database.DataBaseHandler;
import de.dhbw.tracking.GPSTracker;

public class LiveTrackingFragment extends SherlockFragment {

	private GPSTracker gps;
	final String[] items={"lorem", "ipsum", "dolor",
			"sit", "amet",
			"consectetuer", "adipiscing", "elit", "morbi", "vel",
			"ligula", "vitae", "arcu", "aliquet", "mollis",
			"etiam", "vel", "erat", "placerat", "ante",
			"porttitor", "sodales", "pellentesque", "augue", "purus"};
    int i;
	public LiveTrackingFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// inflat and return the layout
		View v = inflater.inflate(R.layout.live_tracking_fragment, container,
				false);
//		ListView lv = (ExpandableListView) v.findViewById(android.R.id.list);
//		ListAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, items);
//	    lv.setAdapter(adapter);

		Button trackingButton = (Button) v.findViewById(R.id.tracking);
		trackingButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				changeTrackingState(view);
			}
		});

		return v;

	}

	public void changeTrackingState(View view) {
		if (view.getTag() == null) {
			
			gps = new GPSTracker(getActivity());
			if (gps.canGetLocation()) {
				
				
				
				DataBaseHandler db = new DataBaseHandler(getActivity());
				List <Coordinates> listContents = new ArrayList<Coordinates>();
				listContents = db.getAllCoordinatePairs();
				
				for (i=0; i<listContents.size(); i++){
					Toast.makeText(
							getActivity(),
							"Your Location is - \nLon: " + listContents.get(i).get_longitude()
							+ "\nLat: " + listContents.get(i).get_latitude(), 
							Toast.LENGTH_LONG).show();
				}
			}else {
				gps.showSettingsAlert();
			}
			view.setTag(1);
			((TextView) view).setText("Live-Tracking anhalten");
		} else if ((Integer) view.getTag() == 1) {
			view.setTag(2);
			((TextView) view).setText("Live-Tracking auswerten");
		} else if ((Integer) view.getTag() == 2) {
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