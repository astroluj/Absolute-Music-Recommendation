package com.chartrecommend.util;

public class util {

	// Intent Put keys
	public static final String URL_KEY = "URL" ;
	public static final String CASE_KEY = "CASE" ;
	
	// URLs
	public static final String NAVER_CHART = "http://m.music.naver.com/listen/top100.nhn?domain=DOMESTIC" ;
	public static final String BILLBOARD_CHART = "http://www.billboard.com/charts/hot-100?mobile_redirection=false" ;
	public static final String ITUNES_CHART = "http://www.apple.com/itunes/charts/songs/" ;
	public static final String GAON_CHART = "http://www.gaonchart.co.kr/main/section/chart/online.gaon" ;
	public static final String GOMTV_CHART = "www.gomtv.com/chart/index.gom?cate=music&rank=" ;
	public static final String MNET_CHART = "http://www.mnet.com/chart/TOP100/2015061801?pNum=1" ;
	// Networks
	public static final int RECONNECTION_COUNT = 5 ;
	public static final String UTF_8 = "UTF-8" ;
	public final static int CONNECT_DELAY_TIME = 5000 ;
	
	// HTML site Case
	public static final int NAVER = 0 ;
	public static final int BILLBOARD = 1 ;
	public static final int ITUNES = 2 ;
	public static final int GAON = 3 ;
	public static final int GOMTV = 4 ;
	public static final int MNET = 5 ;
}
