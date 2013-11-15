package de.dhbw.contents;

import java.util.List;

import com.actionbarsherlock.app.SherlockListFragment;

import de.dhbw.container.R;
import de.dhbw.database.AnalysisCategory;
import de.dhbw.database.CategoryPosition;
import de.dhbw.database.DataBaseHandler;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CategoryListFragment extends SherlockListFragment{

	private DataBaseHandler db;
	private ListView mListView;
	
	public CategoryListFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_category_list, null);
		db = new DataBaseHandler(getActivity());
		
		List<AnalysisCategory> mCategoryList = db.getAllAnalysisCategories();
		String[] mStringCategories = new String[mCategoryList.size()];
		for (int i=0; i<mCategoryList.size(); i++)
			mStringCategories[i] = mCategoryList.get(i).getName() + " (" + mCategoryList.get(i).getFormat() + ")";
			
		setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_row, R.id.textView, mStringCategories));
		
		return v;
		//return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		final List<AnalysisCategory> mCategoryList = db.getAllAnalysisCategories();
		mListView = getListView();
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Log.d("Test", "Position: " + getArguments().getInt("position") + ", ErsetztDurchId: " + mCategoryList.get(position).getId());
				db.updateCategoryPosition(new CategoryPosition(getArguments().getInt("position")+1, mCategoryList.get(position).getId()));
				getFragmentManager().popBackStack();
			}
		});
		super.onActivityCreated(savedInstanceState);
	}
}
