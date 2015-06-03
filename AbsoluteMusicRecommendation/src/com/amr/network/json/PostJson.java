package com.amr.network.json;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.amr.data.AMRRecommendRequestData;
import com.amr.data.AMRRecommendResponseData;
import com.amr.util.util;

import android.content.ContentValues;
import android.util.Log;

public class PostJson extends ControllJson {

    public PostJson () {
    	super () ;
    }
    
    // Get Post type list
	public String sendData(ContentValues jsonMsg, String url) {
		
		// 연결 HttpClient 객체 생성
		HttpURLConnection conn = null ;
		OutputStream outputStream = null ;
		BufferedWriter bufferedWriter = null ;
		BufferedReader bufferedReader = null ;
		
		try {
			conn = (HttpURLConnection) new URL (url).openConnection() ;
			// 객체 연결 설정 부분, 연결 시간, 데이터 대기 시간
			conn.setConnectTimeout(util.CONNECT_DELAY_TIME) ;
			conn.setReadTimeout(util.READ_DELAY_TIME) ;
			conn.setRequestMethod(util.POST) ;
			
			// Header Setting
			// 컨트롤 캐쉬 설정
			conn.setRequestProperty("Cache-Control", "no-cache") ;
			// 타입설정(application/json) 형식으로 전송 (Request Body 전달시 application/json로 서버에 전달.)
			//conn.setRequestProperty("Content-Type", "application/json") ;
			// Server Response JSON Type
			conn.setRequestProperty("Accept", "application/json") ;
			
			// Request, Response Type
			conn.setDoInput(true) ;
			conn.setDoOutput(true) ;
			
			outputStream = conn.getOutputStream();
			bufferedWriter = new BufferedWriter(new OutputStreamWriter (outputStream, util.UTF_8)) ;
			bufferedWriter.write(jsonMsg.toString()) ;

			// Request Body Write
			bufferedWriter.flush();
			 // Close Stream
			bufferedWriter.close() ;
			outputStream.close() ;
			
			String responseMsg = "" ;
			
			// HTTP 상태코드         
			// 200 인경우 성공
			if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			 
				String data = "" ;
				bufferedReader = new BufferedReader(new InputStreamReader (conn.getInputStream())) ;
				
				while ((data = bufferedReader.readLine()) != null)
					responseMsg += data ;
			}
			
			return responseMsg ;
		} catch (IOException e) {
			e.printStackTrace();
			
			return null ;
		} finally {
			// disConnection 
			if (conn != null) conn.disconnect() ;
			// Stream Close
			if (bufferedWriter != null) {
				try {
					bufferedWriter.close() ;
				} catch (IOException e) {
					bufferedWriter = null ;
				} 
			}
			if (outputStream != null) {
				try {
					outputStream.close() ;
				} catch (IOException e) {
					outputStream = null ;
				}
			}
			if (bufferedReader != null) {
				try {
					bufferedReader.close() ;
				} catch (IOException e) {
					bufferedReader = null ;
				}
			}
		}
	}
	
	public ContentValues postRequest (AMRRecommendRequestData amrData) {
		
		try {
			ContentValues jsonMsg = new ContentValues () ;
			jsonMsg.put(util.DATA, this.makeJson(amrData)) ;
							
			Log.d (util.TAG + util.POST + ": ", jsonMsg.toString()) ;
			
			return jsonMsg ;
		} catch (Exception e) {
			e.printStackTrace();
			
			return null ;
		}
	}
	
	// Json Paser
	public ArrayList<AMRRecommendResponseData> getResponseArrays (String responseMsg) {

		Log.d (util.TAG + "Response : " , responseMsg) ;
		
		return this.responsePaser(responseMsg) ;
	}
}
