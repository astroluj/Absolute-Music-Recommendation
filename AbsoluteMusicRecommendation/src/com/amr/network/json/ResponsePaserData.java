package com.amr.network.json;

import android.os.Parcel;
import android.os.Parcelable;

public class ResponsePaserData implements Parcelable {

	private String track_id, artist, title, album, url, score  ;
	
	public static final Parcelable.Creator<ResponsePaserData> CREATOR = new Parcelable.Creator<ResponsePaserData> () {

		@Override
		public ResponsePaserData createFromParcel(Parcel source) {
			return new ResponsePaserData (source) ;
		}

		@Override
		public ResponsePaserData[] newArray(int size) {
			return new ResponsePaserData[size] ;
		}
	} ;
	
	public ResponsePaserData  () {
		// String
		track_id = "" ;
		artist = "" ;
		title = "" ;
		album = "" ;
		url = "" ;
		
		// Float .2f
		score = "" ;
	}
	
	public ResponsePaserData (Parcel source) {
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
