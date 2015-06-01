package com.amr.network.json;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.amr.util.util;

import android.util.Log;

public class PostJson extends Json {

	// Network Handler
	private ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
		 
        @Override
        public String handleResponse(HttpResponse response) throws IOException {
 
            int status = response.getStatusLine().getStatusCode(); // HTTP 상태코드         
                         
            if (status == HttpStatus.SC_OK)  // 200 인경우 성공
                return response.getEntity() != null ? EntityUtils.toString(response.getEntity()) : null ;
            else {
                ClientProtocolException e = new ClientProtocolException("Unexpected response status: " + status);
                throw e; // 상태코드 200이 아닌경우 예외발생
            }
        }
    };
	
    public PostJson () {
    	super () ;
    }
    
    // Get Post type list
	public String sendData(ArrayList <BasicNameValuePair> jsonMsg, String url) {
		
		// 연결 HttpClient 객체 생성
		HttpClient client = new DefaultHttpClient();
		try {
			// 객체 연결 설정 부분, 연결 시간, 데이터 대기 시간
			HttpParams params = client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, util.CONNECT_DELAY_TIME);
			HttpConnectionParams.setSoTimeout(params, util.CONNECT_DELAY_TIME);
		 
			// Post객체 생성
			HttpPost httpPost = new HttpPost(url);
			// Entity 설정
			httpPost.setEntity(new UrlEncodedFormEntity (jsonMsg, HTTP.UTF_8));
			// Header Setting
			// 컨트롤 캐쉬 설정
			httpPost.setHeader("Cache-Control", "no-cache");
			// 타입설정(application/json) 형식으로 전송 (Request Body 전달시 application/json로 서버에 전달.)
			//httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
			// Server Response JSON Type
			httpPost.setHeader("Accept", "application/json");
			
			return client.execute(httpPost, responseHandler);
		} catch (Exception e) {
			if (client != null) client.getConnectionManager().shutdown() ;
			e.printStackTrace(); 
			return null ;
		}
	}
	
	public ArrayList <BasicNameValuePair> postRequest (String feature, String track_id,
			String artist, String title, 
			Object startIndex, Object count) {
		
		try {
			ArrayList<BasicNameValuePair> jsonMsg = new ArrayList<BasicNameValuePair> () ; 
			
			jsonMsg.add (new BasicNameValuePair(util.DATA, 
					this.searchMakeJson(feature, track_id, artist, title, 
							startIndex, count))) ; 
			
			Log.d (util.TAG + util.POST + ": ", jsonMsg.toString()) ;
			
			return jsonMsg ;
		} catch (Exception e) {
			return null ;
		}
	}
	
	// Json Paser
	public ArrayList<Paser> getResponseArrays (String responseMsg) {

		Log.d (util.TAG + "Response : " , responseMsg) ;
		
		return this.responsePaser(responseMsg) ;
	}
}
