package com.music.player;


import com.amr.aidl.amrAIDL;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends Activity {

	// Custom Class
	private RecommendationReciever recommedRecv ;
	private MusicAdapter musicAdapter;
	
	// AMRAIDL
	private amrAIDL aidlAMRService ;
	private ServiceConnection amrServiceConn ;
		
	private ListView musicListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//set the layout of the Activity
		setContentView(R.layout.activity_main) ;

		// TODO : 필수
		// AIDL SET
		serviceConnection () ;
				
		//initialize views
		initializes();
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
	
	private void initializes(){
		// Receiver Registered and AMR called
		if (recommedRecv == null) {
			recommedRecv = new RecommendationReciever() ;
			registerReceiver(recommedRecv, new IntentFilter (util.MUSIC_RECOMMEND_RESPONSE_FILTER)) ;
		}
				
		// MusicAdapter
		musicAdapter = new MusicAdapter (getApplicationContext()) ;	
		
		// ListView
		musicListView = (ListView) findViewById (R.id.music_list_view) ;
		musicListView.setAdapter(musicAdapter);
				
		/* Listener for selecting a item */
		musicListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {
                playMusicService(musicAdapter, position) ;
            }
        });
	}

	private void playMusicService(MusicAdapter musicAdapter, int position) {
    	try {
    		Intent intent = new Intent(MainActivity.this, PlayActivity.class) ;
    		    
    		// Select Position
    		intent.putExtra("position", position) ;
    	
    		// Music Info
		    intent.putStringArrayListExtra("musicArtistList", musicAdapter.getMusicArtisList()) ;
		    intent.putStringArrayListExtra("musicTitleList", musicAdapter.getMusicTitleList()) ;
		    intent.putStringArrayListExtra("albumImageList", musicAdapter.getAlbumImageList()) ;
		    intent.putStringArrayListExtra("musicIDList", musicAdapter.getMusicIDListt()) ;
    		
    	    startActivityForResult(intent, util.REQUEST_CODE);
    	} catch (ActivityNotFoundException e) {
    		e.printStackTrace();
    		Log.e (util.TAG, "ActivityNotFoundException") ;
    	}
    	
		// Call AIDL
		try {
			Uri musicURI = Uri.withAppendedPath(
					MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "" + musicAdapter.getMusicID(position)); 
			aidlAMRService.getKeywordToRecommendLists(musicURI.toString(), 
					util.MUSIC_RECOMMEND_RESPONSE_FILTER,
					musicAdapter.getMusicArtist(position),
					musicAdapter.getMusicTitle(position), 5) ;
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		//aidlMediaService.open(new long [] {id}, 0);
		//aidlMediaService.play();
    }
	
	protected void onActivityResult (int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent) ;
		
		if (requestCode == util.REQUEST_CODE) {
			if (intent != null) {
				
			}
		}
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