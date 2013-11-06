package de.dhbw.achievement;

import java.util.ArrayList;
import java.util.List;

import de.dhbw.container.R;
import de.dhbw.database.Achievement;
import de.dhbw.database.DataBaseHandler;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AchievementFragment extends ListFragment{

	public AchievementFragment() {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.achievement_fragment, container, false);
		
		List<String> tempList = new ArrayList<String>();
		DataBaseHandler db = new DataBaseHandler(getActivity());
		for (int i=0; i<db.getAchievementCount(); i++)
			tempList.add("");
		
		// Expandable list: http://androidword.blogspot.de/2012/01/how-to-use-expandablelistview.html
		setListAdapter(new AchievementsArrayAdapter(getActivity(), R.layout.achievements_list_element, R.id.achievement_name, tempList));
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	public class AchievementsArrayAdapter extends ArrayAdapter<String> {
		
		public AchievementsArrayAdapter(Context context, int resource, int textViewResourceId, List<String> objects) 
		{			
			super(context, resource, textViewResourceId, objects);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		
			LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.achievements_list_element, parent, false);
			
			ImageView imageView = (ImageView) view.findViewById(R.id.achievement_icon);
			TextView nameView = (TextView) view.findViewById(R.id.achievement_name);
			TextView descriptionView = (TextView) view.findViewById(R.id.achievement_description);
			
			//ListView listView = getListView();
			DataBaseHandler db = new DataBaseHandler(getActivity());
			Achievement achievement = db.getAchievement(position+1);
			
			int imageID = getResources().getIdentifier(achievement.getImageName() , "drawable", getActivity().getPackageName());
			if (imageID == 0)
				imageView.setImageResource(R.drawable.ic_questionmark);
			else
				imageView.setImageResource(imageID);
			nameView.setText(achievement.getName());
			descriptionView.setText(achievement.getDescription());
			
			return view;
			
			//return super.getView(position, convertView, parent);
		}
	}
}