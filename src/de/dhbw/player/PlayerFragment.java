package de.dhbw.player;

import de.dhbw.container.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PlayerFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        
		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
