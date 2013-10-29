package de.dhbw.container;

import android.support.v4.app.FragmentPagerAdapter;

import com.actionbarsherlock.app.SherlockFragment;

public class MyPagerAdapter extends FragmentPagerAdapter {

	public MyPagerAdapter(android.support.v4.app.FragmentManager fm) {
		super(fm);
	}

	/*private final String[] TITLES = { "Categories", "Home", "Top Paid",
			"Top Free" };*/
	private final String[] TITLES = {"Auswertung1","Auswertung2","Auswertung3"};

	@Override
	public CharSequence getPageTitle(int position) {
		return TITLES[position];
	}

	@Override
	public int getCount() {
		return TITLES.length;
	}

	@Override
	public SherlockFragment getItem(int position) {
		return SuperAwesomeCardFragment.newInstance(position);
	}
}

