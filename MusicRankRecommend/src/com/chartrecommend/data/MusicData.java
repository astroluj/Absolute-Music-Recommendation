package com.chartrecommend.data;

public class MusicData {

	private String thumbnailSrc ;
	private String title, artist, album ;

	public MusicData () {
		
		this.thumbnailSrc = null ;
		
		this.title = null ;
		this.artist = null ;
		this.album = null ;
	}
	
	public MusicData (String thumbnailSrc, String title, String artist, String album) {
		
		this.thumbnailSrc = thumbnailSrc ;
		
		this.title = title ;
		this.artist =artist ;
		this.album = album ;
	}
	
	public MusicData (MusicData musicData) {
		
		this.thumbnailSrc = musicData.getThumbnailSrc() ;
		
		this.title = musicData.getTitle() ;
		this.artist = musicData.getArtist() ;
		this.album = musicData.getAlbum() ;
	}
	
	// Thumbnail GetSet
	public String getThumbnailSrc () {
		return this.thumbnailSrc ;
	}
	public void setThumbnailSrc (String thumbnailSrc) {
		this.thumbnailSrc = thumbnailSrc ;
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
