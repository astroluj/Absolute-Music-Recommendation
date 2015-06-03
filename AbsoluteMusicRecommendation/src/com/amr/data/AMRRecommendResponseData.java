package com.amr.data;

import android.os.Parcel;
import android.os.Parcelable;

public class AMRRecommendResponseData implements Parcelable {

	private String track_id, artist, title, album, url, score  ;
	
	public static final Parcelable.Creator<AMRRecommendResponseData> CREATOR = new Parcelable.Creator<AMRRecommendResponseData> () {

		@Override
		public AMRRecommendResponseData createFromParcel(Parcel source) {
			return new AMRRecommendResponseData (source) ;
		}

		@Override
		public AMRRecommendResponseData[] newArray(int size) {
			return new AMRRecommendResponseData[size] ;
		}
	} ;
	
	// Constructor
	public AMRRecommendResponseData () {
		// String
		this.track_id = "" ;
		this.artist = "" ;
		this.title = "" ;
		this.album = "" ;
		this.url = "" ;
		
		// Float .2f
		this.score = "" ;
	}
	
	public AMRRecommendResponseData (AMRRecommendResponseData recommendData) {
		// String
		this.track_id = recommendData.getTrackID() ;
		this.artist = recommendData.getArtist() ;
		this.title = recommendData.getTitle() ;
		this.album = recommendData.getAlbum() ;
		this.url = recommendData.getURL() ;
		
		// Float .2f
		this.score = recommendData.getScore() ;
	}
	
	public AMRRecommendResponseData (String track_id, String artist, String title, String album, String url, String score) {
		// String
		this.track_id = track_id ;
		this.artist = artist ;
		this.title = title ;
		this.album = album ;
		this.url = url ;
		
		// Float .2f
		this.score = score ;		
	}
	
	public AMRRecommendResponseData (Parcel source) {
		// String
		this.track_id = source.readString() ;
		this.artist = source.readString() ;
		this.title = source.readString() ;
		this.album = source.readString() ;
		this.url = source.readString() ;
		
		// Float .2f
		this.score = source.readString() ;
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

	@Override
	// Override Type alert
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeString(this.track_id) ;
		dest.writeString(this.artist) ;
		dest.writeString(this.title) ;
		dest.writeString(this.album) ;
		dest.writeString(this.url) ;
		dest.writeString(this.score) ;
	}
}
