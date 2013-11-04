package de.dhbw.achievement;

import java.util.ArrayList;
import java.util.List;

import de.dhbw.container.R;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class AchievementFragment extends ListFragment{

	public AchievementFragment() {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.achievement_fragment, container, false);
		
		setListAdapter(new AchievementsArrayAdapter(getActivity(), R.layout.achievements_list_element, (new ArrayList())));
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	public class AchievementsArrayAdapter extends ArrayAdapter<String> {

		public AchievementsArrayAdapter(Context context, int resource,
				List<String> objects) {
			super(context, resource, objects);
			// TODO Auto-generated constructor stub
		}
	}
}