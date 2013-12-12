package de.dhbw.contents;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import de.dhbw.container.R;
import de.dhbw.database.AnalysisCategory;
import de.dhbw.database.CategoryPosition;
import de.dhbw.database.DataBaseHandler;
	
public class CustomListOnClickListener implements View.OnClickListener {
	
	//OnClickListener; Falls auf ein Kachelelement geklickt wurde, rufe Liste zur Auswahl einer Kategorie (z.B. Dauer, Kalorien etc) aus
	
	private List<AnalysisCategory> mCategoryList;
	private ListView mListView;
	private DataBaseHandler db;
	
	private LiveTrackingFragment mLiveTrackingFragment;
	
	public CustomListOnClickListener(LiveTrackingFragment fragment) {
		mLiveTrackingFragment = fragment;
		mListView = (ListView) ((Activity) mLiveTrackingFragment.mContext).findViewById(R.id.category_list);
		db = new DataBaseHandler(mLiveTrackingFragment.mContext);
	}
	
	@Override
	public void onClick(View v) {
		for (int i=0; i<7; i++)
		{
			if (v.getId() == mLiveTrackingFragment.getResources().getIdentifier("workout_element_"+i, "id", mLiveTrackingFragment.mContext.getPackageName()))
			{
				List<String> listElements = new ArrayList<String>();
				mCategoryList = db.getAllAnalysisCategories();
				for (AnalysisCategory category : mCategoryList)
					listElements.add(String.valueOf(category.getId()));
					
				mLiveTrackingFragment.mView.findViewById(R.id.workout_layout).setVisibility(View.GONE);
				
				mListView.setAdapter(new CustomListAdapter(mLiveTrackingFragment.mContext, R.layout.list_row, listElements));
				mListView.setOnItemClickListener(new CustomCategoryListOnItemClickListener(i));
				
				mListView.setVisibility(View.VISIBLE);
			}
		}			
	}	
	
	//Setze Liste der verfügbaren Kategorien
	private class CustomListAdapter extends ArrayAdapter<String> {

		private List<String> objects;
		
		public CustomListAdapter(Context context, int resource,
				List<String> objects) {
			super(context, resource, objects);
			this.objects = objects;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			LayoutInflater inflater = (LayoutInflater) mLiveTrackingFragment.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.list_row, null);
			
			AnalysisCategory category = db.getAnalysisCategoryById(Integer.parseInt(objects.get(position)));
			
			((TextView) view.findViewById(R.id.textView)).setText(category.getName() + " (" + category.getFormat() + ")");
			int imageId = mLiveTrackingFragment.getResources().getIdentifier(category.getImageName(), "drawable", mLiveTrackingFragment.mContext.getPackageName());
			((ImageView) view.findViewById(R.id.icon)).setImageResource(imageId);
			
			return view;
			//return super.getView(position, convertView, parent);
		}
		
		
	}
	
	//OnClickListener; Schreibe ausgewählte Kategorie in die Datenbank, lade neues Layout und zeige Live-Tracking an
	public class CustomCategoryListOnItemClickListener implements OnItemClickListener
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
			mLiveTrackingFragment.setList();
			mLiveTrackingFragment.mView.findViewById(R.id.workout_layout).setVisibility(View.VISIBLE);
		}	
	}
}