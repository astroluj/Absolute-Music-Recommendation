package com.example.testamr;

import java.util.ArrayList;

import com.amr.aidl.amrAIDL;
import com.amr.data.AMRData;

import android.support.v7.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
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
	
	// AMRAIDL
	private amrAIDL aidlAMRService ;
	private ServiceConnection amrServiceConn ;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// TODO : 필수
		// AIDL SET
		serviceConnection () ;
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
				aidlAMRService.setUserRegistered(RECV_ACTION, "test") ;
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
				break ;
				
			case R.id.music_review:
				break ;
				
			case R.id.user_review:
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
	
	//Catch Recommendation list on Intent Action 
	public class RecommendationReciever extends BroadcastReceiver {
		
		public void onReceive(Context context, Intent intent) {

			ArrayList<AMRData> lists = intent.getParcelableArrayListExtra("AMR Recommend List") ;

			for (AMRData list : lists) {

				Log.d (TAG + "track_id : ", list.getTrackID()) ;
				Log.d (TAG + "user_id : ", list.getUserID()) ;
				Log.d (TAG + "artist : ", list.getArtist()) ;
				Log.d (TAG + "title : ", list.getTitle()) ;
				Log.d (TAG + "album : ", list.getAlbum()) ;
				Log.d (TAG + "score : ", list.getScore()) ;
				Log.d (TAG + "timeStamp : ", list.getTimeStamp()) ;
				Log.d (TAG + "content : ", list.getContent()) ;
				for (int i = 0 ; i < list.getTrack().size() ; i++) {
					Log.d (TAG + "track track_id : ", list.getTrack().get(i).getTrackID()) ;
					Log.d (TAG + "track artist : ", list.getTrack().get(i).getArtist()) ;
					Log.d (TAG + "track title : ", list.getTrack().get(i).getTitle()) ;
				}
			}
		}
	}
}
