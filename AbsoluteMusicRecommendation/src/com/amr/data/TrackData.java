package com.amr.data;

import android.os.Parcel;
import android.os.Parcelable;

public class TrackData implements Parcelable {

	private String track_id, artist, title ;
	
	public static final Parcelable.Creator<TrackData> CREATOR = new Parcelable.Creator<TrackData> () {

		@Override
		public TrackData createFromParcel(Parcel source) {
			return new TrackData (source) ;
		}

		@Override
		public TrackData[] newArray(int size) {
			return new TrackData[size] ;
		}
	} ;
	
	public TrackData () {
		
		this.track_id = "" ;
		this.artist = "" ;
		this.title = "" ;
	}
	
	public TrackData (String track_id, String artist, String title) {
		
		this.track_id = track_id ;
		this.artist = artist ;
		this.title = title ;
	}
	
	public TrackData (TrackData trackData) {
		
		this.track_id = trackData.getTrackID () ;
		this.artist = trackData.getArtist () ;
		this.title = trackData.getTitle () ;
	}
	
	public TrackData (Parcel source) {
		
		this.track_id = source.readString() ;
		this.artist = source.readString() ;
		this.title = source.readString() ;
	}
	
	// track_id GetSet
	public String getTrackID () {
		return this.track_id ;
	}
	public void setTrackID (String track_id) {
		this.track_id = track_id ;
	}
	
	// Artist GetSet
	public String getArtist () {
		return this.artist ;
	}
	public void setArtist (String artist) {
		this.artist = artist ;
	}
	
	// Title GetSet
	public String getTitle () {
		return this.title ;
	}
	public void setTitle (String title) {
		this.title = title ;
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
	}
}
