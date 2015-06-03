package com.amr.data;

public class AMRRecommendRequestData {

	private String feature, track_id, artist, title ;
	private Integer startIndex, count ;
	
	// Constructor
	public AMRRecommendRequestData () {
		
		// String
		this.feature = "" ;
		this.track_id =  "" ;
		this.artist = "" ;
		this.title = "" ;
		
		// Integer
		this.startIndex = 0 ;
		this.count = 0 ;
	}
	
	public AMRRecommendRequestData (String feature, String track_id, String artist, String title,
			Integer startIndex, Integer count) {
		// String
		this.feature = feature ;
		this.track_id =  track_id ;
		this.artist = artist ;
		this.title = title ;
		
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
