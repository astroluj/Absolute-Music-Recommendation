package com.chartrecommend.activity;

import com.chartrecommend.R;
import com.chartrecommend.adapter.MusicAdapter;
import com.chartrecommend.network.MusicChartNetwork;
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
		// MusicAdapter
		musicAdapter = new MusicAdapter (getApplicationContext()) ;	
		setMusicAdapter(getIntent().getStringExtra(util.URL_KEY)) ;
		
		// ListView
		musicListView = (ListView) findViewById (R.id.music_list_view) ;
		musicListView.setAdapter(musicAdapter);
				
		/* Listener for selecting a item */
		musicListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {
            	recommendService(musicAdapter, position) ;
            }
        });
	}

	private void setMusicAdapter (String url) {
		MusicChartNetwork musicChartNetwork = new MusicChartNetwork();
		musicAdapter.putRecommendList (
				musicChartNetwork.getResponseArrays(
						musicChartNetwork.sendData(url))) ;
	}
	
	private void recommendService(MusicAdapter musicAdapter, int position) {
    }
}