package com.amr.thread;

import java.util.ArrayList;

import com.amr.network.json.PostJson;
import com.amr.util.util;
import com.arm.data.AMRData;
import com.arm.data.RecommendData;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

public class NetworkThread extends Thread {

	private PostJson postJson ;
	private ArrayList<RecommendData> responsePaserDataArray ;
	
	private Context context ;
	private Handler handler ;
	
	private String recvAction, feature, track_id, artist, title ;
	private Integer startIndex, count ;
	
	public NetworkThread (Context context, Handler handler,
			String recvAction, AMRData amrData) {
		
		this.postJson = new PostJson () ;
		
		this.context = context ;
		this.handler = handler ;
	
		// String
		this.recvAction = recvAction ;
		this.feature = amrData.getFeature() ;
		this.track_id = amrData.getTrackID() ;
		this.artist = amrData.getArtist() ;
		this.title = amrData.getTitle() ;
		
		// Integer
		this.startIndex = amrData.getStartIndex() ;
		this.count = amrData.getCount() ;
	}
	
	public NetworkThread (Context context, Handler handler,
			String recvAction, String feature, String track_id,
			String artist, String title, Integer startIndex, Integer count) {
		
		this.postJson = new PostJson () ;
		
		this.context = context ;
		this.handler = handler ;
	
		// String
		this.recvAction = recvAction ;
		this.feature = feature ;
		this.track_id = track_id ;
		this.artist = artist ;
		this.title = title ;
		
		// Integer
		this.startIndex = startIndex ;
		this.count  = count ;
	}
	
	public void run () {
		try {
			// Get Bonacell DB Track_ID
			try {
				// POST Request
				// /music/search and /music/recommend Request
				if (feature == null) {
					// /music/search
					RecommendData responsePaserData = postJson.getResponseArrays(
							postJson.sendData(
									postJson.postRequest(new AMRData(null, null,
											artist, title,
											startIndex, util.TRACK_ID_COUNT)),
											util.URL_SEARCH)).get(0) ;
					
					// Recommend Lists
					// /music/recommend
					responsePaserDataArray = postJson.getResponseArrays(
							postJson.sendData(
									postJson.postRequest(new AMRData (null, responsePaserData.getTrackID(),
											null, null,
											null, count)), util.URL_RECOMMEND)) ;
				}
				// /music/similar Request
				else {
					// /music/similar
					responsePaserDataArray = postJson.getResponseArrays(
							postJson.sendData(
									postJson.postRequest(new AMRData (feature, null,
											null, null,
											null, count)), util.URL_SIMILAR)) ;
				}
				
				// Send BroadCast Recommend Lists
				sendBroadCast (recvAction, responsePaserDataArray) ;
				Log.d (util.TAG + "SendBraodCast : ", "Search Recommend Lists") ;
				handler.sendEmptyMessage(util.RECOMMEND_LIST) ;
			} catch (IndexOutOfBoundsException e) {
				// dis-exist in Bonacell DB
				// feature 가능 하면 feature 추출
				if (feature == null) {
					
					handler.sendEmptyMessage(util.ANALYZE_FEATURE) ;
				}
				// 가능 못하면 DB에서 찾을 수 없다고 알림
				else {
					// Send BroadCast Not found Lists
					sendBroadCast (recvAction, responsePaserDataArray) ;
					Log.d (util.TAG + "SendBraodCast : ", "Not found Recommend Lists") ;
					handler.sendEmptyMessage(util.NOT_FOUND_RECOMMEND) ;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sendBroadCast (String recvAction, ArrayList<RecommendData> responsePaserDataArray) {
		
		Intent intent = new Intent (recvAction) ;
		intent.putParcelableArrayListExtra(util.AMR, responsePaserDataArray) ;
		context.sendBroadcast(intent);
	}
}
