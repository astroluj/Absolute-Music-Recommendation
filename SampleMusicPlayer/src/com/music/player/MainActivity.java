package com.music.player;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
	private final static String MUSIC_RECOMMEND_REQUEST = "com.amr.request";
	private final static String MUSIC_RECOMMEND_RESPONSE = "com.music.player.response" ;
	
	private RecommendationReciever recommedRecv ;
	
	private MediaPlayer mediaPlayer ;
	private SeekBar musicSeekbar;
	private ImageButton playBtn ;
	
	private Handler playHandler = new Handler();
	
	private boolean isFirstPlayFlag ;
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
			// Send Broad
			if (isFirstPlayFlag == false) {
				isFirstPlayFlag = true ;
				
				// Register Receiver
				if (recommedRecv == null) {
					recommedRecv = new RecommendationReciever() ;
					registerReceiver(recommedRecv, new IntentFilter (MUSIC_RECOMMEND_RESPONSE)) ;
				}
				// Set Action
				Intent intent = new Intent (MUSIC_RECOMMEND_REQUEST) ;
				// Set Data
				intent.putExtra("title", "어쩌란 말입니까") ;
				intent.putExtra("singer", "한반도") ;
				// Want to intent action
				intent.putExtra("action", MUSIC_RECOMMEND_RESPONSE) ;
				
				// Request Recommendation list
				sendBroadcast(intent);
			}
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
	
	
	// Catch Recommendation list on Intent Action 
	private class RecommendationReciever extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {

			// Check Action
			if (intent.getAction().equals(MUSIC_RECOMMEND_RESPONSE)) {
				// Push shows music list
				
				unregisterReceiver () ;
			}
		}
	}
	
	@Override
	public void onDestroy () {
		super.onDestroy();
		
		mediaPlayer.stop();
		mediaPlayer.release();
		playHandler.removeCallbacks(moveSeekBar) ;
		
		unregisterReceiver () ;
	}
	
	private void unregisterReceiver () {
		// UnRegister Receiver
		try {
			if (recommedRecv != null) {
				unregisterReceiver(recommedRecv);
				recommedRecv = null ;
			} 
		} catch (IllegalArgumentException e) {
			recommedRecv = null ;
		}
	}
}