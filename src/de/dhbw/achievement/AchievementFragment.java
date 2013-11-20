package de.dhbw.achievement;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;


import de.dhbw.container.R;
import de.dhbw.database.Achievement;
import de.dhbw.database.DataBaseHandler;

public class AchievementFragment extends Fragment{
	
	public AchievementFragment() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.achievement_fragment, null);
		
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		
        ExpandableListView elv = (ExpandableListView) v.findViewById(R.id.list);
        elv.setAdapter(new SavedTabsListAdapter());
        for (int i=0; i < elv.getExpandableListAdapter().getGroupCount(); i++)
        {
        	if (sharedPreferences.getBoolean("Group"+i+"Expanded", true))
        		elv.expandGroup(i);
        	else
        		elv.collapseGroup(i);
        }
        
        return v;
	}
	
	public class SavedTabsListAdapter extends BaseExpandableListAdapter {
		 
	    private String[] groups = new String[4];
	    
	    private DataBaseHandler db = new DataBaseHandler(getActivity());
	    
	    public SavedTabsListAdapter() {
	    	groups[0] = "Gesamtdistanz (" + db.getAchievementCount("tkm", true) + " von " + db.getAchievementCount("tkm") + ")";
	    	groups[1] = "GesamtZeit (" + db.getAchievementCount("ts", true) + " von " + db.getAchievementCount("ts") + ")";
			groups[2] = "Einzeldistanz (" + db.getAchievementCount("skm", true) + " von " + db.getAchievementCount("skm") + ")";
			groups[3] = "Einzelzeit (" + db.getAchievementCount("ss", true) + " von " + db.getAchievementCount("ss") + ")";
		}
	    
	    private String getUnitByPos(int position)
	    {
	    	switch (position)
	    	{	    	
			    case 0:
					return "tkm";
				case 1:
					return "ts";
				case 2:
					return "skm";
				case 3:
					return "ss";
				default:
					return null;
			}
	    }	   
	    
	    @Override
	    public void onGroupCollapsed(int groupPosition) {
	    	SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
	    	sharedPreferences.edit().putBoolean("Group"+groupPosition+"Expanded", false)
	    							.commit();
	    	super.onGroupCollapsed(groupPosition);
	    }
	    
	    @Override
	    public void onGroupExpanded(int groupPosition) {
	    	SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
	    	sharedPreferences.edit().putBoolean("Group"+groupPosition+"Expanded", true)
	    							.commit();
	    	super.onGroupExpanded(groupPosition);
	    }
	    
	    @Override
	    public int getGroupCount() {
	        return groups.length;
	    }

	    @Override
	    public int getChildrenCount(int i) {
	    	String unit = getUnitByPos(i);
	    	if (unit != null)
	    		return db.getAchievementCount(unit, true);
	    	else
	    		return db.getAchievementCount();	    	
	    }

	    @Override
	    public Object getGroup(int i) {
	        return groups[i];
	    }

	    @Override
	    public Object getChild(int i, int i1) {
	    	DataBaseHandler db = new DataBaseHandler(getActivity());
	    	return db.getAchievementsByUnit(getUnitByPos(i)).get(i1).getName();
	    }

	    @Override
	    public long getGroupId(int i) {
	        return i;
	    }

	    @Override
	    public long getChildId(int i, int i1) {
	        return i1;
	    }

	    @Override
	    public boolean hasStableIds() {
	        return true;
	    }

	    @Override
	    public View getGroupView(int i, boolean b, View arg0, ViewGroup viewGroup) {
	        
	        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.achievements_list_group, viewGroup, false);
			
			TextView header = (TextView) view.findViewById(R.id.achievement_list_header);
			header.setText(getGroup(i).toString());
			
			return header;
	    }

	    @Override
	    public View getChildView(int i, int i1, boolean b, View arg0, ViewGroup viewGroup) {
	    	
	    	LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.achievements_list_element, viewGroup, false);
			
			ImageView imageView = (ImageView) view.findViewById(R.id.achievement_icon);
			TextView nameView = (TextView) view.findViewById(R.id.achievement_name);
			TextView descriptionView = (TextView) view.findViewById(R.id.achievement_description);
			
			DataBaseHandler db = new DataBaseHandler(getActivity());
			Achievement achievement = (Achievement) db.getAchievementsByUnit(getUnitByPos(i), true).get(i1);
			
	    	Log.d("ListItem Gruppe "+i+", Item "+i1, achievement.toString());
			
			int imageID = getResources().getIdentifier(achievement.getName() , "drawable", getActivity().getPackageName());
			if (imageID == 0)
				imageView.setImageResource(R.drawable.ic_questionmark);
			else
				imageView.setImageResource(imageID);
			nameView.setText(achievement.getName());
			descriptionView.setText(achievement.getDescription());
			
			return view;
	    }

	    @Override
	    public boolean isChildSelectable(int i, int i1) {
	    	return false;	//Default: true
	    }
	}
}