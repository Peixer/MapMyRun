package de.dhbw.player;

import java.io.IOException;

import de.dhbw.container.R;
import android.app.Fragment;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class PlayerFragment extends Fragment{
	
	private MediaPlayer mp = new MediaPlayer();
	
	private ImageView playButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        view.findViewById(R.id.player_playlist_slow).setVisibility(View.VISIBLE);
        
        playButton = (ImageView) view.findViewById(R.id.player_player_play);
        playButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mp.isPlaying())
					pauseSong(mp);
				else
					playSong(mp);
			}
		});
        
		return view;
	}
	
	private void changeSong(MediaPlayer mediaPlayer)
	{
		stopSong(mediaPlayer);
		try {
			mp.setDataSource("/sdcard/song.mp3");
			mp.prepare();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		playSong(mediaPlayer);
	}
	
	private void playSong(MediaPlayer mediaPlayer)
	{
		mp.start();
		playButton.setImageResource(R.drawable.ic_action_pause);
	}
	
	private void pauseSong(MediaPlayer mediaPlayer)
	{
		mediaPlayer.pause();
		playButton.setImageResource(R.drawable.ic_action_play);
	}
	
	private void stopSong(MediaPlayer mediaPlayer)
	{
		mediaPlayer.stop();
		mediaPlayer.reset();
	}
}
