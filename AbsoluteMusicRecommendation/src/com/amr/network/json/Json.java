package com.amr.network.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.amr.util.util;

public class Json {

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
	protected ArrayList<Paser> responsePaser (String responseMsg) {
		
		try {
			// Init Response message Keys containers
			ArrayList<Paser> paserArray = new ArrayList<Paser> () ;
			
			JSONObject jsonObject = new JSONObject(responseMsg) ;
			JSONArray jsonArray = jsonObject.getJSONArray(util.TRACKS) ;

			for (int i = 0, size = jsonArray.length() ; i < size ; i++) {
				
				JSONObject paserData = jsonArray.getJSONObject(i) ;
				
				Paser paser = new Paser () ;
				
				// Keys Paser
				try {
					paser.setTrackID(paserData.getString(util.TRACK_ID)) ;
				} catch (JSONException e) {
					paser.setTrackID(null) ;
				}
				
				try {
					paser.setArtist(paserData.getString(util.ARTIST)) ;
				} catch (JSONException e) {
					paser.setArtist(null) ;
				}
				
				try {
					paser.setTitle(paserData.getString(util.TITLE)) ;
				} catch (JSONException e) {
					paser.setTitle(null) ;
				}
				
				try {
					paser.setAlbum(paserData.getString(util.ALBUM)) ;
				} catch (JSONException e) {
					paser.setAlbum(null) ;
				}
				
				try {
					paser.setURL(paserData.getString(util.URL)) ;
				} catch (JSONException e) {
					paser.setURL(null) ;
				}
				
				try {
					paser.setScore(paserData.getString(util.SCORE)) ;
				} catch (JSONException e) {
					paser.setScore(null) ;
				}
				
				// add Pasing Datas
				paserArray.add(paser) ;
			}
			
			return paserArray ;
		} catch (JSONException e) {
			e.printStackTrace();

			return null ;
		}
	}
}
