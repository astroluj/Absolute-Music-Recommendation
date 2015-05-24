package com.amr.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import com.amr.aidl.amrAIDL;
import com.amr.network.json.MakeJson;
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
				
					PostJson requestJson = new PostJson () ;
					try {
						requestJson.getRecommendLists(new MakeJson (artist, title, util.START_INDEX, count), new URL (util.URL_SEARCH)) ;
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					
					try {
						ArrayList<BasicNameValuePair> post = new ArrayList<BasicNameValuePair> () ;
						post.add (new BasicNameValuePair(util.ARTIST, artist)) ;
						post.add (new BasicNameValuePair(util.TITLE, title)) ;
						post.add (new BasicNameValuePair(util.START, "" + util.START_INDEX)) ;
						post.add (new BasicNameValuePair(util.COUNT,  "" + count)) ;
						Log.d (util.TAG, post.toString()) ;
						requestJson.getRecommendLists(requestJson.sendData(post, util.URL_SEARCH)) ;
					} catch (ClientProtocolException e) {
						e.printStackTrace();
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
