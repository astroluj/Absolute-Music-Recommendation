package com.amr;

import java.util.ArrayList;

import com.amr.data.AMRData;
import com.amr.data.AMRRecommendRequestData;
import com.amr.data.UserData;
import com.amr.network.json.PostJson;
import com.amr.util.util;

import android.util.Log;

public class AMR {

	private boolean repeatFlag ;
	
	public AMR () {
		
		repeatFlag = false ;
	}
	
	// Keyword Searching
	public ArrayList<AMRData> getKeywordToRecommendLists (String uri, String artist, String title, int count) {

		Log.d (util.TAG, "called getKeywordToRecommendLists function") ;
		
		return calledBonacell(new AMRRecommendRequestData (
				null, null, artist, title, null, null,
				null, null,
				util.START_INDEX, count), util.MUSIC_SEARCH) ;
	}

	public ArrayList<AMRData> setUserRegistered(String userID) {

		Log.d (util.TAG, "called setUserRegistered function") ;
		
		return calledBonacell(new AMRRecommendRequestData (
				null, null, null, null,  null, null,
				new UserData (userID, util.USER_ID), null,
				null, null), util.USER_REGISTER) ;
	}

	public ArrayList<AMRData> setUserUnregistered(String userID) {

		Log.d (util.TAG, "called setUserUnregistered function") ;
		
		return calledBonacell(new AMRRecommendRequestData (
				null, null, null, null, null, null,
				new UserData (userID, util.USER_ID, true), null,
				null, null), util.USER_REMOVE) ;
	}

	public ArrayList<AMRData> setUserPlay(String uri, String userID,
			String artist, String title, String album) {

		Log.d (util.TAG, "called setUserPlay function") ;

		return calledBonacell(new AMRRecommendRequestData (
				null, null, artist, title, album, null,
				new UserData (userID, util.USER_ID), null,
				null, null), util.USER_PLAY) ;
	}

	public ArrayList<AMRData> getMusicViewUserList(String trackID, int start, int count) {
		
		Log.d (util.TAG, "called getMusicViewUserList function") ;

		return calledBonacell(new AMRRecommendRequestData (
				null, trackID, null, null, null, null,
				null, null,
				start, count), util.MUSIC_USERS) ;
	}

	public ArrayList<AMRData> getMatchedViewList(String reqUserID, String lookUpUserID, int start, int count) {

		Log.d (util.TAG, "called getMatchedViewList function") ;
			
		return calledBonacell(new AMRRecommendRequestData (
				null, null, null, null, null, null,
				new UserData (reqUserID, util.REQ_USER_ID), new UserData (lookUpUserID, util.LOOK_UP_USER_ID),
				start, count), util.USER_SHARED_HISTORY) ;
	}

	public ArrayList<AMRData> getUnmatchedViewList(String reqUserID, String lookUpUserID, int start, int count)  {

		Log.d (util.TAG, "called getUnmachedViewList function") ;

		return calledBonacell(new AMRRecommendRequestData (
				null, null, null, null, null, null,
				new UserData (reqUserID, util.REQ_USER_ID), new UserData (lookUpUserID, util.LOOK_UP_USER_ID),
				null, null), util.USER_NONSHARED_HISTORY) ;
	}

	public ArrayList<AMRData> setMate(String matingUserID, String matedUserID) {

		Log.d (util.TAG, "called setMate function") ;

		return calledBonacell(new AMRRecommendRequestData (
				null, null, null, null, null, null,
				new UserData (matingUserID, util.MATING_USER_ID), new UserData (matedUserID, util.MATED_USER_ID),
				null, null), util.USER_MATE) ;
	}

	public ArrayList<AMRData> setUnmate(String unmatingUserID, String unmatedUserID) {
		
		Log.d (util.TAG, "called setUnmate function") ;

		return calledBonacell(new AMRRecommendRequestData (
				null, null, null, null, null, null,
				new UserData (unmatingUserID, util.UNMATING_USER_ID), new UserData (unmatedUserID, util.UNMATED_USER_ID),
				null, null), util.USER_UNMATE) ;
	}

	public ArrayList<AMRData> getMateList(String userID,
			int start, int count) {
		
		Log.d (util.TAG, "called getMateList function") ;

		return calledBonacell(new AMRRecommendRequestData (
				null, null, null, null, null, null,
				new UserData (userID, util.USER_ID), null,
				start, count), util.USER_MATE_LIST) ;
	}
	
	public ArrayList<AMRData> reviewWrite (String userID, String trackID, String content) {

		Log.d (util.TAG, "called reviewWrite function") ;

		return calledBonacell(new AMRRecommendRequestData (
				null, trackID, null, null, null, content,
				new UserData (userID, util.USER_ID), null,
				null, null), util.REVIEW_WRITE) ;
	}

	public ArrayList<AMRData> getMusicReview(String trackID, int start, int count) {

		Log.d (util.TAG, "called getMusicReview function") ;

		return calledBonacell(new AMRRecommendRequestData (
				null, trackID, null, null, null, null,
				null, null,
				start, count), util.MUSIC_REVIEW) ;
	}

	public ArrayList<AMRData> getUserReview(String userID, int start, int count) {

		Log.d (util.TAG, "called getUserReview function") ;

		return calledBonacell(new AMRRecommendRequestData (
				null, null, null, null, null, null,
				new UserData(userID, util.USER_ID), null,
				start, count), util.USER_REVIEW) ;
	}
	
	public ArrayList<AMRData> getUserMatesViewList(String userID, int start, int count) {
		
		Log.d (util.TAG, "called getUserMatedViewList function") ;

		return calledBonacell(new AMRRecommendRequestData (
				null, null, null, null, null, null,
				new UserData (userID, util.USER_ID), null,
				start, count), util.USER_FEED) ;
	}
	
	// Network Process
	private ArrayList<AMRData> calledBonacell (AMRRecommendRequestData amrData, int CASE) {
		
		ArrayList<AMRData> responsePaserDataArray = null ;
		PostJson postJson = new PostJson () ;
		
		// Get Bonacell DB Track_ID
		// POST Request
		switch (CASE) {
		case util.USER_REGISTER :
		case util.USER_REMOVE :
			// /user/register or remove
			responsePaserDataArray = postJson.getResponseArrays(
					postJson.sendData(
							postJson.postRequest(amrData), (amrData.getUserData().getIsRemove() == true) ?
											util.URL_USER_REMOVE
											: util.URL_USER_REGISTER)) ;
			
			break ;
		case util.MUSIC_SIMILAR :
			// /music/similar Request
			responsePaserDataArray = postJson.getResponseArrays(
					postJson.sendData(
							postJson.postRequest(amrData), util.URL_SIMILAR)) ;
			
			break ;
		case util.MUSIC_SEARCH :
		case util.MUSIC_RECOMMEND :
			try {
				// /music/search
				AMRData responsePaserData = postJson.getResponseArrays(
						postJson.sendData(
								postJson.postRequest(amrData),
										util.URL_SEARCH)).get(0) ;
				
				amrData.setTrackID(responsePaserData.getTrackID()) ;
				// Recommend Lists
				// /music/recommend
				responsePaserDataArray = postJson.getResponseArrays(
						postJson.sendData(
								postJson.postRequest(amrData), util.URL_RECOMMEND)) ;
				
			} catch (IndexOutOfBoundsException e) {
				// dis-exist in Bonacell DB
				// feature 가능 하면 feature 추출
				if (amrData.getFeature () != null && !repeatFlag) {
					
					repeatFlag = true ;
					
				}
				// 가능 못하면 DB에서 찾을 수 없음
				else repeatFlag = false ;
			}
			
			break ;
		case util.USER_PLAY :
			// /user/play
			responsePaserDataArray = postJson.getResponseArrays(
					postJson.sendData(
							postJson.postRequest(amrData), util.URL_USER_PLAY)) ;
			
			break ;
		case util.USER_SHARED_HISTORY :
			// /user/shared_history
			responsePaserDataArray = postJson.getResponseArrays(
					postJson.sendData(
							postJson.postRequest(amrData), util.URL_USER_SHARED_HISTORY)) ;
			
			break ;
		case util.USER_NONSHARED_HISTORY :
			// /user/nonshared_history
			responsePaserDataArray = postJson.getResponseArrays(
					postJson.sendData(
							postJson.postRequest(amrData), util.URL_USER_NON_SHARED_HISTORY)) ;
			
			break ;
		case util.MUSIC_USERS :
			// /music/user
			responsePaserDataArray = postJson.getResponseArrays(
					postJson.sendData(
							postJson.postRequest(amrData), util.URL_MUSIC_USER)) ;
			
			break ;
		case util.USER_MATE :
			// /user/mate
			responsePaserDataArray = postJson.getResponseArrays(
					postJson.sendData(
							postJson.postRequest(amrData), util.URL_USER_MATE)) ;
			
			break ;
		case util.USER_UNMATE :
			// /user/unmate
			responsePaserDataArray = postJson.getResponseArrays(
					postJson.sendData(
							postJson.postRequest(amrData), util.URL_USER_UNMATE)) ;
			
			break ;
		case util.USER_MATE_LIST :
			// /user/matelist
			responsePaserDataArray = postJson.getResponseArrays(
					postJson.sendData(
							postJson.postRequest(amrData), util.URL_USER_MATE_LIST)) ;
			
			break ;
		case util.REVIEW_WRITE :
			// /review/write
			responsePaserDataArray = postJson.getResponseArrays(
					postJson.sendData(
							postJson.postRequest(amrData), util.URL_REVIEW_WRITE)) ;
			
			break ;
		case util.MUSIC_REVIEW :
			// /music/review
			responsePaserDataArray = postJson.getResponseArrays(
					postJson.sendData(
							postJson.postRequest(amrData), util.URL_MUSIC_REVIEW)) ;
			
			break ;
		case util.USER_REVIEW :
			// /user/review
			responsePaserDataArray = postJson.getResponseArrays(
					postJson.sendData(
							postJson.postRequest(amrData), util.URL_USER_REVIEW)) ;
			
			break ;
		case util.USER_FEED :
			// /user/feed
			responsePaserDataArray = postJson.getResponseArrays(
					postJson.sendData(
							postJson.postRequest(amrData), util.URL_USER_FEED)) ;
			
			break ;
		}
		
		return responsePaserDataArray ;
	}
}
