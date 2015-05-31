package com.amr.service;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

import com.amr.aidl.amrAIDL;
import com.amr.network.json.PostJson;
import com.amr.util.MakeJson;
import com.amr.util.util;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

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
						ArrayList<BasicNameValuePair> jsonMsg = new ArrayList<BasicNameValuePair> () ; 
						jsonMsg.add (new BasicNameValuePair(
								util.DATA, MakeJson.keywordMakeJson(artist, title, util.START_INDEX, count))) ; 
						
						PostJson requestJson = new PostJson () ;
						requestJson.getRecommendLists(
								requestJson.sendData(jsonMsg, util.URL_SEARCH)) ;
						
						return true ;
					} catch (Exception e) {
						// return false is not exist music in Bonacell Server
						return false;
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
