package com.amr.data;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class AMRData implements Parcelable {

	// Custom Class
	private ArrayList<TrackData> trackData ;
	
	private String track_id, user_id, artist, title, album, url, score, timeStamp, content  ;
	
	public static final Parcelable.Creator<AMRData> CREATOR = new Parcelable.Creator<AMRData> () {

		@Override
		public AMRData createFromParcel(Parcel source) {
			return new AMRData (source) ;
		}

		@Override
		public AMRData[] newArray(int size) {
			return new AMRData[size] ;
		}
	} ;
	
	// Constructor
	public AMRData () {
		// TrackData
		this.trackData = new ArrayList<TrackData> () ;
		
		// String
		this.track_id = "" ;
		this.user_id = "" ;
		
		this.artist = "" ;
		this.title = "" ;
		this.album = "" ;
		this.url = "" ;
		this.timeStamp = "" ;
		this.content = "" ;
		
		// Float .2f
		this.score = "" ;
	}
	
	public AMRData (AMRData recommendData) {
		// TrackData
		this.trackData = recommendData.getTrack() ;
		
		// String
		this.track_id = recommendData.getTrackID() ;
		this.user_id = recommendData.getUserID () ;
		
		this.artist = recommendData.getArtist() ;
		this.title = recommendData.getTitle() ;
		this.album = recommendData.getAlbum() ;
		this.url = recommendData.getURL() ;
		
		this.timeStamp = recommendData.getTimeStamp() ;
		this.content = recommendData.getContent() ;
		
		// Float .2f
		this.score = recommendData.getScore() ;
	}
	
	public AMRData (ArrayList<TrackData> trackData, String track_id, String user_id,
			String artist, String title, String album,
			String url, String score,
			String timeStamp, String content) {
		
		// TrackData
		this.trackData = trackData ;
		
		// String
		this.track_id = track_id ;
		this.user_id = user_id ;
		
		this.artist = artist ;
		this.title = title ;
		this.album = album ;
		this.url = url ;
		
		this.timeStamp = timeStamp ;
		this.content = content ;
		
		// Float .2f
		this.score = score ;		
	}
	
	public AMRData (Parcel source) {
		// TrackData
		this.trackData = source.readArrayList(TrackData.class.getClassLoader()) ;
				
		// String
		this.track_id = source.readString() ;
		this.user_id = source.readString() ;
		
		this.artist = source.readString() ;
		this.title = source.readString() ;
		this.album = source.readString() ;
		this.url = source.readString() ;
		
		this.timeStamp = source.readString() ;
		this.content = source.readString() ;
		
		// Float .2f
		this.score = source.readString() ;
	}
	
	// Get TrackData
	public ArrayList<TrackData> getTrack() {
		return this.trackData ;
	}
	// Set TrackData
	public void setTrack (ArrayList<TrackData> trackData) {
		this.trackData = trackData ;
	}
	
	// Get track_id
	public String getTrackID () {
		return this.track_id ;
	}
	// Set track_id
	public void setTrackID (String track_id) {
		this.track_id = track_id ;
	}
	
	// Get track_id
	public String getUserID () {
		return this.user_id ;
	}
	// Set track_id
	public void setUserID (String user_id) {
		this.user_id = user_id ;
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
	
	// Get timeStamp
	public String getTimeStamp () {
		return this.timeStamp ;
	}
	// Set timeStatmp
	public void  setTimeStampe (String timeStamp) {
		this.timeStamp = timeStamp ;
	}
	
	// Get content
	public String getContent () {
		return this.content ;
	}
	// Set Content
	public void setContent (String content) {
		this.content = content ;
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

		dest.writeTypedList(this.trackData);
		dest.writeString(this.track_id) ;
		dest.writeString(this.user_id) ;
		dest.writeString(this.artist) ;
		dest.writeString(this.title) ;
		dest.writeString(this.album) ;
		dest.writeString(this.url) ;
		dest.writeString(this.timeStamp);
		dest.writeString(this.content);
		dest.writeString(this.score) ;
	}
}
