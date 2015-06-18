package com.chartrecommend.youtube;

import android.content.Context;
import android.util.Log;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubePlayer implements YouTubePlayer.OnInitializedListener {

	private final String TAG = "YoutubePlayer :" ;
	
	private Context context ;
	private YouTubePlayerView youtubeView ;
	
	// Video ID
	private String ID ;
	
	public YoutubePlayer (Context context, YouTubePlayerView youtubeView) {
		
		this.context = context ;
		this.youtubeView = youtubeView ;
		
		initialize () ;
	}
	
	private void initialize () {
		
		ID = null ;
		
		Log.i (TAG, "Initialize Come") ;
	}
	
	@Override
	public void onInitializationFailure(Provider provider,
			YouTubeInitializationResult result) {
		Log.e (TAG, "Youtube Play Error" + result) ;
	}

	@Override
	public void onInitializationSuccess(Provider provider, YouTubePlayer youtubePlayer,
			boolean isWasRestored) {
		/** add listeners to YouTubePlayer instance **/
		youtubePlayer.setPlayerStateChangeListener(new PlayerStateChangeListener() {
			
			@Override
			public void onVideoStarted() {}
			
			@Override
			public void onVideoEnded() {}
			
			@Override
			public void onLoading() {}
			
			@Override
			public void onLoaded(String arg0) {}
			
			@Override
			public void onError(ErrorReason arg0) {}
			
			@Override
			public void onAdStarted() {}
		});
		youtubePlayer.setPlaybackEventListener(new PlaybackEventListener() {
			
			@Override
			public void onStopped() {}
			
			@Override
			public void onSeekTo(int arg0) {}
			
			@Override
			public void onPlaying() {}
			
			@Override
			public void onPaused() {}
			
			@Override
			public void onBuffering(boolean arg0) {}
		});
		
		/** Start buffering **/
		if (isWasRestored == false)
			youtubePlayer.loadVideo(ID);
	}

}
