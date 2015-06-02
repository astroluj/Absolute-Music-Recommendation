package com.amr.network.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.amr.util.util;

public class ControllJson {

	// Music/Search Json
	protected String searchMakeJson (String feature, String track_id,
			String artist, String title, 
			Object startIndex, Object count) {
		
		String jsonMsg = "" ;
		
		try {
			JSONObject jsonObject = new JSONObject() ;
			
			if (feature != null) 
				jsonObject.put(util.FEATURE, feature) ;
			
			if (track_id != null)
				jsonObject.put(util.TRACK_ID, track_id) ;
			
			if (artist != null)
				jsonObject.put(util.ARTIST, artist) ;
			
			if (title != null)
				jsonObject.put(util.TITLE, title) ;
			
			if (startIndex != null) 
				jsonObject.put(util.START, (int)startIndex) ;
			
			if (count != null)
				jsonObject.put(util.COUNT, (int)count) ;

			jsonMsg = jsonObject.toString() ;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jsonMsg ;
	}
	
	// Music/Similar Json
	// Music/Recommend Json
	
	// Paser
	protected ArrayList<ResponsePaserData> responsePaser (String responseMsg) {
		
		try {
			// Init Response message Keys containers
			ArrayList<ResponsePaserData> paserArray = new ArrayList<ResponsePaserData> () ;
			
			JSONObject jsonObject = new JSONObject(responseMsg) ;
			JSONArray jsonArray = jsonObject.getJSONArray(util.TRACKS) ;

			for (int i = 0, size = jsonArray.length() ; i < size ; i++) {
				
				JSONObject paserData = jsonArray.getJSONObject(i) ;
				
				ResponsePaserData ResponsePaserData = new ResponsePaserData () ;
				
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
