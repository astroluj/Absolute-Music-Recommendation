package com.amr.network.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.amr.data.AMRRecommendRequestData;
import com.amr.data.AMRData;
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
			JSONArray jsonArray = jsonObject.getJSONArray(util.TRACKS) ;

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
				
				
				// add Pasing Datas
				paserArray.add(ResponsePaserData) ;
			}
			
			return paserArray ;
		} catch (JSONException e) {
			e.printStackTrace();

			return null ;
		}
	}
}
