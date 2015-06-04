package com.music.player;

import java.util.ArrayList;

import com.amr.aidl.amrAIDL;
import com.amr.data.AMRRecommendResponseData;
import com.android.music.IMediaPlaybackService;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends Activity {

	// Custom Class
	private RecommendationReciever recommedRecv ;
	private MusicAdapter musicAdapter;
	
	// AMRAIDL
	private amrAIDL aidlAMRService ;
	// MediaPlayerBackService AIDL
	private IMediaPlaybackService aidlMediaService ;
	private ServiceConnection amrServiceConn, mediaPlayServiceConn ;
    
	private NotificationManager notiManager ;
	private NotificationCompat.Builder notiBuilder ;
	private NotificationCompat.InboxStyle inboxStyle ;
	
	private ListView musicListView;
	
	private MediaPlayer mediaPlayer ;
	
	private Handler playHandler = new Handler();
	
	private boolean isHtc ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//set the layout of the Activity
		setContentView(R.layout.activity_main) ;

		// TODO : 필수
		// AIDL SET
		serviceConnection () ;
		
		//initialize views
		initializes();
	}
	
	private void serviceConnection () {
		amrServiceConn = new ServiceConnection() {
			public void onServiceDisconnected(ComponentName name) {
				aidlAMRService = null ;
				Log.i(util.TAG + "AMR", "Disconnected!");
			}

			public void onServiceConnected(ComponentName name, IBinder service) {
				aidlAMRService = amrAIDL.Stub.asInterface(service);
				Log.i(util.TAG + "AMR", "Connected!");
			}
		};
		mediaPlayServiceConn = new ServiceConnection () {
			public void onServiceDisconnected(ComponentName name) {
				aidlMediaService = null ;
				Log.i(util.TAG + "MediaPlayBackService", "Disconnected!");
			}

			public void onServiceConnected(ComponentName name, IBinder service) {
				aidlMediaService = IMediaPlaybackService.Stub.asInterface(service);
				Log.i(util.TAG + "MediaPlayBackService", "Connected!");
			}
		} ;
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
                Uri musicURI = Uri.withAppendedPath(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "" + musicAdapter.getMusicID(position)); 
                
                //playMusic(musicURI);
                playMusicService(musicAdapter.getMusicID(position)) ;
            }
        });
        
		// Notification Manager
		notiManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE) ;
				
		// Music Player
		mediaPlayer = new MediaPlayer();
	}

	private void playMusicService(int id) {
    	try {
    		// 첫번째 인자를 Media ID로 이루어진
            //  Array를 넘기면 Play 리스트를 연주하게 된다.

    		aidlMediaService.open(new long [] {id}, 0);
    		aidlMediaService.play();
    	} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	//handler to change seekBarTime
	private Runnable moveSeekBar = new Runnable () {
		
		public void run () {
			try {
				// Register Receiver
				if (recommedRecv == null) {
					recommedRecv = new RecommendationReciever() ;
					registerReceiver(recommedRecv, new IntentFilter (util.MUSIC_RECOMMEND_RESPONSE)) ;
				}
				// Call AIDL
				try {
					aidlAMRService.getKeywordToRecommendLists(util.MUSIC_RECOMMEND_RESPONSE,
							"백뱅", "Looser", 5) ;
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (NullPointerException e) {}
				
			} catch (Exception e) {}
		}
	};
	
	

	// Catch Recommendation list on Intent Action 
	private class RecommendationReciever extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			// Check Action
			if (intent.getAction().equals(util.MUSIC_RECOMMEND_RESPONSE)) {
				// Push shows music list
				// Notification Alert
				// Release previous notification
				if (notiBuilder != null) {
					notiManager.cancel(0) ;
					inboxStyle = null ;
					notiBuilder = null ;
				}
				
				PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
				notiBuilder = new NotificationCompat.Builder(getApplicationContext()) ;
				notiBuilder.setContentTitle("추천 리스트") ;
				notiBuilder.setSmallIcon(R.drawable.equalizer) ;
				notiBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.equalizer)) ;
				notiBuilder.setTicker("추천 음악") ;
				notiBuilder.setContentIntent(pendingIntent) ;
				notiBuilder.setAutoCancel(true) ;
				
				// API < API 11
				ArrayList<AMRRecommendResponseData> lists = intent.getParcelableArrayListExtra("AMR Recommend List") ;
				// Debug lists
				if (lists == null || lists.size() == 0) {
					
					notiBuilder.setContentText("비슷한 음악을 찾을 수 없습니다.") ;
				}
				else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
					String contentText = "" ;
					
					for (AMRRecommendResponseData list : lists) {
						contentText += "Artist : " + list.getArtist() ;
						if (list.getAlbum() != null) contentText += " Album : " + list.getAlbum() ;
						contentText += " Title : " + list.getTitle() + "\n" ;
						
					}
					notiBuilder.setContentText(contentText) ;
				}
				else {
					// Inbox Style Set
					inboxStyle = new NotificationCompat.InboxStyle(notiBuilder) ;
					for (AMRRecommendResponseData list : lists) {
						String contentText = "" ;
						contentText += "Artist : " + list.getArtist() ;
						if (list.getAlbum() != null) contentText += " Album : " + list.getAlbum() ;
						contentText += " Title : " + list.getTitle() ;
						
						inboxStyle.addLine(contentText) ;
					}
				}
				// Show notification
				notiManager.notify(0, notiBuilder.build()) ;
			}
		}
	}
	
	protected void onPause () {
		super.onPause() ;
		
		// AIDL disable
		unbindService(amrServiceConn);
		unbindService(mediaPlayServiceConn);
	}

	protected void onResume() {
		super.onResume();

		// AIDL enable
		Intent amrIntent = new Intent(util.AMR_FILTER);
		//amrIntent.setClassName(util.AMR_PACKAGE_NAME, util.AMR_CLASS_NAME) ;
		Log.d(util.TAG, "AMR Connection bind " + bindService(amrIntent, amrServiceConn, BIND_AUTO_CREATE));
		
		Intent mediaPlayIntent = new Intent(util.MEDIA_PLAY_FILTER) ;
		//mediaPlayIntent.setClassName(util.MEDIA_PLAY_PACKAGE_NAME, util.MEDIA_PLAY_CLASS_NAME) ;
        
		Log.d (util.TAG, "MediaPlayer Connection bind " + bindService(mediaPlayIntent, mediaPlayServiceConn, BIND_AUTO_CREATE)) ;
	}
	
	protected void onDestroy () {
		super.onDestroy();
		
		mediaPlayer.stop();
		mediaPlayer.reset();
		mediaPlayer.release();
		
		unregisterReceiver () ;
	}
	
	private void unregisterReceiver () {
		// UnRegister Receiver
		try {
			if (recommedRecv != null) {
				unregisterReceiver(recommedRecv);
				recommedRecv = null ;
			} 
		} catch (IllegalArgumentException e) {
			recommedRecv = null ;
		}
	}
}