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
							new AMRRecommendRequestData (null, null, artist, title, null,
							null,
							util.START_INDEX, count)) ;
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
							new AMRRecommendRequestData (null, null, null, null,  null,
							new UserData (user_id, true),
							null, null)) ;
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
							new AMRRecommendRequestData (null, null, null, null, null,
							new UserData (user_id, false),
							null, null)) ;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void setUserPlay(String recvAction, String user_id,
					String artist, String title, String album)
					throws RemoteException {

				Log.d (util.TAG, "called setUserPlay function") ;
				try {
					if (networkThread != null)
						releaseNetworkThread();
					
					startNetworkThread(recvAction,
							new AMRRecommendRequestData (null, null, artist, title, album, 
							new UserData (user_id, true),
							null, null)) ;
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
	private void startNetworkThread (String recvAction, AMRRecommendRequestData amrData) {
		networkThread =new NetworkThread (getApplicationContext(), networkHandlerCallback,
				recvAction, amrData) ;
		
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
