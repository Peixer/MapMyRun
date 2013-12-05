package de.dhbw.tracking;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import de.dhbw.contents.LiveTrackingFragment;
import de.dhbw.database.DataBaseHandler;
import de.dhbw.database.classes.Coordinates;
import de.dhbw.helpers.TrackService;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class GPSTracker extends Service implements LocationListener {
	private final Context mContext;
	
	//LiveTrackingFragment (needed for updating list values)
	private LiveTrackingFragment mLiveTrackingFragment;
	private List<DistanceSegment> mSegmentList = new ArrayList<DistanceSegment>();
	
	// flag for GPS status
	boolean isGPSEnabled = false;

	// flag for network status
	boolean isNetworkEnabled = false;

	boolean canGetLocation = false;

	Location location; // location
	double latitude; // latitude
	double longitude; // longitude
	double altitude; //altitude
	long timestamp;//timestamp of tracking samples

	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 1 meter

	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000*25; //25 sec

	// Declaring a Location Manager
	protected LocationManager locationManager;
	
	// Barriere für Meilenstein-Kilometer-Berechnung
	private int distanceBorder = 0;	
	private final static int DEFAULT_DISTANCE_BORDER = 0;

	public GPSTracker(Context context) {
		this.mContext = context;
		this.distanceBorder = DEFAULT_DISTANCE_BORDER;
		getLocation();
	}
	
	public GPSTracker(Context context, LiveTrackingFragment mLiveTrackingFragment) {
		this.mContext = context;
		this.distanceBorder = DEFAULT_DISTANCE_BORDER;
		setLiveTrackingFragment(mLiveTrackingFragment);
		getLocation();
	}
	
	public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);
 
            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
 
            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
 
            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        }
                    }
                }
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        return location;
    }

	@Override
	public void onLocationChanged(Location location) {
		if (location != null) {
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			altitude = getElevationFromGoogleMaps(longitude, latitude);
			timestamp = location.getTime();
			DataBaseHandler db = new DataBaseHandler(mContext);
			db.addCoordinates(new Coordinates(longitude, latitude, altitude, timestamp));
			
			List<Coordinates> coordinatePairs = db.getAllCoordinatePairs();
			double distance = TrackService.calcDistance(coordinatePairs);
			if (distance >= distanceBorder)
			{
				String distanceString = String.valueOf(distance);
				String duration = TrackService.calcDuration(coordinatePairs);
				String speed = String.valueOf(TrackService.calcPace(coordinatePairs));
				mLiveTrackingFragment.mSegmentList.add(new DistanceSegment(distanceString, duration, speed));
				distanceBorder++;
			}
		}		
		// Update List
		mLiveTrackingFragment.setList();
	}
 
    @Override
    public void onProviderDisabled(String provider) {
    }
 
    @Override
    public void onProviderEnabled(String provider) {
    }
 
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
 
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    
    /**
     * Function to get latitude
     * */
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
         
        // return latitude
        return latitude;
    }
     
    /**
     * Function to get longitude
     * */
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
         
        // return longitude
        return longitude;
    }
    /**
     * Function to check if best network provider
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }
     
    /**
     * Function to show settings alert dialog
     * */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
      
        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");
  
        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
  
        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);
  
        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });
  
        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
  
        // Showing Alert Message
        alertDialog.show();
    }
    
    
    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in the app
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPSTracker.this);
        }      
        distanceBorder = DEFAULT_DISTANCE_BORDER;
    }
    
    public static double getElevationFromGoogleMaps(double longitude, double latitude) {
    	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

    	StrictMode.setThreadPolicy(policy); 
        double result = Double.NaN;
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String url = "http://maps.googleapis.com/maps/api/elevation/"
                + "xml?locations=" + String.valueOf(latitude)
                + "," + String.valueOf(longitude)
                + "&sensor=true";
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                int r = -1;
                StringBuffer respStr = new StringBuffer();
                while ((r = instream.read()) != -1)
                    respStr.append((char) r);
                String tagOpen = "<elevation>";
                String tagClose = "</elevation>";
                if (respStr.indexOf(tagOpen) != -1) {
                    int start = respStr.indexOf(tagOpen) + tagOpen.length();
                    int end = respStr.indexOf(tagClose);
                    String value = respStr.substring(start, end);
                    result = (double)(Double.parseDouble(value));
                }
                instream.close();
            }
        } catch (ClientProtocolException e) {} 
        catch (IOException e) {}

        return result;
    }

	public LiveTrackingFragment getLiveTrackingFragment() {
		return mLiveTrackingFragment;
	}

	public void setLiveTrackingFragment(LiveTrackingFragment mLiveTrackingFragment) {
		this.mLiveTrackingFragment = mLiveTrackingFragment;
	}

    
}