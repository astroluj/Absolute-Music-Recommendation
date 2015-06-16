package com.amr.thread;

import java.util.ArrayList;

import com.amr.data.AMRRecommendRequestData;
import com.amr.data.AMRData;
import com.amr.network.json.PostJson;
import com.amr.util.util;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

public class NetworkThread extends Thread {

	// Custom Class
	private PostJson postJson ;
	private ArrayList<AMRData> responsePaserDataArray ;
	private AMRRecommendRequestData amrReqData ;
	
	private Context context ;
	private Handler handler ;
	
	private String recvAction ;
	private int CASE ;
	
	public NetworkThread (Context context, Handler handler,
			String recvAction, AMRRecommendRequestData amrReqData, int CASE) {
		
		this.postJson = new PostJson () ;
		
		this.context = context ;
		this.handler = handler ;
	
		this.amrReqData = amrReqData ;
		
		this.recvAction = recvAction ;
		
		this.CASE = CASE ;
	}
	
	public void run () {
		try {
			// Get Bonacell DB Track_ID
			// POST Request
			switch (CASE) {
			case util.USER_REGISTER :
			case util.USER_REMOVE :
				// /user/register or remove
				responsePaserDataArray = postJson.getResponseArrays(
						postJson.sendData(
								postJson.postRequest(amrReqData), (amrReqData.getUserData().getIsRemove() == true) ?
												util.URL_USER_REMOVE
												: util.URL_USER_REGISTER)) ;
				
				break ;
			case util.MUSIC_SIMILAR :
				// /music/similar Request
				responsePaserDataArray = postJson.getResponseArrays(
						postJson.sendData(
								postJson.postRequest(amrReqData), util.URL_SIMILAR)) ;
				
				break ;
			case util.MUSIC_SEARCH :
			case util.MUSIC_RECOMMEND :
				try {
					// /music/search
					AMRData responsePaserData = postJson.getResponseArrays(
							postJson.sendData(
									postJson.postRequest(amrReqData),
											util.URL_SEARCH)).get(0) ;
					
					amrReqData.setTrackID(responsePaserData.getTrackID()) ;
					// Recommend Lists
					// /music/recommend
					responsePaserDataArray = postJson.getResponseArrays(
							postJson.sendData(
									postJson.postRequest(amrReqData), util.URL_RECOMMEND)) ;
					
				} catch (IndexOutOfBoundsException e) {
					// dis-exist in Bonacell DB
					// feature 가능 하면 feature 추출
					if (amrReqData.getFeature () != null) {
						
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
				
				break ;
			case util.USER_PLAY :
				// /user/play
				responsePaserDataArray = postJson.getResponseArrays(
						postJson.sendData(
								postJson.postRequest(amrReqData), util.URL_USER_PLAY)) ;
				
				break ;
			case util.USER_SHARED_HISTORY :
				// /user/shared_history
				responsePaserDataArray = postJson.getResponseArrays(
						postJson.sendData(
								postJson.postRequest(amrReqData), util.URL_USER_SHARED_HISTORY)) ;
				
				break ;
			case util.USER_NONSHARED_HISTORY :
				// /user/nonshared_history
				responsePaserDataArray = postJson.getResponseArrays(
						postJson.sendData(
								postJson.postRequest(amrReqData), util.URL_USER_NON_SHARED_HISTORY)) ;
				
				break ;
			case util.MUSIC_USERS :
				// /music/user
				responsePaserDataArray = postJson.getResponseArrays(
						postJson.sendData(
								postJson.postRequest(amrReqData), util.URL_MUSIC_USER)) ;
				
				break ;
			case util.USER_MATE :
				// /user/mate
				responsePaserDataArray = postJson.getResponseArrays(
						postJson.sendData(
								postJson.postRequest(amrReqData), util.URL_USER_MATE)) ;
				
				break ;
			case util.USER_UNMATE :
				// /user/unmate
				responsePaserDataArray = postJson.getResponseArrays(
						postJson.sendData(
								postJson.postRequest(amrReqData), util.URL_USER_UNMATE)) ;
				
				break ;
			case util.USER_MATE_LIST :
				// /user/matelist
				responsePaserDataArray = postJson.getResponseArrays(
						postJson.sendData(
								postJson.postRequest(amrReqData), util.URL_USER_MATE_LIST)) ;
				
				break ;
			case util.REVIEW_WRITE :
				// /review/write
				responsePaserDataArray = postJson.getResponseArrays(
						postJson.sendData(
								postJson.postRequest(amrReqData), util.URL_REVIEW_WRITE)) ;
				
				break ;
			case util.MUSIC_REVIEW :
				// /music/review
				responsePaserDataArray = postJson.getResponseArrays(
						postJson.sendData(
								postJson.postRequest(amrReqData), util.URL_MUSIC_REVIEW)) ;
				
				break ;
			case util.USER_REVIEW :
				// /user/review
				responsePaserDataArray = postJson.getResponseArrays(
						postJson.sendData(
								postJson.postRequest(amrReqData), util.URL_USER_REVIEW)) ;
				
				break ;
			case util.USER_FEED :
				// /user/feed
				responsePaserDataArray = postJson.getResponseArrays(
						postJson.sendData(
								postJson.postRequest(amrReqData), util.URL_USER_FEED)) ;
				
				break ;
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
