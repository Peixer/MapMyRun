package de.dhbw.achievement;

import java.util.List;

import de.dhbw.container.R;
import de.dhbw.database.Achievement;
import de.dhbw.database.DataBaseHandler;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class AchievementFragment extends Fragment{

	//private ExpandableListAdapter mAdapter;
	
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
		
		/*View view = inflater.inflate(R.layout.achievement_fragment, container, false);
		
		List<String> tempList = new ArrayList<String>();
		DataBaseHandler db = new DataBaseHandler(getActivity());
		for (int i=0; i<db.getAchievementCount(); i++)
			tempList.add("");
		
		 Expandable list: http://androidword.blogspot.de/2012/01/how-to-use-expandablelistview.html
			Gesamtdistanz
			Gesamtzeit
			Einzeldistanz
			Einzelzeit
		
		//setListAdapter(new AchievementsArrayAdapter(getActivity(), R.layout.achievements_list_element, R.id.achievement_name, tempList));
		
		return super.onCreateView(inflater, container, savedInstanceState);*/
	}
	
	public class SavedTabsListAdapter extends BaseExpandableListAdapter {
		 
	    private String[] groups = {"Gesamtdistanz", "GesamtZeit", "Einzeldistanz", "Einzelzeit"};

	    /*private String[][] children = {
	        { "Arnold", "Barry", "Chuck", "David" },
	        { "Ace", "Bandit", "Cha-Cha", "Deuce" },
	        { "Fluffy", "Snuggles" },
	        { "Goldy", "Bubbles" }
	    };*/
	    
	    private DataBaseHandler db = new DataBaseHandler(getActivity());
	    
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
	    	// TODO Auto-generated method stub
	    	SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
	    	sharedPreferences.edit().putBoolean("Group"+groupPosition+"Expanded", false)
	    							.commit();
	    	super.onGroupCollapsed(groupPosition);
	    }
	    
	    @Override
	    public void onGroupExpanded(int groupPosition) {
	    	// TODO Auto-generated method stub
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
	        //return children[i].length;
	    	String unit = getUnitByPos(i);
	    	if (unit != null)
	    		return db.getAchievementCount(unit);
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
	        //return children[i][i1];
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
	        /*TextView textView = new TextView(AchievementFragment.this.getActivity());
	        textView.setText(getGroup(i).toString());
	        textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
	        textView.setTextSize(25);
	        return textView;*/
	        
	        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.achievements_list_group, viewGroup, false);
			
			TextView header = (TextView) view.findViewById(R.id.achievement_list_header);
			header.setText(getGroup(i).toString());
			
			return header;
	    }

	    @Override
	    public View getChildView(int i, int i1, boolean b, View arg0, ViewGroup viewGroup) {
	        /*TextView textView = new TextView(AchievementFragment.this.getActivity());
	        textView.setText(getChild(i, i1).toString());
	        return textView;*/
	    	
	    	LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.achievements_list_element, viewGroup, false);
			
			ImageView imageView = (ImageView) view.findViewById(R.id.achievement_icon);
			TextView nameView = (TextView) view.findViewById(R.id.achievement_name);
			TextView descriptionView = (TextView) view.findViewById(R.id.achievement_description);
			
			//ListView listView = getListView();
			DataBaseHandler db = new DataBaseHandler(getActivity());
			//Achievement achievement = db.getAchievement(position+1);
			
			Achievement achievement = db.getAchievementsByUnit(getUnitByPos(i)).get(i1);
	    	Log.d("ListItem Gruppe "+i+", Item "+i1, achievement.toString());
			
			int imageID = getResources().getIdentifier(achievement.getImageName() , "drawable", getActivity().getPackageName());
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
	        //return true;
	    	return false;
	    }
	}
	
	/*public class AchievementsArrayAdapter extends ArrayAdapter<String> {
		
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
	}*/
}