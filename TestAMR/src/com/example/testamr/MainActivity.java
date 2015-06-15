package com.example.testamr;

import java.util.ArrayList;

import com.amr.aidl.amrAIDL;
import com.amr.data.AMRData;

import android.support.v7.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

	public static final String TAG = "TestAMR View :" ;
	public static final String RECV_ACTION = "com.example.testamr" ;
	
	// Custom Class
	private RecommendationReciever recommedRecv ;
		
	// AMRAIDL
	private amrAIDL aidlAMRService ;
	private ServiceConnection amrServiceConn ;
		
	private ArrayList<String> track_id = new ArrayList<String>() ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// TODO : 필수
		// AIDL SET
		serviceConnection () ;
		
		// Receiver Registered and AMR called
		if (recommedRecv == null) {
			recommedRecv = new RecommendationReciever() ;
			registerReceiver(recommedRecv, new IntentFilter (RECV_ACTION)) ;
		}
	}

	
	private void serviceConnection () {
		amrServiceConn = new ServiceConnection() {
			public void onServiceDisconnected(ComponentName name) {
				aidlAMRService = null ;
				Log.i(TAG , "Disconnected!");
			}

			public void onServiceConnected(ComponentName name, IBinder service) {
				aidlAMRService = amrAIDL.Stub.asInterface(service);
				// 시작 노래 추천 곡 찾기
				Log.i(TAG,  "Connected!");
			}
		};
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		try {
			switch (id) {
			case R.id.user_register:
				if (item.getTitle().equals(getString(R.string.user_register))) {
					item.setTitle(getString(R.string.user_remove)) ;
					aidlAMRService.setUserRegistered(RECV_ACTION, "test") ;
				}
				else {
					item.setTitle(getString(R.string.user_register)) ;
					aidlAMRService.setUserUnregistered(RECV_ACTION, "test") ;
				}
				
				break ;
				
			case R.id.music_recommend:
				aidlAMRService.getKeywordToRecommendLists(null, RECV_ACTION, "김종국", "한 남자", 10) ;
				break ;
				
			case R.id.user_history:
				break ;
				
			case R.id.music_user:
				break ;
				
			case R.id.user_mate:
				break ;
				
			case R.id.user_mate_list:
				break ;
				
			case R.id.review_write:
				aidlAMRService.setReview(RECV_ACTION, "tset", track_id.get(0), "ABCD") ;
				
				break ;
				
			case R.id.music_review:
				aidlAMRService.getReview(RECV_ACTION, track_id.get(0), 0, 10);
				
				break ;
				
			case R.id.user_review:
				aidlAMRService.getUserReview(RECV_ACTION, "test", 0, 10) ;

				break ;
				
			case R.id.user_feed:
				break ;
				
			}
		} catch (RemoteException e) {}
		
		return super.onOptionsItemSelected(item);
	}
	
	protected void onResume() {
		super.onResume();

		// AIDL enable
		Intent amrIntent = new Intent("com.amr.service.AMRService");
		//amrIntent.setClassName(util.AMR_PACKAGE_NAME, util.AMR_CLASS_NAME) ;
		Log.d(TAG, "TestAMR Connection bind " + bindService(amrIntent, amrServiceConn, BIND_AUTO_CREATE));
	
	}
	
	protected void onPause () {
		super.onPause() ;
		
		// AIDL disable
		unbindService(amrServiceConn);
	}
	
	protected void onDestroy () {
		super.onDestroy();
		
		unregisterReceiver () ;
	}
	
	//Catch Recommendation list on Intent Action 
	public class RecommendationReciever extends BroadcastReceiver {
		
		public void onReceive(Context context, Intent intent) {

			ArrayList<AMRData> lists = intent.getParcelableArrayListExtra("AMR Recommend List") ;

			try {
				for (AMRData list : lists) {
	
					try {
						Log.d (TAG + "track_id : ", list.getTrackID()) ;
						track_id.add (list.getTrackID()) ;
					} catch (NullPointerException e) {}
					
					try {
						Log.d (TAG + "user_id : ", list.getUserID()) ;
					} catch (NullPointerException e) {}
					try {
						Log.d (TAG + "artist : ", list.getArtist()) ;
					} catch (NullPointerException e) {}
					try {
						Log.d (TAG + "title : ", list.getTitle()) ;
					} catch (NullPointerException e) {}
					try {
						Log.d (TAG + "album : ", list.getAlbum()) ;
					} catch (NullPointerException e) {}
					try {
						Log.d (TAG + "score : ", list.getScore()) ;
					} catch (NullPointerException e) {}
					try {
						Log.d (TAG + "timeStamp : ", list.getTimeStamp()) ;
					} catch (NullPointerException e) {}
					try {
						Log.d (TAG + "content : ", list.getContent()) ;
					} catch (NullPointerException e) {}
					
					try {
						for (int i = 0 ; i < list.getTrack().size() ; i++) {
							try {
								Log.d (TAG + "track track_id : ", list.getTrack().get(i).getTrackID()) ;
							} catch (NullPointerException e) {}
							try {
								Log.d (TAG + "track artist : ", list.getTrack().get(i).getArtist()) ;
							} catch (NullPointerException e) {}
							try {
								Log.d (TAG + "track title : ", list.getTrack().get(i).getTitle()) ;
							} catch (NullPointerException e) {}
						}
					} catch (NullPointerException e) {}
				}
			} catch (Exception e) {}
		}
	}
}
