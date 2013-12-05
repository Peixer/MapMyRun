package de.dhbw.contents;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragment;

import de.dhbw.container.MenuContainerActivity;
import de.dhbw.container.R;
import de.dhbw.database.AnalysisCategory;
import de.dhbw.database.CategoryPosition;
import de.dhbw.database.Coordinates;
import de.dhbw.database.DataBaseHandler;
import de.dhbw.database.Workout;
import de.dhbw.helpers.TrackService;
import de.dhbw.tracking.DistanceSegment;
import de.dhbw.tracking.GPSTracker;
import de.dhbw.tracking.MyItemizedOverlay;

public class LiveTrackingFragment extends SherlockFragment {

	private Context mContext;
	private GPSTracker gps;
    private DataBaseHandler db;
    private View mView;
    private LinearLayout mWorkoutLayout;
    private ListView mListView;
    
    public List<DistanceSegment> mSegmentList = new ArrayList<DistanceSegment>();
    
    //leerer Konstruktor
	public LiveTrackingFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Layout erstellen
		View v = inflater.inflate(R.layout.live_tracking_fragment, container,
				false);
		
		//Container Acivity
		mContext = getActivity();
		
		//Live Tracking Layout anzeigen
		mWorkoutLayout = (LinearLayout) v.findViewById(R.id.workout_layout);
		mWorkoutLayout.setVisibility(View.VISIBLE);
		
		//Karte ausblenden
		MapView mMapView = (MapView) v.findViewById(R.id.mapview);
		mMapView.setVisibility(View.GONE);
		
		//(Tracking-)Kategorieliste (Dauer,Distanz etc.) ausblenden
		mListView = (ListView) v.findViewById(R.id.category_list);
		mListView.setVisibility(View.GONE);
		
		db = new DataBaseHandler(mContext);

		//Button zum Starten, Anhalten und Auswerten von einer Live-Tracking
		Button trackingButton = (Button) v.findViewById(R.id.tracking);
		trackingButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				changeTrackingState(view);
			}
		});
		
		return v;
	}
	
	//TODO comment
	public void onBackPressed()
	{
		if (mListView.getVisibility() == View.VISIBLE)
		{
			mListView.setVisibility(View.GONE);
			mWorkoutLayout.setVisibility(View.VISIBLE);
		}
	}
	
	//TODO comment
	@Override
	public void onResume() {
		// Set List
		mView = getView();	
		setList();
		super.onResume();
	}
	
	//TODO comment
	public void formatCategoryHeadline(AnalysisCategory ac, TextView valueView ){
		String format = ac.getFormat();
		
		if (format.equals("hh:mm:ss"))
			valueView.setText("00:00:00");
		else if (format.equals("km"))
			valueView.setText("0");
		else if (format.equals("m"))
			valueView.setText("0");
		else if (format.equals("kcal"))
			valueView.setText("0");
		else if (format.equals("km/h"))
			valueView.setText("0,0");	
		else if (format.equals("hh:mm"))
		{
			if (!ac.getName().equals("Zeit"))
				valueView.setText("00:00");
		}

	}
	
	//TODO comment
	public void setList()
	{
		
		for (int i=0; i<7; i++)
		{
			AnalysisCategory ac = db.getAnalysisCategoryById(db.getCategoryIdByPosition(i+1));
			if (ac == null)
				continue;
			
			int viewId = getResources().getIdentifier("workout_element_"+i, "id", mContext.getPackageName());
			View listElement = mView.findViewById(viewId);
			listElement.setOnClickListener(new CustomListOnClickListener());
			
			TextView valueView = ((TextView) listElement.findViewById(R.id.live_tracking_element_value_text));
			db = new DataBaseHandler(mContext);
			List <Coordinates> listContents = new ArrayList<Coordinates>();
			listContents = db.getAllCoordinatePairs();
			
			formatCategoryHeadline(ac, valueView);

			
			//Tracking Kategorien Werte zuordnen
			switch(ac.getId())
			{
				case 1:		//Dauer
					valueView.setText(TrackService.calcDuration(listContents));
					break;
				case 2:		//Distanz
					valueView.setText(String.valueOf(TrackService.calcDistance(listContents)));
					break;
				case 3:		//Hoehenmeter aufwaerts
					valueView.setText(String.valueOf(TrackService.calcElevation(listContents)));
					break;
				case 4:		//Hoehenmeter abwaerts
					valueView.setText(String.valueOf(TrackService.calcDescent(listContents)));
					break;
				case 5:		//Verbrannte Kalorien
					valueView.setText(String.valueOf(TrackService.calcCaloriesBurned(listContents)));
					break;
				case 6:		//Durchschnittsgeschwindigkeit
					valueView.setText(String.valueOf(TrackService.calcPace(listContents)));
					break;
				case 7:		//Zeit
					Calendar c = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
					valueView.setText(sdf.format(c.getTime()));
					break;
				default:	//Wird nie erreicht
					break;
			}
			
			//Zu Karegorien icons laden
			int imageId = getResources().getIdentifier(ac.getImageName(), "drawable", mContext.getPackageName());
			((ImageView) listElement.findViewById(R.id.live_tracking_element_value_icon)).setImageResource(imageId);
			//Zu Kategorien Ueberschriften laden		
			((TextView) listElement.findViewById(R.id.live_tracking_element_name)).setText(ac.getName() + " (" + ac.getFormat() + ")");
		
		}
	}
	//TODO comment
	private class CustomListOnClickListener implements View.OnClickListener
	{
		private List<AnalysisCategory> mCategoryList;
		private ListView mListView = (ListView) ((Activity) mContext).findViewById(R.id.category_list);;
		
		@Override
		public void onClick(View v) {
			for (int i=0; i<7; i++)
			{
				if (v.getId() == getResources().getIdentifier("workout_element_"+i, "id", mContext.getPackageName()))
				{
					mCategoryList = db.getAllAnalysisCategories();
					String[] mStringCategories = new String[mCategoryList.size()];
					for (int j=0; j<mCategoryList.size(); j++)
						mStringCategories[j] = mCategoryList.get(j).getName() + " (" + mCategoryList.get(j).getFormat() + ")";
						
					mView.findViewById(R.id.workout_layout).setVisibility(View.GONE);
					mListView.setVisibility(View.VISIBLE);
					mListView.setAdapter(new ArrayAdapter<String>(mContext, R.layout.list_row, R.id.textView, mStringCategories));
					mListView.setOnItemClickListener(new CustomCategoryListOnItemClickListener(i));
				}
			}
			
		}	
		
		//TODO comment
		private class CustomCategoryListOnItemClickListener implements OnItemClickListener
		{
			private int categoryPosition;
			
			public CustomCategoryListOnItemClickListener(int categoryPosition) {
				this.categoryPosition = categoryPosition;
			}
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				db.updateCategoryPosition(new CategoryPosition(categoryPosition+1, mCategoryList.get(position).getId()));				
				mListView.setVisibility(View.GONE);
				setList();
				mView.findViewById(R.id.workout_layout).setVisibility(View.VISIBLE);
			}	
		}
	}

	//Starten, Anhalten und Auswerten von Tracking
	public void changeTrackingState(View view) {
		// Container Activity
		MenuContainerActivity ac = (MenuContainerActivity) getActivity();

		// Start Tracking
		if (view.getTag() == null) {
			
			//Koordinaten des vergangenen Tracking aus DB entfernen
			db.clearCoordinates();
			
			//Tracking über GPS oder Netzwerk starten
			gps = new GPSTracker(mContext, this);
			view.setTag(1);
			
			//Überschrift von Start auf Stop aendern
			((TextView) view).setText(getString(R.string.button_workout_stop));

			// Achievements und Menü sperren solange Tracking aktiv
			ac.setLocked(true);
			ac.invalidateOptionsMenu();

		} 
		else if ((Integer) view.getTag() == 1) {
			view.setTag(2);
			
			//Tracking anhalten
			gps.stopUsingGPS();
			
			//Route auf Karte anzeigen
			showTrackingRouteOnMap();

			//Neues Workout speicern
			Workout aktWorkout = populateWorkoutWithData();
			db.addWorkout(aktWorkout);
			db.checkAchievements(aktWorkout, mContext);
			((TextView) view)
					.setText(getString(R.string.button_workout_analyse));
		} 
		
		//Navigation zur Detailauswertung 
		else if ((Integer) view.getTag() == 2) {
			getToTrackingEvaluation();
			//Menü und Achievements reaktivieren
			ac.setLocked(false);
			ac.invalidateOptionsMenu();
		}
	}
	
	
	//Route auf Karte anzeigen
	public void showTrackingRouteOnMap(){
		//Karte einblenden
		mView.findViewById(R.id.workout_layout).setVisibility(View.GONE);
		MapView mapView = (MapView) mView.findViewById(R.id.mapview);
		mapView.setVisibility(View.VISIBLE);
		
		//Zoom Funktionalität aktivieren
		mapView.setBuiltInZoomControls(true);
		
		//Markierungelement definieren
		Drawable marker = getResources().getDrawable(
				android.R.drawable.star_big_on);
		int markerWidth = marker.getIntrinsicWidth();
		int markerHeight = marker.getIntrinsicHeight();
		marker.setBounds(0, markerHeight, markerWidth, 0);
		
		
		//Markierungselement auf Karte positionieren
		ResourceProxy resourceProxy = new DefaultResourceProxyImpl(mContext);
		MyItemizedOverlay myItemizedOverlay = new MyItemizedOverlay(marker,
				resourceProxy);
		mapView.getOverlays().add(myItemizedOverlay);
		List<Coordinates> listContents = new ArrayList<Coordinates>();
		listContents = db.getAllCoordinatePairs();
		
		//Koordinaten als Markierungselement auf Karte anzeigen
		for (Coordinates i : listContents) {
			GeoPoint myPoint1 = new GeoPoint(i.get_latitude(),
					i.get_longitude());
			myItemizedOverlay.addItem(myPoint1, "myPoint1", "myPoint1");
		}
		
	}
	
	//Neues Workout aus aktuellen Trackingergebnissen erstellen
	public Workout populateWorkoutWithData(){
		List <Coordinates> listContents = new ArrayList<Coordinates>();
		listContents = db.getAllCoordinatePairs();
		String duration = TrackService.calcDuration(listContents);
		double pace = TrackService.calcPace(listContents);
		double elevation = TrackService.calcElevation(listContents);
		double descent = TrackService.calcDescent(listContents);
		double calories_burned = TrackService.calcCaloriesBurned(listContents);
		double distance = TrackService.calcDistance(listContents);
		return new Workout(duration, pace, elevation, descent, calories_burned, distance, new Date());
	}

	public void getToTracking(SherlockFragment single_evaluation) {
		((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction()
				.add(R.id.currentFragment, single_evaluation).commit();
	}

	
	//Zur Detailauswertung
	public void getToTrackingEvaluation() {
		
		//TODO comment
		Bundle bundle = new Bundle();
		bundle.putInt("segmentlength", mSegmentList.size());
		for (int i=0; i<mSegmentList.size(); i++)
			bundle.putStringArray("segment"+String.valueOf(i), mSegmentList.get(i).toStringArray());
		EvaluationViewPager evp = new EvaluationViewPager();
		evp.setArguments(bundle);
		((FragmentActivity) mContext)
				.getSupportFragmentManager()
				.beginTransaction()
			.replace(R.id.currentFragment,
						evp, evp.TAG).addToBackStack(null).commit();
	}

}