package com.amr.service;


import com.amr.aidl.amrAIDL;
import com.amr.thread.NetworkThread;
import com.amr.util.util;
import com.arm.data.AMRData;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

public class AIDLService extends Service {

	amrAIDL.Stub amrService  ;	// AIDLService
	
	private NetworkThread networkThread ;
	private Handler networkHandlerCallback = new Handler (new NetworkHandlerCallback()) ;
	
	public void onCreate () {
		super.onCreate () ;
		
		// stub로 연결하여 미리 작성한 interface대로 작성
		amrService =new amrAIDL.Stub() {

			@Override
			// Client Analyzation music
			public void getAnalyzeToRecommendLists (String recvAction, String mediaPath, int count) throws RemoteException {
				
				// 주어진 mediaPath를 통하여 음악의 Feature 구하기
				String feature = null ;
				try {
					startNetworkThread(recvAction, new AMRData (feature, null, null, null, null, count)) ;
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// Keyword Searching
			@Override
			public void getKeywordToRecommendLists (String recvAction, String artist, String title, int count) throws RemoteException {

				try {
					startNetworkThread(recvAction, new AMRData (null, null, artist, title, util.START_INDEX, count)) ;
					
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
	private void startNetworkThread (String recvAction, AMRData amrData) {
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
				
			case util.CALL_FEATURE :
				try {
					Log.d (util.TAG + "Handler Come : ", "CALL_FEATURE") ;
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
