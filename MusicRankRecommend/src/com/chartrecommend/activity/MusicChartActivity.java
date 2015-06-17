package com.chartrecommend.activity;

import com.chartrecommend.R;
import com.chartrecommend.adapter.MusicAdapter;
import com.chartrecommend.parser.MusicChartParser;
import com.chartrecommend.util.util;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MusicChartActivity extends Activity {

	private static final String TAG = "Music Chart :" ;
	
	// Custom Class
	private MusicAdapter musicAdapter;
	
	private ListView musicListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//set the layout of the Activity
		setContentView(R.layout.activity_music_chart) ;
				
		//initialize views
		initializes();
		
		Log.i (TAG, "onCreate" + this.getClass()) ;
	}
	
	private void initializes(){
		// ListView
		musicListView = (ListView) findViewById (R.id.music_list_view) ;
		/* Listener for selecting a item */
		musicListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {
            	recommendService(musicAdapter, position) ;
            }
        });
		
		// MusicAdapter
		setMusicAdapter(getIntent().getStringExtra(util.URL_KEY),
				getIntent().getIntExtra(util.CASE_KEY, -1)) ;
	}

	private void setMusicAdapter (String url, int CASE) {
		
		musicAdapter = new MusicAdapter (getApplicationContext()) ;	
		new MusicChartParser (MusicChartActivity.this,
				musicAdapter, musicListView, CASE).execute(url) ;
	}
	
	private void recommendService(MusicAdapter musicAdapter, int position) {
    }
}