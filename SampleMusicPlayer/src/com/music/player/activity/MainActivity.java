package com.music.player.activity;


import com.music.player.R;
import com.music.player.adapter.MusicAdapter;
import com.music.player.util.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends Activity {

	public static final String TAG = "Sample Music Player :" ;
	
	// Custom Class
	private MusicAdapter musicAdapter;
	
	private ListView musicListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//set the layout of the Activity
		setContentView(R.layout.activity_main) ;
				
		//initialize views
		initializes();
	}
	
	private void initializes(){
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
		    intent.putStringArrayListExtra("musicIDList", musicAdapter.getMusicIDList()) ;
    		
    	    startActivityForResult(intent, util.REQUEST_CODE);
    	} catch (ActivityNotFoundException e) {
    		e.printStackTrace();
    		Log.e (TAG, "ActivityNotFoundException") ;
    	}
    }
	
	protected void onActivityResult (int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent) ;
		
		if (requestCode == util.REQUEST_CODE) {
			if (intent != null) {
				
			}
		}
	}
}