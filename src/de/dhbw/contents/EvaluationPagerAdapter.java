package de.dhbw.contents;

import android.support.v4.app.FragmentPagerAdapter;

import com.actionbarsherlock.app.SherlockFragment;


public class EvaluationPagerAdapter extends FragmentPagerAdapter {

	public EvaluationPagerAdapter(android.support.v4.app.FragmentManager fm) {
		super(fm);
	}

	private final String[] TITLES = {"Diagramme","Zwischentabelle","Hauptansicht"};

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
          switch (position) {
          case 0:
              return new EvaluationDiagrammFragment();
          case 1:
              return new EvaluationStagesFragment();
          case 2:
              return new EvaluationSummaryFragment();

          default:
              return null;
          }
      }
}

