package com.chartrecommend.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.chartrecommend.data.MusicData;
import com.chartrecommend.util.util;

import android.util.Log;

public class MusicChartNetwork extends MusicChartParser {

	private final String TAG = "Music Chart Netowkr :" ;
	
    public MusicChartNetwork () {
    	super () ;
    }
    
    // Get Post type list
	public String sendData(String url) {
		
		// 연결 HttpClient 객체 생성
		HttpURLConnection conn = null ;
		BufferedReader bufferedReader = null ;
		try {
			// Try URL Connection
			for (int i = 0 ; i < util.RECONNECTION_COUNT ; i++) {
				conn = setURLConnection(new URL(url)) ;
				if (conn != null)	break ;
			}
			String responseMsg = "" ;

			// HTTP 상태코드         
			// 200 인경우 성공
			if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			 
				Log.d (TAG + "HTTP Status code : ", "" + conn.getResponseCode()) ;
				String data = "" ;
				bufferedReader = new BufferedReader(new InputStreamReader (conn.getInputStream())) ;
				
				while ((data = bufferedReader.readLine()) != null)
					responseMsg += data + "\n" ;
			}
			else {
				Log.d (TAG + "HTTP Status code : ", "" + conn.getResponseCode()) ;
			}
			
			return responseMsg ;
		} catch (IOException e) {
			e.printStackTrace();
			
			return null ;
		} catch (Exception e) {
			e.printStackTrace();
			
			return null;
			
		} finally {
			// disConnection 
			if (conn != null) conn.disconnect() ;
			// Stream Close
			if (bufferedReader != null) {
				try {
					bufferedReader.close() ;
				} catch (IOException e) {
					bufferedReader = null ;
				}
			}
		}
	}
	
	private HttpURLConnection setURLConnection (URL url) {
		
		HttpURLConnection conn = null ;
		
		try {
		conn = (HttpURLConnection) url.openConnection() ;
		// 객체 연결 설정 부분, 연결 시간, 데이터 대기 시간
		conn.setConnectTimeout(util.CONNECT_DELAY_TIME) ;
		conn.setReadTimeout(util.READ_DELAY_TIME) ;
		conn.setRequestMethod(util.GET) ;

		// Header Setting
		// 컨트롤 캐쉬 설정
		conn.setUseCaches(false) ;
		// 타입설정(application/json) 형식으로 전송 (Request Body 전달시 application/json로 서버에 전달.)
		//conn.setRequestProperty("Content-Type", "application/json") ;
		// Server Response JSON Type
		//conn.setRequestProperty("Accept", "application/json") ;

		// Request, Response Type
		conn.setDoInput(true) ;
		conn.setDoOutput(true) ;
		} catch (IOException e) {
			e.printStackTrace() ; 
		}
		
		return conn ;
	}
	
	// Html Paser
	public ArrayList<MusicData> getResponseArrays (String responseMsg) {

		try {
			Log.d (TAG + "Response : " , "Response Data : " + responseMsg) ;
		} catch (NullPointerException e) {
			Log.e (TAG + "Response : " , "null") ;
		}
		return this.responsePaser(responseMsg) ;
	}
}
