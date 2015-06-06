package com.music.player;

import java.util.ArrayList;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.amr.data.AMRRecommendResponseData;

//Catch Recommendation list on Intent Action 
public class RecommendationReciever extends BroadcastReceiver {
	
	public void onReceive(Context context, Intent intent) {
		
		// Check Action
		if (intent.getAction().equals(util.MUSIC_RECOMMEND_RESPONSE_FILTER)) {
			// Notification
			NotificationManager notiManager ;
			NotificationCompat.Builder notiBuilder ;
			NotificationCompat.InboxStyle inboxStyle ;
			
			// Notification Manager
			notiManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE) ;
			
			// Push shows music list
			// Notification Alert
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
			notiBuilder = new NotificationCompat.Builder(context) ;
			notiBuilder.setContentTitle("추천 리스트") ;
			notiBuilder.setSmallIcon(R.drawable.equalizer) ;
			notiBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.equalizer)) ;
			notiBuilder.setTicker("추천 음악") ;
			notiBuilder.setContentIntent(pendingIntent) ;
			notiBuilder.setAutoCancel(true) ;
			
			// API < API 11
			ArrayList<AMRRecommendResponseData> lists = intent.getParcelableArrayListExtra("AMR Recommend List") ;
			// Debug lists
			if (lists == null || lists.size() == 0) {
				
				notiBuilder.setContentText("비슷한 음악을 찾을 수 없습니다.") ;
			}
			else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
				String contentText = "" ;
				
				for (AMRRecommendResponseData list : lists) {
					contentText += "Artist : " + list.getArtist() ;
					if (list.getAlbum() != null) contentText += " Album : " + list.getAlbum() ;
					contentText += " Title : " + list.getTitle() + "\n" ;
					
				}
				notiBuilder.setContentText(contentText) ;
			}
			else {
				// Inbox Style Set
				inboxStyle = new NotificationCompat.InboxStyle(notiBuilder) ;
				for (AMRRecommendResponseData list : lists) {
					String contentText = "" ;
					contentText += "Artist : " + list.getArtist() ;
					if (list.getAlbum() != null) contentText += " Album : " + list.getAlbum() ;
					contentText += " Title : " + list.getTitle() ;
					
					inboxStyle.addLine(contentText) ;
				}
			}
			// Show notification
				notiManager.notify(0, notiBuilder.build()) ;
		}
	}
}
