package de.dhbw.contents;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.viewpager.extensions.PagerSlidingTabStrip;

import de.dhbw.container.R;

public class EvaluationViewPager extends Fragment {

	public static final String TAG = EvaluationViewPager.class
			.getSimpleName();

	public static EvaluationViewPager newInstance() {
		return new EvaluationViewPager();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.evaluation_pager, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) view
				.findViewById(R.id.tabs);
		ViewPager pager = (ViewPager) view.findViewById(R.id.pager);
		EvaluationPagerAdapter adapter = new EvaluationPagerAdapter(getChildFragmentManager());
		pager.setAdapter(adapter);
		tabs.setViewPager(pager);

	}
}
