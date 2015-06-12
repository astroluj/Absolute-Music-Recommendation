package com.amr.network.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.amr.data.AMRRecommendRequestData;
import com.amr.data.AMRData;
import com.amr.data.TrackData;
import com.amr.util.util;

public class ControllJson {

	// Music/Search Json
	// Music/Similar Json
	// Music/Recommend Json
	
	protected String makeJson (AMRRecommendRequestData amrData) {
		
		String jsonMsg = "" ;
		
		try {
			JSONObject jsonObject = new JSONObject() ;
			
			if (amrData.getFeature() != null) 
				jsonObject.put(util.FEATURE, amrData.getFeature()) ;
			
			if (amrData.getTrackID() != null)
				jsonObject.put(util.TRACK_ID, amrData.getTrackID()) ;
			
			if (amrData.getArtist() != null)
				jsonObject.put(util.ARTIST, amrData.getArtist()) ;
			
			if (amrData.getTitle() != null)
				jsonObject.put(util.TITLE, amrData.getTitle()) ;
			
			if (amrData.getAlbum() != null)
				jsonObject.put(util.ALBUM, amrData.getAlbum()) ;
			
			if (amrData.getContent() != null)
				jsonObject.put(util.CONTENT, amrData.getContent()) ;
			
			if (amrData.getUserData() != null) 
				jsonObject.put(util.USER_ID, amrData.getUserData().getUserID()) ;
			
			if (amrData.getStartIndex() != null) 
				jsonObject.put(util.START, amrData.getStartIndex()) ;
			
			if (amrData.getCount() != null)
				jsonObject.put(util.COUNT, amrData.getCount()) ;

			jsonMsg = jsonObject.toString() ;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jsonMsg ;
	}
	
	// Paser
	protected ArrayList<AMRData> responsePaser (String responseMsg) {
		
		try {
			// Init Response message Keys containers
			ArrayList<AMRData> paserArray = new ArrayList<AMRData> () ;
			
			JSONObject jsonObject = new JSONObject(responseMsg) ;
			JSONArray jsonArray = null ;
			try {
				jsonArray = jsonObject.getJSONArray(util.TRACKS) ;
			} catch (JSONException e) {
				try {
					jsonArray = jsonObject.getJSONArray(util.REVIEWS) ;
				} catch (JSONException e2) {}
			}

			for (int i = 0, size = jsonArray.length() ; i < size ; i++) {
				
				JSONObject paserData = jsonArray.getJSONObject(i) ;
				
				AMRData ResponsePaserData = new AMRData () ;
				
				// Keys Paser
				try {
					ResponsePaserData.setTrackID(paserData.getString(util.TRACK_ID)) ;
				} catch (JSONException e) {
					ResponsePaserData.setTrackID(null) ;
				}
				
				try {
					ResponsePaserData.setUserID (paserData.getString(util.USER_ID)) ;
				} catch (JSONException e) {
					ResponsePaserData.setUserID(null) ;
				}
				
				try {
					ResponsePaserData.setArtist(paserData.getString(util.ARTIST)) ;
				} catch (JSONException e) {
					ResponsePaserData.setArtist(null) ;
				}
				
				try {
					ResponsePaserData.setTitle(paserData.getString(util.TITLE)) ;
				} catch (JSONException e) {
					ResponsePaserData.setTitle(null) ;
				}
				
				try {
					ResponsePaserData.setAlbum(paserData.getString(util.ALBUM)) ;
				} catch (JSONException e) {
					ResponsePaserData.setAlbum(null) ;
				}
				
				try {
					ResponsePaserData.setURL(paserData.getString(util.URL)) ;
				} catch (JSONException e) {
					ResponsePaserData.setURL(null) ;
				}
				
				try {
					ResponsePaserData.setTimeStampe(paserData.getString(util.TIME_STAMP));
				} catch (JSONException e) {
					ResponsePaserData.setTimeStampe(null) ;
				}
				
				try {
					ResponsePaserData.setContent(paserData.getString(util.CONTENT));
				} catch (JSONException e) {
					ResponsePaserData.setContent(null) ;
				}
				
				try {
					ResponsePaserData.setScore(paserData.getString(util.SCORE)) ;
				} catch (JSONException e) {
					ResponsePaserData.setScore(null) ;
				}
				
				try {
					// Init Response message Keys containers
					ArrayList<TrackData> subPaserArray = new ArrayList<TrackData> () ;
					
					JSONArray subJsonArray = paserData.getJSONArray(util.TRACKS) ;
					
					for (int j = 0, subSize = jsonArray.length() ; j < subSize ; j++) {
						
						JSONObject subPaserData = subJsonArray.getJSONObject(i) ;
						
						TrackData trackData = new TrackData () ;
						
						try {
							trackData.setTrackID(subPaserData.getString(util.TRACK_ID)) ;
						} catch (JSONException e) {
							trackData.setTrackID(null) ;
						}
						
						try {
							trackData.setArtist(subPaserData.getString(util.ARTIST)) ;
						} catch (JSONException e) {
							trackData.setArtist(null) ;
						}
						
						try {
							trackData.setTitle(subPaserData.getString(util.TITLE)) ;
						} catch (JSONException e) {
							trackData.setTitle(null) ;
						}
						
						// add Pasing Datas
						subPaserArray.add(trackData) ;
					}
					
					ResponsePaserData.setTrack(subPaserArray) ;
				} catch (JSONException e) {
					ResponsePaserData.setTrack(null) ;
				}
				
				// add Pasing Datas
				paserArray.add(ResponsePaserData) ;
			}
			
			return paserArray ;
		} catch (Exception e) {
			e.printStackTrace();

			return null ;
		}
	}
}
