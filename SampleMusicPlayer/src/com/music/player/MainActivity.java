package com.music.player;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

public class MainActivity extends Activity {

	private final static long INTERVAL = 1000 ;
	
	private MediaPlayer mediaPlayer ;
	private SeekBar musicSeekbar;
	private ImageButton playBtn ;
	
	private Handler playHandler = new Handler();
	
	private int songDuration ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//set the layout of the Activity
		setContentView(R.layout.activity_main);

		//initialize views
		initializes();
	}
	
	public void initializes(){
		// Music Player
		mediaPlayer = MediaPlayer.create(this, R.raw.sample);
		songDuration = mediaPlayer.getDuration() ;
				
		// Music Controll
		musicSeekbar = (SeekBar) findViewById (R.id.music_seek_bar) ;
		musicSeekbar.setMax (songDuration) ;
		// No play case
		musicSeekbar.setClickable (false) ;
		
		playBtn = (ImageButton) findViewById (R.id.music_play) ;
	}

	// Music Play
	public void playClick (View view) {
		
		if (musicSeekbar.isClickable() == false) 
			musicSeekbar.setClickable(true) ;
		
		// playing
		if (mediaPlayer.isPlaying ()) {
			playHandler.removeCallbacks(moveSeekBar) ;
			mediaPlayer.pause() ;
			playBtn.setImageResource (android.R.drawable.ic_media_play) ;
		}
		// stopping
		else {
			playHandler.postDelayed(moveSeekBar, INTERVAL);
			mediaPlayer.start () ;
			playBtn.setImageResource (android.R.drawable.ic_media_pause) ;
		}
	}

	//handler to change seekBarTime
	private Runnable moveSeekBar = new Runnable () {
		int currentPos = 0 ;
		
		public void run () {
			try {
				//Get Current Position
				currentPos = mediaPlayer.getCurrentPosition();
				// Set SeekBar Position
				musicSeekbar.setProgress((int)currentPos) ;
				
				playHandler.postDelayed (this, INTERVAL) ;
			} catch (Exception e) {}
		}
	};
	
	@Override
	public void onDestroy () {
		super.onDestroy();
		
		mediaPlayer.stop();
		mediaPlayer.release();
		playHandler.removeCallbacks(moveSeekBar) ;
	}
}