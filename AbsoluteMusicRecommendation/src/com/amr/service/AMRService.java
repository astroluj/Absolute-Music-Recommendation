package com.amr.service;


import com.amr.aidl.amrAIDL;
import com.amr.data.AMRRecommendRequestData;
import com.amr.data.UserData;
import com.amr.thread.NetworkThread;
import com.amr.util.util;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

public class AMRService extends Service {

	amrAIDL.Stub amrService  ;	// AIDLService
	
	private NetworkThread networkThread ;
	private Handler networkHandlerCallback = new Handler (new NetworkHandlerCallback()) ;
	
	public void onCreate () {
		super.onCreate () ;
		
		// stub로 연결하여 미리 작성한 interface대로 작성
		amrService =new amrAIDL.Stub() {

			// Keyword Searching
			@Override
			public void getKeywordToRecommendLists (String uri, String recvAction, String artist, String title, int count) throws RemoteException {

				Log.d (util.TAG, "called getKeywordToRecommendLists function") ;
				try {
					if (networkThread != null)
						releaseNetworkThread();
					
					startNetworkThread(recvAction,
							new AMRRecommendRequestData (null, null, artist, title, null, null,
							null,
							util.START_INDEX, count), util.MUSIC_SEARCH) ;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void setUserRegistered(String recvAction, String user_id)
					throws RemoteException {

				Log.d (util.TAG, "called setUserRegistered function") ;
				try {
					if (networkThread != null)
						releaseNetworkThread();
					
					startNetworkThread(recvAction,
							new AMRRecommendRequestData (null, null, null, null,  null, null,
							new UserData (user_id, true),
							null, null), util.USER_REGISTER) ;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void setUserUnregistered(String recvAction, String user_id)
					throws RemoteException {

				Log.d (util.TAG, "called setUserUnregistered function") ;
				try {
					if (networkThread != null)
						releaseNetworkThread();
					
					startNetworkThread(recvAction,
							new AMRRecommendRequestData (null, null, null, null, null, null,
							new UserData (user_id, false),
							null, null), util.USER_REMOVE) ;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void setUserPlay(String recvAction, String uri, String user_id,
					String artist, String title, String album)
					throws RemoteException {

				Log.d (util.TAG, "called setUserPlay function") ;
				try {
					if (networkThread != null)
						releaseNetworkThread();
					
					startNetworkThread(recvAction,
							new AMRRecommendRequestData (null, null, artist, title, album, null,
							new UserData (user_id, true),
							null, null), util.USER_PLAY) ;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void setReview(String recvAction, String user_id,
					String track_id, String content) throws RemoteException {

				Log.d (util.TAG, "called setReview function") ;
				try {
					if (networkThread != null)
						releaseNetworkThread();
					
					startNetworkThread(recvAction,
							new AMRRecommendRequestData (null, track_id, null, null, null, content,
							new UserData (user_id, true),
							null, null), util.REVIEW_WRITE) ;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void getReview(String recvAction, String track_id, int start, int count)
					throws RemoteException {

				Log.d (util.TAG, "called getReview function") ;
				try {
					if (networkThread != null)
						releaseNetworkThread();
					
					startNetworkThread(recvAction,
							new AMRRecommendRequestData (null, track_id, null, null, null, null,
							null,
							start, count), util.MUSIC_REVIEW) ;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void getUserReview(String recvAction, String user_id, int start, int count)
					throws RemoteException {

				Log.d (util.TAG, "called getUserReview function") ;
				try {
					if (networkThread != null)
						releaseNetworkThread();
					
					startNetworkThread(recvAction,
							new AMRRecommendRequestData (null, null, null, null, null, null,
							new UserData(user_id, true),
							start, count), util.USER_REVIEW) ;
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		} ;
	}
	
	// 바인더를 통해 연결
	public IBinder onBind (Intent intent) {
		return amrService ;
	}
	
	public void onDestory () {
		super.onDestroy() ;
	}

	// NetworkThread Create & Release
	private void startNetworkThread (String recvAction,
			AMRRecommendRequestData amrData, int CASE) {
		
		networkThread =new NetworkThread (getApplicationContext(), networkHandlerCallback,
				recvAction, amrData, CASE) ;
		
		networkThread.setDaemon(true) ;
		networkThread.start() ;
	}
	
	private void releaseNetworkThread () {
		try {
			networkThread.interrupt() ;
		} catch (Exception e) {
			e.printStackTrace() ;
			networkThread = null ;
		}
	}
	
	// NetworkThread handler
	private class NetworkHandlerCallback implements Handler.Callback {
		public boolean handleMessage (Message msg) {
			 
			releaseNetworkThread() ;
			
			switch (msg.what) {
			case util.RECOMMEND_LIST : 
				try {
					Log.d (util.TAG + "Handler Come : ", "RECOMMEND_LIST") ;
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				break ;
				
			case util.ANALYZE_FEATURE :
				try {
					Log.d (util.TAG + "Handler Come : ", "ANALYZE_FEATURE") ;
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				break ;
				
			case util.NOT_FOUND_RECOMMEND :
				try {
					Log.d (util.TAG + "Handler Come : ", "NOT_FOUND_RECOMMEND") ;
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				break ;
			}
			
			return true ;
		}
	}
}
