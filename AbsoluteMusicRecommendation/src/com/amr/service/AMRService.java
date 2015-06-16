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
			public void getKeywordToRecommendLists (String recvAction, String uri,
					String artist, String title, int count) throws RemoteException {

				Log.d (util.TAG, "called getKeywordToRecommendLists function") ;
				try {
					if (networkThread != null)
						releaseNetworkThread();
					
					startNetworkThread(recvAction,
							new AMRRecommendRequestData (null, null, artist, title, null, null,
							null, null,
							util.START_INDEX, count), util.MUSIC_SEARCH) ;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void setUserRegistered(String recvAction, String userID)
					throws RemoteException {

				Log.d (util.TAG, "called setUserRegistered function") ;
				try {
					if (networkThread != null)
						releaseNetworkThread();
					
					startNetworkThread(recvAction,
							new AMRRecommendRequestData (null, null, null, null,  null, null,
							new UserData (userID, util.USER_ID), null,
							null, null), util.USER_REGISTER) ;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void setUserUnregistered(String recvAction, String userID)
					throws RemoteException {

				Log.d (util.TAG, "called setUserUnregistered function") ;
				try {
					if (networkThread != null)
						releaseNetworkThread();
					
					startNetworkThread(recvAction,
							new AMRRecommendRequestData (null, null, null, null, null, null,
							new UserData (userID, util.USER_ID, true), null,
							null, null), util.USER_REMOVE) ;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void setUserPlay(String recvAction, String uri, String userID,
					String artist, String title, String album)
					throws RemoteException {

				Log.d (util.TAG, "called setUserPlay function") ;
				try {
					if (networkThread != null)
						releaseNetworkThread();
					
					startNetworkThread(recvAction,
							new AMRRecommendRequestData (null, null, artist, title, album, null,
							new UserData (userID, util.USER_ID), null,
							null, null), util.USER_PLAY) ;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void getMusicViewUserList(String recvAction, String trackID,
					int start, int count) throws RemoteException {
				
				Log.d (util.TAG, "called getMusicViewUserList function") ;
				try {
					if (networkThread != null)
						releaseNetworkThread();
					
					startNetworkThread(recvAction,
							new AMRRecommendRequestData (null, trackID, null, null, null, null,
							null, null,
							start, count), util.USER_PLAY) ;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void getMatchedViewList(String recvAction, String reqUserID,
					String lookUpUserID, int start, int count)
					throws RemoteException {

				Log.d (util.TAG, "called getMatchedViewList function") ;
				try {
					if (networkThread != null)
						releaseNetworkThread();
					
					startNetworkThread(recvAction,
							new AMRRecommendRequestData (null, null, null, null, null, null,
							new UserData (reqUserID, util.REQ_USER_ID), new UserData (lookUpUserID, util.LOOK_UP_USER_ID),
							start, count), util.USER_PLAY) ;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void getUnmatchedViewList(String recvAction,
					String reqUserID, String lookUpUserID, int start, int count)
					throws RemoteException {

				Log.d (util.TAG, "called getUnmachedViewList function") ;
				try {
					if (networkThread != null)
						releaseNetworkThread();
					
					startNetworkThread(recvAction,
							new AMRRecommendRequestData (null, null, null, null, null, null,
							new UserData (reqUserID, util.REQ_USER_ID), new UserData (lookUpUserID, util.LOOK_UP_USER_ID),
							null, null), util.USER_PLAY) ;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void setMate(String recvAction, String matingUserID,
					String matedUserID) throws RemoteException {

				Log.d (util.TAG, "called setMate function") ;
				try {
					if (networkThread != null)
						releaseNetworkThread();
					
					startNetworkThread(recvAction,
							new AMRRecommendRequestData (null, null, null, null, null, null,
							new UserData (matingUserID, util.MATING_USER_ID), new UserData (matedUserID, util.MATED_USER_ID),
							null, null), util.USER_PLAY) ;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void setUnmate(String recvAction, String unmatingUser_id,
					String unmatedUserID) throws RemoteException {
				Log.d (util.TAG, "called setUnmate function") ;
				try {
					if (networkThread != null)
						releaseNetworkThread();
					
					startNetworkThread(recvAction,
							new AMRRecommendRequestData (null, null, null, null, null, null,
							new UserData (unmatingUser_id, util.UNMATING_USER_ID), new UserData (unmatedUserID, util.UNMATED_USER_ID),
							null, null), util.USER_PLAY) ;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void reviewWrite (String recvAction, String userID,
					String trackID, String content) throws RemoteException {

				Log.d (util.TAG, "called reviewWrite function") ;
				try {
					if (networkThread != null)
						releaseNetworkThread();
					
					startNetworkThread(recvAction,
							new AMRRecommendRequestData (null, trackID, null, null, null, content,
							new UserData (userID, util.USER_ID), null,
							null, null), util.REVIEW_WRITE) ;
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}

			@Override
			public void getMusicReview(String recvAction, String trackID, int start, int count)
					throws RemoteException {

				Log.d (util.TAG, "called getMusicReview function") ;
				try {
					if (networkThread != null)
						releaseNetworkThread();
					
					startNetworkThread(recvAction,
							new AMRRecommendRequestData (null, trackID, null, null, null, null,
							null, null,
							start, count), util.MUSIC_REVIEW) ;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void getUserReview(String recvAction, String userID, int start, int count)
					throws RemoteException {

				Log.d (util.TAG, "called getUserReview function") ;
				try {
					if (networkThread != null)
						releaseNetworkThread();
					
					startNetworkThread(recvAction,
							new AMRRecommendRequestData (null, null, null, null, null, null,
							new UserData(userID, util.USER_ID), null,
							start, count), util.USER_REVIEW) ;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void getUserMatesViewList(String recvAction, String userID,
					int start, int count) throws RemoteException {
				
				Log.d (util.TAG, "called getUserMatedViewList function") ;
				try {
					if (networkThread != null)
						releaseNetworkThread();
					
					startNetworkThread(recvAction,
							new AMRRecommendRequestData (null, null, null, null, null, null,
							new UserData (userID, util.USER_ID), null,
							start, count), util.USER_PLAY) ;
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
