package com.music.player.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.amr.AMR;
import com.amr.data.AMRData;
import com.music.player.R;
import com.music.player.adapter.MusicAdapter;
import com.music.player.adapter.RecommendAdapter;
import com.music.player.thread.MusicSeekBarThread;
import com.music.player.util.util;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class PlayActivity extends Activity {

	public static final String TAG = "Play View :" ;
	
	// Custom Class
	private RecommendAdapter recommendAdapter ;
	private MusicAdapter musicAdapter ;
	private MusicSeekBarThread musicSeekBarThread ;
	private Handler musicSeekBarHandler = new Handler (new MusicSeekBarHandlerCallback ()) ;
	
	// AMR
	private AMR amr ;
    
	// Task-Networking 
	AsyncTask<String, Void, ArrayList<AMRData>> asyncTask ;
	
	// MediaPlayBackService
	private MediaPlayer mediaPlayer ;
	
	// Recommend List 
	private ListView recommendListView;
	
	// Widget
	private TextView titleTextView, currentTimeTextView, totalTimeTextView ;
	private ImageView thumbnailImageView ;
	private SeekBar musicSeekbar ;
	
	private int currentPosition, maxMusicCount ;
	
	// Flag
	private boolean repeatFlag, randomFlag ;
	
	public PlayActivity () {
	
		currentPosition = 0 ;
		maxMusicCount = 0 ;
		
		repeatFlag = false ;
		randomFlag = false ;
		
		Log.i (TAG, "PlayActivity" + this.getClass()) ;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//set the layout of the Activity
		setContentView(R.layout.activity_play) ;
				
		//initialize views
		initializes () ;
	}

	private void initializes () {
		
		// AMR Init
		amr =new AMR () ;
		
		// Recommend Adatper
		recommendAdapter = new RecommendAdapter (getApplicationContext()) ;
		// ListView
		recommendListView = (ListView) findViewById (R.id.recommend_list_view) ;
		/* Listener for selecting a item */
		recommendListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {
            	
            	// Music Pause
            	playStopClick ((ImageView) findViewById (R.id.play_stop_btn)) ;
            	
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
		
		// Intent Get
		musicAdapter = new MusicAdapter(getApplicationContext()) ;
		musicAdapter.setMusicArtistList(getIntent().getStringArrayListExtra(util.MUSIC_ARTIST_LIST_NAME)) ;
		musicAdapter.setMusicTitleList(getIntent().getStringArrayListExtra(util.MUSIC_TITLE_LIST_NAME)) ;
		musicAdapter.setAlbumImageList(getIntent().getStringArrayListExtra(util.ALBUM_IMAGE_LIST_NAME)) ;
		musicAdapter.setMusicIDList(getIntent().getStringArrayListExtra(util.MUSIC_ID_LIST_NAME)) ;
		
		currentPosition = getIntent().getIntExtra(util.POSITION_NAME, 0) ;
		maxMusicCount = musicAdapter.getMusicIDList().size() ;
		
		// Init Widget
		titleTextView = (TextView) findViewById (R.id.song_title) ;
		currentTimeTextView = (TextView) findViewById (R.id.song_current_time) ;
		totalTimeTextView = (TextView) findViewById (R.id.song_total_time) ;
		
		thumbnailImageView = (ImageView) findViewById (R.id.song_thumbnail_image) ;
		
		musicSeekbar = (SeekBar) findViewById (R.id.song_seekbar) ;
		musicSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			public void onStopTrackingTouch(SeekBar seekBar) {
				mediaPlayer.seekTo(seekBar.getProgress() * 1000) ;
			}
			
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
			}
		});
		// Music Player
		mediaPlayer = new MediaPlayer();
		// next play music
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer mp) {
				
				// On Repeat
				if (repeatFlag == true) {

					Uri musicUri = getMusicUri(currentPosition) ;
					playMusic (musicUri) ;
					
				}
				// On Random
				else if (randomFlag == true) {

					Uri musicUri = getMusicUri(new Random ().nextInt(maxMusicCount)) ;
					playMusic (musicUri) ;
					
				}
				// Off Repeat, Random
				else {
					Uri musicUri = getMusicUri(getMusicPosition (util.INCREASE)) ;
					playMusic (musicUri) ;
					
				}
			}
		});
		
		playMusic (getMusicUri(currentPosition)) ;
		
	}
	
	private Uri getMusicUri (int position) {
		return Uri.withAppendedPath(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "" + musicAdapter.getMusicID(position)); 
	}
	
	private void playMusic (Uri musicUri) {

		// Start Music
		try {
			mediaPlayer.reset() ;
			mediaPlayer.setDataSource(getApplicationContext(), musicUri) ;
			mediaPlayer.prepare() ;
			mediaPlayer.start() ;
			
			requestRecommendList(musicUri) ;
			
			// Thread Start
			if (musicSeekBarThread != null) releaseMusicSeekBarThread() ;
			setMusicSeekBarThread() ;
			
			// Set Title and thumbnail
			String title = musicAdapter.getMusicTitle(currentPosition) ;
			
			titleTextView.setText((title == null) ? "" :  title) ;
			thumbnailImageView.setImageBitmap(musicAdapter.getAlbumImage(currentPosition)) ;
			
			// Set Seekbar
			totalTimeTextView.setText(util.musicTimeFormatter(mediaPlayer.getDuration() / 1000)) ;
			musicSeekbar.setProgress(0) ;
			musicSeekbar.setMax(mediaPlayer.getDuration() / 1000) ;
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void requestRecommendList (Uri musicUri) {
		// Call AMR
		try {
			// AsyncTask Init
			asyncTask = new AsyncTask<String, Void, ArrayList<AMRData>>() {

				@Override
			    protected void onPreExecute() {
			        super.onPreExecute();
				}
				
				@Override
				protected ArrayList<AMRData> doInBackground(String... params) {
					
					return amr.getKeywordToRecommendLists(params[0],
							musicAdapter.getMusicArtist(currentPosition),
							musicAdapter.getMusicTitle(currentPosition), 5) ;
				}
			
				@Override
			    protected void onPostExecute(ArrayList<AMRData> result) {
			        super.onPostExecute(result);
			         
			        Log.d (TAG + "come :", "onPostExecute") ;
			        
			        // Init
			        recommendAdapter.clearAdapter() ;
			        
			        // Set List View
			        if(result == null || result.size() == 0) {

			        	// Debug lists
						recommendAdapter.putRecommendList(null, null, null, null);
					}
					else {
						for (AMRData list : result) {
							recommendAdapter.putRecommendList(
									list.getTrackID(), list.getArtist(), 
									list.getTitle(), list.getURL()) ;
						}
					}
					// List View Shows
					recommendListView.setAdapter(recommendAdapter);
			    }
			}.execute(musicUri.toString()) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Seek Bar controll
	private void setMusicSeekBarThread () {
		
		musicSeekBarThread = new MusicSeekBarThread (musicSeekBarHandler) ;
		musicSeekBarThread.setDaemon(true) ;
		musicSeekBarThread.start() ; 
	}
	
	private void releaseMusicSeekBarThread () {
		try {
			musicSeekBarThread.setRun(false) ;
			musicSeekBarThread.interrupt() ;
			musicSeekBarThread = null ;
		} catch (Exception e) {
			Log.e (TAG, "MusicSeekBarThread release Error") ;
			musicSeekBarThread = null ;
		}
	}
	
	// Buttons functions
	// Music Play Repeat On/Off
	public void repeatClick (View v) {
		
		repeatFlag = !repeatFlag ;
		
		// On repeat
		if (repeatFlag == true) 
			// off repeat
			((ImageButton) v).setImageResource(R.drawable.on_repeat_button_selector) ;
		// Off repeat
		else 
			// on repeat
			((ImageButton) v).setImageResource(R.drawable.off_repeat_button_selector) ;
	}
	
	// Music Play Random On/Off
	public void randomClick (View v) {
		
		randomFlag = !randomFlag ;
		
		// On Random
		if (randomFlag == true) 
			// off Random
			((ImageButton) v).setImageResource(R.drawable.on_random_button_selector) ;
		// Off Random
		else 
			// on Random
			((ImageButton) v).setImageResource(R.drawable.off_random_button_selector) ;
	}
	
	// Music Previous Play
	public void previousClick (View v) {
		currentPosition = getMusicPosition (util.DECREASE) ;

		Uri musicUri = getMusicUri(currentPosition) ;
		playMusic (musicUri) ;
		
	}
	
	// Music Next Play
	public void nextClick (View v) {
		currentPosition = getMusicPosition (util.INCREASE) ;
		
		Uri musicUri = getMusicUri(currentPosition) ;
		playMusic (musicUri) ;
		
	}
	
	// Music ReWard 15 Second
	public void rewardClick (View v) {
		mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - util.SEEK) ;
	}
	
	// Music ForWard 15 Second
	public void forwardClick (View v) {
		mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + util.SEEK) ;
	}
	
	// Music play and stop
	public void playStopClick (View v) {
		
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.pause() ;
			
			musicSeekBarThread.setHandler(false ) ;
			((ImageButton) v).setImageResource(R.drawable.stop_button_selector);
		}
		else {
			mediaPlayer.start() ;
			
			musicSeekBarThread.setHandler(true ) ;
			((ImageButton) v).setImageResource(R.drawable.play_button_selector);
		}
	}
	
	
	// 뮤직 리스트 포지션 계산
	private int getMusicPosition (int increase) {
		
		// On Random
		if (randomFlag == true) 
			return new Random ().nextInt(maxMusicCount) ;
		// 현재 곡이 리스트의 마지막 일 때
		else if (currentPosition + increase == maxMusicCount) 
			return 0 ;
		// 현재 곡이 리스트의 처음 일 때
		else if (currentPosition + increase < 0)
			return maxMusicCount - 1 ;
		else 
			return currentPosition + increase ;
	}
	
	private class MusicSeekBarHandlerCallback implements Handler.Callback {
		public boolean handleMessage (Message msg) {
			
			try {
				int position = mediaPlayer.getCurrentPosition() /1000 ;
				
				musicSeekbar.setProgress(position) ;
				currentTimeTextView.setText(
						util.musicTimeFormatter(position)) ;
			} catch (Exception e) {}
			
			return true ;
		}
	}
	
	protected void onPause () {
		super.onPause() ;
		
		musicSeekBarThread.setHandler(false ) ;
	}

	protected void onResume() {
		super.onResume();

		musicSeekBarThread.setHandler(true ) ;
	}
	
	protected void onDestroy () {
		super.onDestroy();
		
		mediaPlayer.stop();
		mediaPlayer.reset();
		mediaPlayer.release();
		
		if (musicSeekBarThread != null) releaseMusicSeekBarThread() ;
	}
}
