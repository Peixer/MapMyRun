package de.dhbw.player;

import de.dhbw.container.R;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PlayerFragment extends Fragment{
	
	public PlayerFragment() {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        view.findViewById(R.id.player_playlist_slow).setVisibility(View.VISIBLE);
        
        Log.d("Test","PlayerFragment created");
        
		return view;
	}
}
