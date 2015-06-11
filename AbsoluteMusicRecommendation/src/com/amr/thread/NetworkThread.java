package com.amr.thread;

import java.util.ArrayList;

import com.amr.data.AMRRecommendRequestData;
import com.amr.data.AMRData;
import com.amr.data.UserData;
import com.amr.network.json.PostJson;
import com.amr.util.util;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

public class NetworkThread extends Thread {

	// Custom Class
	private UserData userData ;
	private PostJson postJson ;
	private ArrayList<AMRData> responsePaserDataArray ;
	
	private Context context ;
	private Handler handler ;
	
	private String recvAction, feature, track_id, artist, title, album ;
	private Integer startIndex, count ;
	
	public NetworkThread (Context context, Handler handler,
			String recvAction, AMRRecommendRequestData amrData) {
		
		this.postJson = new PostJson () ;
		
		this.context = context ;
		this.handler = handler ;
	
		// String
		this.recvAction = recvAction ;
		this.feature = amrData.getFeature() ;
		this.track_id = amrData.getTrackID() ;
		this.artist = amrData.getArtist() ;
		this.title = amrData.getTitle() ;
		this.album = amrData.getAlbum() ;
		
		this.userData = amrData.getUserData() ;
		
		// Integer
		this.startIndex = amrData.getStartIndex() ;
		this.count = amrData.getCount() ;
	}
	
	public void run () {
		try {
			// Get Bonacell DB Track_ID
			// POST Request
			// /user/play
			if (album != null) {
				// /user/play
				responsePaserDataArray = postJson.getResponseArrays(
						postJson.sendData(
								postJson.postRequest(new AMRRecommendRequestData (feature, track_id,
										artist, title, album, userData,
										startIndex, count)), util.URL_USER_PLAY)) ;
			}
			//  /user/register or remove
			else if (userData != null) {
				// /user/register or remove
				responsePaserDataArray = postJson.getResponseArrays(
						postJson.sendData(
								postJson.postRequest(new AMRRecommendRequestData (feature, track_id,
										artist, title, album, userData,
										startIndex, count)), (userData.getIsRemove() == false) ?
												util.URL_USER_REMOVE
												: util.URL_USER_REGISTER)) ;
			}
			// /music/search and /music/recommend Request
			else if (feature == null) {
				try {
					// /music/search
					AMRData responsePaserData = postJson.getResponseArrays(
							postJson.sendData(
									postJson.postRequest(new AMRRecommendRequestData(feature, track_id,
											artist, title, album, userData,
											startIndex, util.TRACK_ID_COUNT)),
											util.URL_SEARCH)).get(0) ;
					
					// Recommend Lists
					// /music/recommend
					responsePaserDataArray = postJson.getResponseArrays(
							postJson.sendData(
									postJson.postRequest(new AMRRecommendRequestData (feature, responsePaserData.getTrackID(),
											artist, title, album, userData,
											startIndex, count)), util.URL_RECOMMEND)) ;
					
				} catch (IndexOutOfBoundsException e) {
					// dis-exist in Bonacell DB
					// feature 가능 하면 feature 추출
					if (feature != null) {
						
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
			}
			// /music/similar Request
			else {
				// /music/similar
				responsePaserDataArray = postJson.getResponseArrays(
						postJson.sendData(
								postJson.postRequest(new AMRRecommendRequestData (feature, track_id,
										artist, title, album, userData,
										startIndex, count)), util.URL_SIMILAR)) ;
			}
				
			// Send BroadCast Recommend Lists
			if (recvAction != null) {
				sendBroadCast (recvAction, responsePaserDataArray) ;
				Log.d (util.TAG + "SendBraodCast : ", "Search Recommend Lists") ;
				handler.sendEmptyMessage(util.RECOMMEND_LIST) ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sendBroadCast (String recvAction, ArrayList<AMRData> responsePaserDataArray) {
		
		Intent intent = new Intent (recvAction) ;
		intent.putParcelableArrayListExtra(util.AMR, responsePaserDataArray) ;
		context.sendBroadcast(intent);
	}
}
