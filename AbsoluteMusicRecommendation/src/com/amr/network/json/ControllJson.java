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
			
			if (amrData.getContent() != null)
				jsonObject.put(util.CONTENT, amrData.getContent()) ;
			
			if (amrData.getUserData() != null) 
				jsonObject.put(amrData.getUserData().getName(), amrData.getUserData().getUserID()) ;
			
			if (amrData.getSubUserData() != null) 
				jsonObject.put(amrData.getSubUserData().getName(), amrData.getSubUserData().getUserID()) ;
			
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
			Boolean isAdditionalItems = null ;
			
			JSONObject jsonObject = new JSONObject(responseMsg) ;
			JSONArray jsonArray = null ;
			
			// AdditionalItems
			try {
				isAdditionalItems = jsonObject.getBoolean(util.ADDITIONAL_ITEMS) ;
			} catch (JSONException e) {}
			
			// ETC.
			try {
				jsonArray = jsonObject.getJSONArray(util.TRACKS) ;
			} catch (JSONException e) {
				try {
					jsonArray = jsonObject.getJSONArray(util.REVIEWS) ;
				} catch (JSONException e2) {
					try {
						jsonArray = jsonObject.getJSONArray(util.LISTENING_HISTORY) ;
					} catch (JSONException e3) {
						try {
							jsonArray = jsonObject.getJSONArray(util.USERS) ;
						} catch (JSONException e4) {
							try {
								jsonArray = jsonObject.getJSONArray(util.MATES) ;
							} catch (JSONException e5) {
								// Error Messages
								JSONObject paserData = jsonObject.getJSONObject(util.ERROR) ;
								
								AMRData responsePaserData = new AMRData () ;
								
								try {
									responsePaserData.setErrorCode(paserData.getInt(util.ERROR_CODE)) ;
								} catch (JSONException e6) {
									responsePaserData.setErrorCode(null) ;
								}
								
								try {
									responsePaserData.setErrorDescription(paserData.getString(util.ERROR_DESCRIPTION)) ;
								} catch (JSONException e6) {
									responsePaserData.setErrorDescription(null) ;
								}
								
								paserArray.add(responsePaserData) ;
								
								return paserArray ;
							}
						}
					}
				}
			}

			for (int i = 0, size = jsonArray.length() ; i < size ; i++) {
				
				JSONObject paserData = jsonArray.getJSONObject(i) ;
				
				AMRData responsePaserData = new AMRData () ;
				
				// Keys Paser
				try {
					responsePaserData.setTrackID(paserData.getString(util.TRACK_ID)) ;
				} catch (JSONException e) {
					responsePaserData.setTrackID(null) ;
				}
				
				try {
					responsePaserData.setUserID (paserData.getString(util.USER_ID)) ;
				} catch (JSONException e) {
					responsePaserData.setUserID(null) ;
				}
				
				try {
					responsePaserData.setArtist(paserData.getString(util.ARTIST)) ;
				} catch (JSONException e) {
					responsePaserData.setArtist(null) ;
				}
				
				try {
					responsePaserData.setTitle(paserData.getString(util.TITLE)) ;
				} catch (JSONException e) {
					responsePaserData.setTitle(null) ;
				}
				
				try {
					responsePaserData.setAlbum(paserData.getString(util.ALBUM)) ;
				} catch (JSONException e) {
					responsePaserData.setAlbum(null) ;
				}
				
				try {
					responsePaserData.setURL(paserData.getString(util.URL)) ;
				} catch (JSONException e) {
					responsePaserData.setURL(null) ;
				}
				
				try {
					responsePaserData.setTimeStampe(paserData.getString(util.TIME_STAMP));
				} catch (JSONException e) {
					responsePaserData.setTimeStampe(null) ;
				}
				
				try {
					responsePaserData.setContent(paserData.getString(util.CONTENT));
				} catch (JSONException e) {
					responsePaserData.setContent(null) ;
				}
				
				try {
					responsePaserData.setScore(paserData.getDouble(util.SCORE)) ;
				} catch (JSONException e) {
					responsePaserData.setScore(null) ;
				}
				
				try {
					responsePaserData.setAdditionalItems(isAdditionalItems) ;
				} catch (Exception e) {
					responsePaserData.setAdditionalItems(null) ;
				}
				
				try {
					// Init Response message Keys containers
					ArrayList<AMRData> subPaserArray = new ArrayList<AMRData> () ;
					
					JSONArray subJsonArray = paserData.getJSONArray(util.TRACKS) ;
					
					for (int j = 0, subSize = jsonArray.length() ; j < subSize ; j++) {
						
						JSONObject subPaserData = subJsonArray.getJSONObject(i) ;
						
						AMRData trackData = new AMRData () ;
						
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
					
					responsePaserData.setTrack(subPaserArray) ;
				} catch (JSONException e) {
					responsePaserData.setTrack(null) ;
				}
				
				// add Pasing Datas
				paserArray.add(responsePaserData) ;
			}
			
			return paserArray ;
		} catch (Exception e) {
			e.printStackTrace();

			return null ;
		}
	}
}
