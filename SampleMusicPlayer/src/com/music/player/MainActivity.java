package com.music.player;

import com.amr.aidl.amrAIDL;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

public class MainActivity extends Activity {

	private final String TAG = "Sample Music Player :" ;
	private final long INTERVAL = 1000 ;
	private final String MUSIC_RECOMMEND_RESPONSE = "com.music.player.response" ;
	
	private RecommendationReciever recommedRecv ;
	
	// AIDL
	amrAIDL aidlService;
	ServiceConnection serviceConn;
		
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

		// TODO : 필수
		// AIDL SET
		serviceConn = new ServiceConnection() {
			public void onServiceDisconnected(ComponentName name) {
				aidlService = null;
			}

			public void onServiceConnected(ComponentName name, IBinder service) {
				aidlService = amrAIDL.Stub.asInterface(service);
			}
		};
				
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
				// Send Broad
				if (isFirstPlayFlag == false) {
					
					// Register Receiver
					if (recommedRecv == null) {
						recommedRecv = new RecommendationReciever() ;
						registerReceiver(recommedRecv, new IntentFilter (MUSIC_RECOMMEND_RESPONSE)) ;
					}
					// Call AIDL
					try {
						aidlService.getInformationToRecommendLists("한반도", "어쩌란 말입니까",
								MUSIC_RECOMMEND_RESPONSE) ;
						isFirstPlayFlag = true ;
					} catch (RemoteException e) {
						e.printStackTrace();
					} catch (NullPointerException e) {
					}
				}
				
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
				String lists[] = intent.getStringArrayExtra("lists") ;
				// Debug lists
				for (String list : lists)
					Log.d (TAG, list) ;
				
				unregisterReceiver () ;
			}
		}
	}
	
	protected void onPause () {
		super.onPause() ;
		
		// AIDL disable
		unbindService(serviceConn);
	}

	protected void onResume() {
		super.onResume();

		// AIDL enable
		Intent aidlIntent = new Intent("com.amr.service.AIDLService");
		Log.d(TAG, "Connection bind " + bindService(aidlIntent, serviceConn, BIND_AUTO_CREATE));
	}
	
	protected void onDestroy () {
		super.onDestroy();
		
		mediaPlayer.stop();
		mediaPlayer.release();
		playHandler.removeCallbacks(moveSeekBar) ;
		
		unregisterReceiver () ;
		
		// AIDL disable
		unbindService(serviceConn);
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