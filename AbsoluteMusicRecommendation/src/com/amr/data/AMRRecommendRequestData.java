package com.amr.data;

public class AMRRecommendRequestData {

	// Cusmtom Class
	private UserData userData, subUserData ;
	
	private String feature, track_id, artist, title, album, content ;
	private Integer startIndex, count ;
	
	// Constructor
	public AMRRecommendRequestData () {
		
		// String
		this.feature = "" ;
		this.track_id =  "" ;
		this.artist = "" ;
		this.title = "" ;
		this.album = "" ;
		this.content = "" ;
		
		this.userData = new UserData () ;
		this.subUserData = new UserData () ;
		
		// Integer
		this.startIndex = 0 ;
		this.count = 0 ;
	}
	
	public AMRRecommendRequestData (String feature, String track_id,
			String artist, String title, String album, String content,
			UserData userData, UserData subUserData,
			Integer startIndex, Integer count) {
		// String
		this.feature = feature ;
		this.track_id =  track_id ;
		this.artist = artist ;
		this.title = title ;
		this.album = album ;
		this.content = content ;
		
		// UserData
		this.userData = userData ;
		this.subUserData = subUserData ;
		
		// Integer
		this.startIndex = startIndex ;
		this.count = count ;
	}

	
	public AMRRecommendRequestData (AMRRecommendRequestData amrData) {
		// String
		this.feature = amrData.getFeature() ;
		this.track_id =  amrData.getTrackID() ;
		this.artist = amrData.getArtist() ;
		this.title = amrData.getTitle() ;
		this.album = amrData.getAlbum () ;
		this.content = amrData.getContent () ;
		
		// UserData
		this.userData = amrData.getUserData() ;
		this.subUserData = amrData.getSubUserData() ;
		
		// Integer
		this.startIndex = amrData.getStartIndex() ;
		this.count = amrData.getCount() ;
	}
	
	// feature GetSet
	public String getFeature () {
		return this.feature ;
	}
	public void setFeature (String feature) {
		this.feature = feature ;
	}
	
	// track_id GetSet
	public String getTrackID () {
		return this.track_id ;
	}
	public void setTrackID (String track_id) {
		this.track_id = track_id ;
	}
	
	// artist GetSet
	public String getArtist () {
		return this.artist ;
	}
	public void setArtist (String artist) {
		this.artist = artist ;
	}
	
	// title GetSet
	public String getTitle () {
		return this.title ;
	}
	public void setTitle (String title) {
		this.title = title ;
	}
	
	// Album GetSEt 
	public String getAlbum () {
		return this.album ;
	}
	public void setAlbum (String album) {
		this.album = album ;
	}
	
	// Content GetSet
	public String getContent () {
		return this.content ;
	}
	public void setContent (String content) {
		this.content = content ;
	}
	
	// User ID GetSet
	public UserData getUserData () {
		return this.userData ;
	}
	public void setUserData (UserData userData) {
		this.userData = userData ;
	}
	public UserData getSubUserData () {
		return this.subUserData ;
	}
	public void setSubUserData (UserData subUserData) {
		this.subUserData =  subUserData ;
	}
	
	// startIndex GetSet
	public Integer getStartIndex () {
		return this.startIndex ;
	}
	public void setStartIndex (Integer startIndex) {
		this.startIndex = startIndex ;
	}
	
	// count GetSet
	public Integer getCount () {
		return this.count ;
	}
	public void setCount (Integer count) {
		this.count = count ;
	}
}
