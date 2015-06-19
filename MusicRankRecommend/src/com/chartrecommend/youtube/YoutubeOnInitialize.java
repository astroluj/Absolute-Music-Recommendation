package com.chartrecommend.youtube;

import android.util.Log;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;

public class YoutubeOnInitialize implements YouTubePlayer.OnInitializedListener {

	private final String TAG = "YoutubePlayer :" ;
	
	// Video ID
	private String ID ;
	
	public YoutubeOnInitialize (String ID) {
		this.ID = ID ;
		Log.d (TAG, "Constructor come") ;
	}
	
	@Override
	public void onInitializationFailure(Provider provider,
			YouTubeInitializationResult result) {
		Log.e (TAG, "Youtube Play Error" + result) ;
	}

	@Override
	public void onInitializationSuccess(Provider provider, YouTubePlayer youtubePlayer,
			boolean isWasRestored) {
		
		/** Start buffering **/
		if (youtubePlayer != null) {
			if (youtubePlayer.isPlaying()) {
				youtubePlayer.pause();
				youtubePlayer.release() ;
			}
			youtubePlayer.loadVideo(ID);
		}
	}
}
