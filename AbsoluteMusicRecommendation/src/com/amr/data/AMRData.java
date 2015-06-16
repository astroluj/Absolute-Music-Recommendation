package com.amr.data;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class AMRData implements Parcelable {

	// Custom Class
	private ArrayList<AMRData> trackData ;
	
	private String track_id, user_id, artist, title, album, url, timeStamp, content  ;
	private Double score ;
	private Boolean isAdditionalItems ;
	
	// Errors
	private String errorDescription ;
	private Integer errorCode ;
	
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
		this.trackData = new ArrayList<AMRData> () ;

		// String
		this.track_id = null ;
		this.user_id = null ;
		
		this.artist = null ;
		this.title = null ;
		this.album = null ;
		this.url = null ;
		this.timeStamp = null ;
		this.content = null ;
		
		// boolean
		this.isAdditionalItems = null ;
		
		// Float .2f
		this.score = null ;
				
		// Errors
		this.errorCode = null ;
		this.errorDescription = null ;
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
		
		// boolean
		this.isAdditionalItems = recommendData.isAdditionalItems() ;
		
		// Float .2f
		this.score = recommendData.getScore() ;
				
		// Erros
		this.errorCode = recommendData.getErrorCode() ;
		this.errorDescription = recommendData.getErrorDescription() ;
	}
	
	public AMRData (ArrayList<AMRData> trackData, String track_id, String user_id,
			String artist, String title, String album,
			String url, Double score,
			String timeStamp, String content, Boolean isAdditionalItems,
			Integer errorCode, String errorDescription) {
		
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
		
		// boolean
		this.isAdditionalItems = isAdditionalItems ;
		
		// Float .2f
		this.score = score ;		
		
		// Erros
		this.errorCode = errorCode ;
		this.errorDescription =  errorDescription ;
	}
	
	public AMRData (Parcel source) {
		// TrackData
		try {
			source.readTypedList(trackData, AMRData.CREATOR);
		} catch (NullPointerException e) {
			this.trackData = source.createTypedArrayList(AMRData.CREATOR) ;
		}
		// String
		this.track_id = source.readString() ;
		this.user_id = source.readString() ;
		
		this.artist = source.readString() ;
		this.title = source.readString() ;
		this.album = source.readString() ;
		this.url = source.readString() ;
		
		this.timeStamp = source.readString() ;
		this.content = source.readString() ;
		
		// Boolean
		try {
			this.isAdditionalItems = (Boolean) source.readValue(Boolean.class.getClassLoader()) ;
		} catch (Exception e) {
			this.isAdditionalItems = null ;
		}
		
		// Float .2f
		try {
			this.score = (Double) source.readValue(Double.class.getClassLoader()) ;
		} catch (Exception e) {
			this.score = null ;
		}
		
		// Errors
		try {
			this.errorCode = (Integer) source.readValue(Integer.class.getClassLoader()) ;
		} catch (Exception e) {
			this.errorCode = null ;
		}
		this.errorDescription = source.readString() ;
	}
	
	// Get TrackData
	public ArrayList<AMRData> getTrack() {
		return this.trackData ;
	}
	// Set TrackData
	public void setTrack (ArrayList<AMRData> trackData) {
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
	public Double getScore () {
		return this.score ;
	}
	// Set score
	public void setScore (Double score) {
		this.score =  score ;
	}

	// Get AdditionalItems
	public Boolean isAdditionalItems () {
		return this.isAdditionalItems ;
	}
	// Set AdditionalItems
	public void setAdditionalItems (Boolean isAdditionalItems) {
		this.isAdditionalItems = isAdditionalItems ;
	}
	
	// Get Error Code
	public Integer getErrorCode () {
		return this.errorCode ;
	}
	// Set Error code
	public void setErrorCode (Integer errorCode) {
		this.errorCode = errorCode ;
	}
	
	// Get Error Description
	public String getErrorDescription () {
		return this.errorDescription ;
	}
	// Set Error Description
	public void setErrorDescription (String errorDescription) {
		this.errorDescription = errorDescription ;
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
		dest.writeValue(this.score) ;
		dest.writeValue(this.isAdditionalItems) ;
		dest.writeValue(this.errorCode);
		dest.writeString(this.errorDescription);
	}
}
