package com.chartrecommend.activity;

import java.util.ArrayList;

import com.amr.AMR;
import com.amr.data.AMRData;
import com.chartrecommend.R;
import com.chartrecommend.adapter.MusicAdapter;
import com.chartrecommend.adapter.RecommendAdapter;
import com.chartrecommend.parser.MusicChartParser;
import com.chartrecommend.util.Scale;
import com.chartrecommend.util.util;
import com.google.android.youtube.player.YouTubePlayerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class MusicChartActivity extends Activity {

	private static final String TAG = "Music Chart :" ;
	
	// Custom Class
	private MusicAdapter musicAdapter ;
	private RecommendAdapter recommendAdapter ;
	private Scale scale ;
	private AMR amr ;
	
	private YouTubePlayerView youtubeView ;
	
	// Task-Networking 
	AsyncTask<String, Void, ArrayList<AMRData>> asyncTask ;
		
	private RelativeLayout relativeLayout ;
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
		// AMR Lib init
		amr = new AMR () ;
		
		// Scale Setting
		// get Scale
		DisplayMetrics disM =new DisplayMetrics () ;
		getWindowManager ().getDefaultDisplay().getMetrics(disM) ;
		scale =  new Scale (disM) ;
		
		// Youtube View Dynamic constuructor
		/*youtubeView = new YouTubePlayerView(getApplicationContext()) ;
		youtubeView.initialize(util.YOUTTUBE_API_KEY, getApplicationContext()) ;
		youtubeView.setVisibility(View.GONE) ;
		youtubeView.setLayoutParams(new LayoutParams (
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));*/
		
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
				(int)(scale.getScaleHeight() * 25 / 100))) ;
		
		/* Listener for selecting a item */
		recommendListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {
            	
            	// 유투브 연결
            	try {
	            	Intent intent =new Intent(
	            			Intent.ACTION_VIEW,
	            			Uri.parse(recommendAdapter.getMusicUri(position))) ; 
	            	startActivity(intent) ;
            	} catch (NullPointerException e) {
            		Log.d (TAG, "Not-exist Recommend List") ;
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
            	
            	if (recommendListView.getVisibility() == View.GONE) 
            		recommendListView.setVisibility(View.VISIBLE) ;
            	
            	if (youtubeView.getVisibility() == View.GONE) {
            		youtubeView.setVisibility(View.VISIBLE) ;
            		relativeLayout.addView(youtubeView) ;
            	}
            		
        		recommendService(position) ;
        	}
        });
		// Listener for scrolling 
		musicListView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
				if (recommendListView.getVisibility() == View.VISIBLE) {
            		recommendListView.setVisibility(View.GONE) ;
				}
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

	private void setMusicAdapter (String url, int CASE) {
		
		new MusicChartParser (MusicChartActivity.this,
				musicAdapter, musicListView, CASE).execute(url) ;
	}
	
	private void recommendService(int position) {
		// music uri, position
		currentPosition = position ;
		// AsyncTask Init
		asyncTask = new AsyncTask<String, Void, ArrayList<AMRData>>() {

			ProgressDialog progressDialog ;
			@Override
		    protected void onPreExecute() {
		        super.onPreExecute();
		        
			     // Loading Progress
		        progressDialog = new ProgressDialog(MusicChartActivity.this);
		        progressDialog.setTitle("Music Chart Recommendation");
		        progressDialog.setMessage("Recommend List Loading...");
		        progressDialog.setIndeterminate(false);
		        progressDialog.show();
			}
			
			@Override
			protected ArrayList<AMRData> doInBackground(String... params) {
				
				return amr.getKeywordToRecommendLists(params[0],
						musicAdapter.getMusicArtist(currentPosition),
						musicAdapter.getMusicTitle(currentPosition), 10) ;
			}
		
			@Override
		    protected void onPostExecute(ArrayList<AMRData> result) {
		        super.onPostExecute(result);
		         
		        Log.d (TAG + "come :", "onPostExecute") ;
		        
		        // Recommed Adatper Init
		        recommendAdapter.clearAdapter() ;
		        
		        // Set List View
		        if(result == null || result.size() == 0) 
		        	// Debug lists
					recommendAdapter.putRecommendList(null, null, null, null) ;
				else recommendAdapter.putRecommendList(result) ;
		        
				// List View Shows
				recommendListView.setAdapter(recommendAdapter);
				
				// Clear Progress
		        progressDialog.dismiss() ;
		    }
		}.execute("") ;
    }
}