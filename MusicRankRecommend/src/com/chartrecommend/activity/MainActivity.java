package com.chartrecommend.activity;

import com.chartrecommend.R;
import com.chartrecommend.util.util;

import android.util.Log;
import android.view.View;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

	private static final String TAG = "Music Selection :" ;
	private final int REQUEST_CODE = 1221 ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.i (TAG, "onCreate" + this.getClass()) ;
	}
	
	// Naver music chart
	public void naverClick (View v) {
		startMusicChartActivity (util.NAVER_CHART, util.NAVER) ;
	}
	
	// Billboard music chart
	public void billboardClick (View v) {
		startMusicChartActivity (util.BILLBOARD_CHART, util.BILLBOARD) ;
	}
	
	// Itunes music chart
	public void itunesClick (View v) {
		startMusicChartActivity (util.ITUNES_CHART, util.ITUNES) ;
	}
	
	// Gaon music chart
	public void gaonClick (View v) {
		startMusicChartActivity (util.GAON_CHART, util.GAON) ;
	}
	
	// GomTV music chart
	public void gomtvClick (View v) {
		startMusicChartActivity (util.GOMTV_CHART, util.GOMTV) ;
	}
	
	// MNet music chart
	public void mnetClick (View v) {
		startMusicChartActivity (util.MNET_CHART, util.MNET) ;
	}
		
	private void startMusicChartActivity (String url, int CASE) {
		
		Intent intent = new Intent (MainActivity.this, MusicChartActivity.class) ;
		intent.putExtra(util.URL_KEY, url) ;
		intent.putExtra(util.CASE_KEY, CASE) ;
		
		startActivityForResult(intent, REQUEST_CODE) ;
	}
	
	protected void onActivityResult (int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent) ;
		
		if (requestCode == REQUEST_CODE) {
			if (intent != null) {
				
			}
		}
	}
}
