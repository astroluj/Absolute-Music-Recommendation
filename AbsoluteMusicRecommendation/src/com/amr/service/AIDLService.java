package com.amr.service;


import com.amr.aidl.amrAIDL;
import com.amr.thread.NetworkThread;
import com.amr.util.util;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;

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
			public void getAnalyzeToRecommendLists (String recvAction, String analyzeData, int count) throws RemoteException {
			}

			// Keyword Searching
			@Override
			public void getKeywordToRecommendLists (String recvAction, String artist,
					String title, int count) throws RemoteException {
				
				try {
					startNetworkThread(recvAction, artist, title, count) ;
					
				} catch (Exception e) {
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

	private void startNetworkThread (String recvAction, String artist, String title, int count) {
		networkThread =new NetworkThread (getApplicationContext(), networkHandlerCallback,
				recvAction, artist, title, count) ;
		
		networkThread.setDaemon(true) ;
		networkThread.start() ;
	}
	
	private void releaseNetworkThread () {
		try {
			networkThread.interrupt() ;
		} catch (Exception e) {
			networkThread = null ;
		}
	}
	
	private class NetworkHandlerCallback implements Handler.Callback {
		public boolean handleMessage (Message msg) {
			 
			switch (msg.what) {
			case util.SEND_RECOMMEND_LIST : 
				try {
					
					releaseNetworkThread() ;
				} catch (Exception e) {}
				
				break ;
				
			case util.CALL_FEATURE :
				try {
					
				} catch (Exception e) {}
				
				break ;
				
			case util.NOT_FOUND_RECOMMEND :
				try {
					
				} catch (Exception e) {}
				
				break ;
			}
			
			return true ;
		}
	}
}
