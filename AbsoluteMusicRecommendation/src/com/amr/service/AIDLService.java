package com.amr.service;

import java.util.ArrayList;

import com.amr.aidl.amrAIDL;
import com.amr.network.json.ResponsePaserData;
import com.amr.network.json.PostJson;
import com.amr.util.util;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class AIDLService extends Service {

	amrAIDL.Stub amrService  ;	// AIDLService
	
	public void onCreate () {
		super.onCreate () ;
		
		// stub로 연결하여 미리 작성한 interface대로 작성
		amrService =new amrAIDL.Stub() {

			@Override
			// Client Analyzation music
			public boolean getAnalyzeToRecommendLists (String recvAction, String analyzeData, int count) throws RemoteException {
				return false;
			}

			// Keyword Searching
			@Override
			public boolean getKeywordToRecommendLists (String recvAction, String artist,
					String title, int count) throws RemoteException {
				
				try {
					PostJson postJson = new PostJson () ;
					
					// Get Bonacell DB Track_ID
					try {
						// POST Request
						ResponsePaserData ResponsePaserData = postJson.getResponseArrays(
								postJson.sendData(
										postJson.postRequest(null, null,
												artist, title,
												util.START_INDEX, util.TRACK_ID_COUNT),
												util.URL_SEARCH)).get(0) ;
						
						// Recommend Lists
						ArrayList<ResponsePaserData> ResponsePaserDataArray = postJson.getResponseArrays(
								postJson.sendData(
										postJson.postRequest(null, ResponsePaserData.getTrackID(),
												null, null, null, count), util.URL_RECOMMEND)) ;

						// Send BroadCast Recommend Lists
						Intent intent = new Intent (recvAction) ;
						intent.putParcelableArrayListExtra(util.AMR, ResponsePaserDataArray) ;
						sendBroadcast(intent);
						Log.d (util.TAG + "SendBraodCast : ", "Search Recommend Lists") ;
						
						return true ;
					} catch (NullPointerException e) {
						// dis-exist in Bonacell DB
						// feature 가능 하면 feature 추출
						// 가능 못하면 DB에서 찾을 수 없다고 알림
						
						return false ;
					}
				} catch (Exception e) {
					return false ;
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

}
