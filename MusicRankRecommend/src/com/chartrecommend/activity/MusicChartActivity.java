package com.chartrecommend.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.chartrecommend.R;
import com.chartrecommend.adapter.MusicAdapter;
import com.chartrecommend.adapter.RecommendAdapter;
import com.chartrecommend.parser.MusicChartParser;
import com.chartrecommend.task.RecommendTask;
import com.chartrecommend.task.YoutubeSearchTask;
import com.chartrecommend.util.Scale;
import com.chartrecommend.util.util;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayerView;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class MusicChartActivity extends YouTubeBaseActivity {

	private final String TAG = "Music Chart :" ;
	// View Height
	private int RECOMMEND_HEIGHT, YOUTUBE_HEIGHT, SUB_YOUTUBE_HEIGHT ;
	
	// Custom Class
	private MusicAdapter musicAdapter ;
	private RecommendAdapter recommendAdapter ;
	private Scale scale ;
	
	private YouTubePlayerView youtubeView ;
	
	// Task-Networking 
	RecommendTask recommendTask ;
		
	private RelativeLayout relativeLayout ;
	private RelativeLayout.LayoutParams params ;
	// Top100 List
	private ListView musicListView;
	// Recommend List 
	private ListView recommendListView;
		
	private int currentPosition ;
	
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
		// Scale Setting
		// get Scale
		DisplayMetrics disM =new DisplayMetrics () ;
		getWindowManager ().getDefaultDisplay().getMetrics(disM) ;
		scale =  new Scale (disM) ;
		RECOMMEND_HEIGHT = (int)(scale.getScaleHeight() * 25 / 100) ;
		YOUTUBE_HEIGHT = (int)(scale.getScaleHeight() * 35 / 100) ;
		SUB_YOUTUBE_HEIGHT = (int)(scale.getScaleHeight() * 40 / 100) ;
				
		relativeLayout = (RelativeLayout) findViewById (R.id.relative_layout) ;
		
		// MusicAdapter
		musicAdapter = new MusicAdapter (getApplicationContext()) ;	
		// Recommend Adatper
		recommendAdapter = new RecommendAdapter (getApplicationContext()) ;

		// Recommend ListView
		recommendListView = new ListView (getApplicationContext()) ;
		recommendListView.setScrollbarFadingEnabled(false) ;
		recommendListView.setVisibility(View.GONE) ;
		recommendListView.setLayoutParams(new LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RECOMMEND_HEIGHT)) ;
		
		/* Listener for selecting a item */
		recommendListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {
            	
            	// 유투브 연결
            	String split = "watch?v=", data = recommendAdapter.getMusicUri(position) ;
            	int watchIndex = data.indexOf(split) ;
            	
            	if (watchIndex > 0) 
            		data = data.substring(watchIndex + split.length(), data.length()) ;
            	
            	try {
            		youtubeService(data) ;
            	} catch (IndexOutOfBoundsException e) {
            		e.printStackTrace();
            	}
            }
        });
		
		// Music ListView
		musicListView = new ListView (getApplicationContext()) ;
		musicListView.setScrollbarFadingEnabled(false) ;
		musicListView.setLayoutParams(new LayoutParams(
				LinearLayoutCompat.LayoutParams.MATCH_PARENT,
				LinearLayoutCompat.LayoutParams.MATCH_PARENT)) ;
		
		// View add indexing
		relativeLayout.addView(musicListView) ;
		relativeLayout.addView(recommendListView) ;
		
		/* Listener for selecting a item */
		musicListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {
            	
            	// Other Views show
            	if (recommendListView.getVisibility() == View.GONE) 
            		recommendListView.setVisibility(View.VISIBLE) ;
            	
        		recommendService(position) ;
        		youtubeService(musicAdapter.getMusicArtist(position) + " "
							+ musicAdapter.getMusicTitle(position)) ;
        		
        		setMusicListViewLayout (youtubeView != null) ;
        	}
        });
		// Listener for scrolling 
		musicListView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
				// Other Views hide
				if (recommendListView.getVisibility() == View.VISIBLE) 
            		recommendListView.setVisibility(View.GONE) ;
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			}
		});
		
		// MusicAdapter
		setMusicAdapter(getIntent().getStringExtra(util.URL_KEY),
				getIntent().getIntExtra(util.CASE_KEY, -1)) ;
	}

	// Youtube Service
	private void youtubeService (String watchData) {
		
		try {
			// Youtube View Dynamic constuructor
			if (youtubeView != null) {
				youtubeView.removeAllViews() ;
				relativeLayout.removeView(youtubeView) ;
				youtubeView = null ;
			}
			youtubeView = new YouTubePlayerView(MusicChartActivity.this) ;
			params = new RelativeLayout.LayoutParams (
					new LayoutParams (
							RelativeLayout.LayoutParams.WRAP_CONTENT,
							YOUTUBE_HEIGHT)) ;
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM) ;
			youtubeView.setLayoutParams(params);
			relativeLayout.addView(youtubeView) ;
			
			new YoutubeSearchTask(youtubeView).execute(
					URLEncoder.encode(watchData, util.UTF_8)) ;
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			if (youtubeView != null) {
				youtubeView.removeAllViews() ;
				relativeLayout.removeView(youtubeView) ;
				youtubeView = null ;
			}
		}
	}
	
	// MusicAdapter Set
	private void setMusicAdapter (String url, int CASE) {
		
		new MusicChartParser (MusicChartActivity.this,
				musicAdapter, musicListView, CASE).execute(url) ;
	}
	
	// MusicListView Layout
	private void setMusicListViewLayout (boolean isYoutubeVisible) {

		if (isYoutubeVisible) {
			params = new RelativeLayout.LayoutParams(new LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					(int)(scale.getScaleHeight() - SUB_YOUTUBE_HEIGHT))) ;
		}
		else {
			params = new RelativeLayout.LayoutParams(new LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.MATCH_PARENT)) ;
		}
		// apply layout
		musicListView.setLayoutParams(params) ;
		relativeLayout.invalidate() ;
	}
	
	// Called AMR
	private void recommendService(int position) {
		// music uri, position
		currentPosition = position ;
		
		// AsyncTask Init
		String featureUri = null ;
		new RecommendTask(this, 
				musicAdapter, recommendAdapter, 
				recommendListView, currentPosition).execute(featureUri) ;
    }
	
	@Override 
	public boolean onKeyDown(int keyCode, KeyEvent event){ 
		
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (youtubeView != null) {

				youtubeView.removeAllViews() ;
				relativeLayout.removeView(youtubeView) ;
				youtubeView = null ;
				
				setMusicListViewLayout (youtubeView != null) ;

				return false ;
			}
		}
		
		return super.onKeyDown(keyCode, event); 
	} 

}