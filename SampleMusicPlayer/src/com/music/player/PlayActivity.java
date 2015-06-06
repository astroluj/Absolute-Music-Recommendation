package com.music.player;


import java.io.IOException;

import com.amr.aidl.amrAIDL;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

public class PlayActivity extends Activity {

	// Custom Class
	private MusicAdapter musicAdapter ;
	
	// AMRAIDL
	private amrAIDL aidlAMRService ;
	private ServiceConnection amrServiceConn ;
    
	// MediaPlayBackService
	private MediaPlayer mediaPlayer ;
	
	private int currentPosition ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//set the layout of the Activity
		setContentView(R.layout.activity_play) ;
				
		// TODO : 필수
		// AIDL SET
		serviceConnection () ;
				
		//initialize views
		initializes () ;
	}

	private void initializes () {
		
		// Intent Get
		currentPosition = getIntent().getIntExtra(util.POSITION_NAME, 0) ;
		
		musicAdapter = new MusicAdapter(getApplicationContext()) ;
		musicAdapter.setMusicArtistList(getIntent().getStringArrayListExtra(util.MUSIC_ARTIST_LIST_NAME)) ;
		musicAdapter.setMusicTitleList(getIntent().getStringArrayListExtra(util.MUSIC_TITLE_LIST_NAME)) ;
		musicAdapter.setAlbumImageList(getIntent().getStringArrayListExtra(util.ALBUM_IMAGE_LIST_NAME)) ;
		musicAdapter.setMusicIDList(getIntent().getStringArrayListExtra(util.MUSIC_ID_LIST_NAME)) ;
		
		// Music Player
		mediaPlayer = new MediaPlayer();
		playMusic (currentPosition) ;
	}
	
	private void playMusic (int position) {
		Uri musicURI = Uri.withAppendedPath(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "" + musicAdapter.getMusicID(position)); 

		// Start Music
		try {
			mediaPlayer.reset() ;
			mediaPlayer.setDataSource(getApplicationContext(), musicURI) ;
			mediaPlayer.prepare() ;
			mediaPlayer.start() ;
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Call AIDL
		try {
			aidlAMRService.getKeywordToRecommendLists(musicURI.toString(), 
					util.MUSIC_RECOMMEND_RESPONSE_FILTER,
					musicAdapter.getMusicArtist(currentPosition),
					musicAdapter.getMusicTitle(currentPosition), 5) ;
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		//aidlMediaService.open(new long [] {id}, 0);
		//aidlMediaService.play();
	}
	
	private void serviceConnection () {
		amrServiceConn = new ServiceConnection() {
			public void onServiceDisconnected(ComponentName name) {
				aidlAMRService = null ;
				Log.i(util.TAG + "AMR", "Disconnected!");
			}

			public void onServiceConnected(ComponentName name, IBinder service) {
				aidlAMRService = amrAIDL.Stub.asInterface(service);
				Log.i(util.TAG + "AMR", "Connected!");
			}
		};
	}
	
	// Buttons functions
	public void playStopClick (View v) {
		if (mediaPlayer.isPlaying())
			mediaPlayer.pause() ;
		else mediaPlayer.start() ;
	}
	
	protected void onPause () {
		super.onPause() ;
		
		// AIDL disable
		unbindService(amrServiceConn);
	}

	protected void onResume() {
		super.onResume();

		// AIDL enable
		Intent amrIntent = new Intent(util.AMR_FILTER);
		//amrIntent.setClassName(util.AMR_PACKAGE_NAME, util.AMR_CLASS_NAME) ;
		Log.d(util.TAG, "AMR Connection bind " + bindService(amrIntent, amrServiceConn, BIND_AUTO_CREATE));
	}
	
	protected void onDestroy () {
		super.onDestroy();
		
		mediaPlayer.stop();
		mediaPlayer.reset();
		mediaPlayer.release();
	}
}
