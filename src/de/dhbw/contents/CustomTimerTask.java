package de.dhbw.contents;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import de.dhbw.container.R;
import de.dhbw.database.AnalysisCategory;
import de.dhbw.database.DataBaseHandler;

public class CustomTimerTask extends TimerTask {

	private LiveTrackingFragment mLiveTrackingFragment;
	private long start;
	private DataBaseHandler db;
	
	public CustomTimerTask(LiveTrackingFragment fragment) {
		mLiveTrackingFragment = fragment;
		start = System.currentTimeMillis();
		db = new DataBaseHandler(mLiveTrackingFragment.mContext);
	}
	
	@Override
	public void run() {
		
		for (int i=0; i<7; i++)
		{
			final AnalysisCategory ac = db.getAnalysisCategoryById(db.getCategoryIdByPosition(i+1));
			if (ac == null)
				continue;
			
			if (ac.getId() != 1 && ac.getId() != 7)
				continue;
			
			int viewId = mLiveTrackingFragment.getResources().getIdentifier("workout_element_"+i, "id", mLiveTrackingFragment.mContext.getPackageName());
			final View listElement = mLiveTrackingFragment.mView.findViewById(viewId);
			listElement.setOnClickListener(new CustomListOnClickListener(mLiveTrackingFragment));
			final TextView valueView = ((TextView) listElement.findViewById(R.id.live_tracking_element_value_text));
			
			String text = "";
			
			if (ac.getId() == 1)
			{
				long diff = System.currentTimeMillis() - start;
				DecimalFormat df = new DecimalFormat("00");
            	int seconds = (int) (diff / 1000) % 60 ;
            	int minutes = (int) ((diff / (1000*60)) % 60);
            	int hours   = (int) ((diff / (1000*60*60)) % 24);
            	text = df.format(hours) + ":" + df.format(minutes) + ":" + df.format(seconds);
			}
			else
			{
				Calendar c = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
				text = sdf.format(c.getTime());
			}
			final String finalText = text;
			mLiveTrackingFragment.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                	valueView.setText(finalText);			
        			//Zu Kategorien Icons laden
        			int imageId = mLiveTrackingFragment.getResources().getIdentifier(ac.getImageName(), "drawable", mLiveTrackingFragment.mContext.getPackageName());
        			((ImageView) listElement.findViewById(R.id.live_tracking_element_value_icon)).setImageResource(imageId);
        			//Zu Kategorien Überschriften laden		
        			((TextView) listElement.findViewById(R.id.live_tracking_element_name)).setText(ac.getName() + " (" + ac.getFormat() + ")");	
                }
            });
		}
	}

}
