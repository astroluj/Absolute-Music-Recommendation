package com.chartrecommend.data;

import android.graphics.Bitmap;

public class MusicData {

	private Bitmap thumbnail ;
	private String title, artist, album ;

	public MusicData () {
		
		this.thumbnail = null ;
		
		this.title = null ;
		this.artist = null ;
		this.album = null ;
	}
	
	public MusicData (Bitmap thumbnail, String title, String artist, String album) {
		
		this.thumbnail = thumbnail ;
		
		this.title = title ;
		this.artist =artist ;
		this.album = album ;
	}
	
	public MusicData (MusicData musicData) {
		
		this.thumbnail = musicData.getThumbnail() ;
		
		this.title = musicData.getTitle() ;
		this.artist = musicData.getArtist() ;
		this.album = musicData.getAlbum() ;
	}
	
	// Thumbnail GetSet
	public Bitmap getThumbnail () {
		return this.thumbnail ;
	}
	public void setThumbnail (Bitmap thumbnail) {
		this.thumbnail = thumbnail ;
	}
	
	// title GetSet
	public String getTitle () {
		return this.title ;
	}
	public void setTitle (String title) {
		this.title = title ;
	}
	
	// Artist GetSet
	public String getArtist () {
		return this.artist ;
	}
	public void setArtist (String artist) {
		this.artist = artist ;
	}
	
	// Album GetSet
	public String getAlbum () {
		return this.album ;
	}
	public void setAlbum (String album) {
		this.album = album ;
	}
}
