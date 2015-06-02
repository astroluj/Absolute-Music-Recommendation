package com.amr.thread;

import java.util.ArrayList;

import com.amr.network.json.PostJson;
import com.amr.network.json.ResponsePaserData;
import com.amr.util.util;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

public class NetworkThread extends Thread {

	private PostJson postJson ;
	private ArrayList<ResponsePaserData> ResponsePaserDataArray ;
	
	private Context context ;
	private Handler handler ;
	
	private String recvAction, artist, title ;
	private int count ;
	
	public NetworkThread (Context context, Handler handler,
			String recvAction, String artist, String title, int count) {
		
		this.postJson = new PostJson () ;
		
		this.context = context ;
		this.handler = handler ;
	
		// String
		this.recvAction = recvAction ;
		this.artist = artist ;
		this.title = title ;
		
		// Integer
		this.count  = count ;
	}
	
	public void run () {
		try {
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
				ResponsePaserDataArray = postJson.getResponseArrays(
						postJson.sendData(
								postJson.postRequest(null, ResponsePaserData.getTrackID(),
										null, null, null, count), util.URL_RECOMMEND)) ;

				// Send BroadCast Recommend Lists
				Intent intent = new Intent (recvAction) ;
				intent.putParcelableArrayListExtra(util.AMR, ResponsePaserDataArray) ;
				context.sendBroadcast(intent);
				Log.d (util.TAG + "SendBraodCast : ", "Search Recommend Lists") ;
				
				handler.sendEmptyMessage(util.SEND_RECOMMEND_LIST) ;
			} catch (IndexOutOfBoundsException e) {
				// dis-exist in Bonacell DB
				// feature 가능 하면 feature 추출
				if (false) {
				}
				// 가능 못하면 DB에서 찾을 수 없다고 알림
				else {
					// Send BroadCast Not found Lists
					Intent intent = new Intent (recvAction) ;
					intent.putParcelableArrayListExtra(util.AMR, ResponsePaserDataArray) ;
					context.sendBroadcast(intent);
					Log.d (util.TAG + "SendBraodCast : ", "Not found Recommend Lists") ;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
