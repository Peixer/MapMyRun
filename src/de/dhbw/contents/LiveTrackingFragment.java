package de.dhbw.contents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import de.dhbw.container.R;
import de.dhbw.tracking.GPSTracker;

public class LiveTrackingFragment extends SherlockFragment {
        private GPSTracker gps;

        public LiveTrackingFragment() {
                // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                        Bundle savedInstanceState) {
                // inflat and return the layout
                View v = inflater.inflate(R.layout.live_tracking_fragment, container,
                                false);

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
                        
         // check if GPS enabled
         if(gps.canGetLocation()){
        
         double latitude = gps.getLatitude();
         double longitude = gps.getLongitude();
        
         // \n is for new line
         Toast.makeText(getActivity(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
         }else{
         // can't get location
         // GPS or Network is not enabled
         // Ask user to enable GPS/network in settings
         gps.showSettingsAlert();
         }
                        view.setTag(1);
                        ((TextView) view).setText("Live-Tracking anhalten");
                } else if ((Integer) view.getTag() == 1) {
                        view.setTag(2);
                        ((TextView) view).setText("Live-Tracking auswerten");
                } else if ((Integer) view.getTag() == 2){
                        getToTrackingEvaluation();
                }
        
        }
        
        public void getToTracking(SherlockFragment single_evaluation){
                getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.currentFragment, single_evaluation).commit();
        }
        
        public void getToTrackingEvaluation(){
                getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.currentFragment,
                                EvaluationViewPager.newInstance(),
                                EvaluationViewPager.TAG).addToBackStack(null).commit();
        }
        
        public void getToTotalEvaluation(SherlockFragment total_evaluation) {
                if (getActivity().getSupportFragmentManager().getBackStackEntryCount() == 0) {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.currentFragment, total_evaluation).addToBackStack(null).commit();
                } else {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.currentFragment, total_evaluation).commit();
                }
        }
}