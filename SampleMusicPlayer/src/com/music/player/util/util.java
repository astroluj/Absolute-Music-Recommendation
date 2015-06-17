package com.music.player.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class util {

	public static final String POSITION_NAME = "position" ;
	
	public static final String MUSIC_ARTIST_LIST_NAME = "musicArtistList" ;
	public static final String MUSIC_TITLE_LIST_NAME = "musicTitleList" ;
	public static final String ALBUM_IMAGE_LIST_NAME = "albumImageList" ;
	public static final String MUSIC_ID_LIST_NAME = "musicIDList" ;
	
	// Time Format
	public static final String musicTimeFormatter (int time) {
		final String TIME_FORMAT = "mm:ss" ;
		
		Date date = new Date () ;
		date.setMinutes(0) ;
		date.setSeconds(time) ;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT) ;
		
		return simpleDateFormat.format(date.getTime()) ;
	}
	
	// 15 Second
	public static final int SEEK = 15000 ;
	
	public static final int INCREASE = 1 ;
	public static final int DECREASE = -1 ;
	public static final int REQUEST_CODE = 231 ;
	
	public static final String UNKNOWN = "<unknown>" ;
	
	public static final String AMR_FILTER= "com.amr.service.AMRService" ;
	
	public static final String MEDIA_PLAY_FILTER = "content://media/external/audio/media/" ;
	public static final long INTERVAL = 1000 ;
	public static final String MUSIC_RECOMMEND_RESPONSE_FILTER = "com.music.player.response" ;
}
