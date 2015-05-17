package com.amr.service;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

import com.amr.aidl.amrAIDL;
import com.amr.communication.json.RequestJson;
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
			public boolean getAnalyzeToRecommendLists (String analyzeData) throws RemoteException {
				return false;
			}

			@Override
			public boolean getInformationToRecommendLists (String artist,
					String title, String recvAction) throws RemoteException {
				
				RequestJson requestJson = new RequestJson () ;
				try {
					// 전송 할 데이터
					ArrayList<BasicNameValuePair> post = new ArrayList<BasicNameValuePair>();
					post.add(new BasicNameValuePair("track_id",artist));	// 사번
					post.add(new BasicNameValuePair("count", title)) ;
					
					requestJson.getInformationToRecommendListsJson(requestJson.sendData(post, util.URL)) ;
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				// return false is not exist music in Bonacell Server
				return false;
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
