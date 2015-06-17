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
	
	// Parser
	protected ArrayList<AMRData> responseParser (String responseMsg) {
		
		try {
			// Init Response message Keys containers
			ArrayList<AMRData> parserArray = new ArrayList<AMRData> () ;
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
								JSONObject parserData = jsonObject.getJSONObject(util.ERROR) ;
								
								AMRData responseParserData = new AMRData () ;
								
								try {
									responseParserData.setErrorCode(parserData.getInt(util.ERROR_CODE)) ;
								} catch (JSONException e6) {
									responseParserData.setErrorCode(null) ;
								}
								
								try {
									responseParserData.setErrorDescription(parserData.getString(util.ERROR_DESCRIPTION)) ;
								} catch (JSONException e6) {
									responseParserData.setErrorDescription(null) ;
								}
								
								parserArray.add(responseParserData) ;
								
								return parserArray ;
							}
						}
					}
				}
			}

			for (int i = 0, size = jsonArray.length() ; i < size ; i++) {
				
				JSONObject parserData = jsonArray.getJSONObject(i) ;
				
				AMRData responseParserData = new AMRData () ;
				
				// Keys Parser
				try {
					responseParserData.setTrackID(parserData.getString(util.TRACK_ID)) ;
				} catch (JSONException e) {
					responseParserData.setTrackID(null) ;
				}
				
				try {
					responseParserData.setUserID (parserData.getString(util.USER_ID)) ;
				} catch (JSONException e) {
					responseParserData.setUserID(null) ;
				}
				
				try {
					responseParserData.setArtist(parserData.getString(util.ARTIST)) ;
				} catch (JSONException e) {
					responseParserData.setArtist(null) ;
				}
				
				try {
					responseParserData.setTitle(parserData.getString(util.TITLE)) ;
				} catch (JSONException e) {
					responseParserData.setTitle(null) ;
				}
				
				try {
					responseParserData.setAlbum(parserData.getString(util.ALBUM)) ;
				} catch (JSONException e) {
					responseParserData.setAlbum(null) ;
				}
				
				try {
					responseParserData.setURL(parserData.getString(util.URL)) ;
				} catch (JSONException e) {
					responseParserData.setURL(null) ;
				}
				
				try {
					responseParserData.setTimeStampe(parserData.getString(util.TIME_STAMP));
				} catch (JSONException e) {
					responseParserData.setTimeStampe(null) ;
				}
				
				try {
					responseParserData.setContent(parserData.getString(util.CONTENT));
				} catch (JSONException e) {
					responseParserData.setContent(null) ;
				}
				
				try {
					responseParserData.setScore(parserData.getDouble(util.SCORE)) ;
				} catch (JSONException e) {
					responseParserData.setScore(null) ;
				}
				
				try {
					responseParserData.setAdditionalItems(isAdditionalItems) ;
				} catch (Exception e) {
					responseParserData.setAdditionalItems(null) ;
				}
				
				try {
					// Init Response message Keys containers
					ArrayList<AMRData> subParserArray = new ArrayList<AMRData> () ;
					
					JSONArray subJsonArray = parserData.getJSONArray(util.TRACKS) ;
					
					for (int j = 0, subSize = jsonArray.length() ; j < subSize ; j++) {
						
						JSONObject subParserData = subJsonArray.getJSONObject(i) ;
						
						AMRData trackData = new AMRData () ;
						
						try {
							trackData.setTrackID(subParserData.getString(util.TRACK_ID)) ;
						} catch (JSONException e) {
							trackData.setTrackID(null) ;
						}
						
						try {
							trackData.setArtist(subParserData.getString(util.ARTIST)) ;
						} catch (JSONException e) {
							trackData.setArtist(null) ;
						}
						
						try {
							trackData.setTitle(subParserData.getString(util.TITLE)) ;
						} catch (JSONException e) {
							trackData.setTitle(null) ;
						}
						
						// add Pasing Datas
						subParserArray.add(trackData) ;
					}
					
					responseParserData.setTrack(subParserArray) ;
				} catch (JSONException e) {
					responseParserData.setTrack(null) ;
				}
				
				// add Pasing Datas
				parserArray.add(responseParserData) ;
			}
			
			return parserArray ;
		} catch (Exception e) {
			e.printStackTrace();

			return null ;
		}
	}
}
