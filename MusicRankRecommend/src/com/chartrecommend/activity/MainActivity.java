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
	public void naverMusicClick (View v) {
		startMusicChartActivity (util.NAVER_CHART) ;
	}
	
	// Daum music chart
	public void daumMusicClick (View v) {
		startMusicChartActivity (util.DAUM_CHART) ;
	}
	
	// Melon music chart
	public void melonMusicClick (View v) {
		startMusicChartActivity (util.MELON_CHART) ;
	}
	
	private void startMusicChartActivity (String url) {
		
		Intent intent = new Intent (MainActivity.this, MusicChartActivity.class) ;
		intent.putExtra(util.URL_KEY, url) ;
		
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
