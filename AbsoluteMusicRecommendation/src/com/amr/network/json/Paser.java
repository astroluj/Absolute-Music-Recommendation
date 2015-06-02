package com.amr.network.json;

public class Paser {

	private String track_id, artist, title, album, url, score  ;
	
	public Paser  () {
		// String
		track_id = "" ;
		artist = "" ;
		title = "" ;
		album = "" ;
		url = "" ;
		
		// Float .2f
		score = "" ;
	}
	
	// Get track_id
	public String getTrackID () {
		return this.track_id ;
	}
	// Set track_id
	public void setTrackID (String track_id) {
		this.track_id = track_id ;
	}
	
	// Get artist
	public String getArtist () {
		return this.artist ;
	}
	// Set artist
	public void setArtist (String artist) {
		this.artist = artist ;
	}
	
	// Get title
	public String getTitle () {
		return this.title ;
	}
	// Set title
	public void setTitle (String title) {
		this.title =  title ;
	}
	
	// Get album
	public String getAlbum () {
		return this.album ;
	}
	// Set album
	public void setAlbum (String album) {
		this.album =  album ;
	}
	
	// Get url 
	public String getURL () {
		return this.url ;
	}
	// Set url
	public void setURL (String url) {
		this.url = url ;
	}
	
	// Get score
	public String getScore () {
		return this.score ;
	}
	// Set score
	public void setScore (String score) {
		try {
		this.score = String.format("%.2f", Float.parseFloat(score)) ;
		} catch (NullPointerException e) {
			this.score =  score ;
		} 
	}
}
