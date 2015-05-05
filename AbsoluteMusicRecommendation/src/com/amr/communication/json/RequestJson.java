package com.amr.communication.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
 
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class RequestJson {
     //final String URL ="http://10.100.10.70:29756/app/authUse.do" ;
     final String SPLIT ="||" ;
     
    private ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
 
        @Override
        public String handleResponse(HttpResponse response) throws IOException {
 
            int status = response.getStatusLine().getStatusCode(); // HTTP 상태코드         
                         
            if (status == HttpStatus.SC_OK) { // 200 인경우 성공
                HttpEntity entity = response.getEntity();
                
                return entity != null ? EntityUtils.toString(entity) : null;
            } else {
                ClientProtocolException e = new ClientProtocolException("Unexpected response status: " + status);
                throw e; // 상태코드 200이 아닌경우 예외발생
            }
 
        }
         
    };
     
	public String sendData(ArrayList<BasicNameValuePair> post, String URL) throws ClientProtocolException, IOException {
		
		// 연결 HttpClient 객체 생성
		HttpClient client = new DefaultHttpClient();
		try {
			// 객체 연결 설정 부분, 연결 시간, 데이터 대기 시간
			HttpParams params = client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 5000);
			HttpConnectionParams.setSoTimeout(params, 5000);
		 
			// Post객체 생성
			HttpPost httpPost = new HttpPost(URL);
			httpPost.setEntity(new UrlEncodedFormEntity (post, HTTP.UTF_8));
			
			return client.execute(httpPost, responseHandler);
		} catch (Exception e) {
			if (client != null) client.getConnectionManager().shutdown() ;
			
			return null ;
		}
	}
	

	public ArrayList<String> attendJsonParser (String responseData) {
		responseData ="[" +responseData +"]" ;

		try {
			JSONArray jsonArray =new JSONArray(responseData) ;
			for (int i =0 ; i < jsonArray.length() ; i++) {
				JSONObject body1 =jsonArray.getJSONObject(i) ;
				if (body1 != null) {
					JSONObject body2 =body1.getJSONObject("body") ;
					if (body2 != null) {
						JSONObject body3 =body2.getJSONObject("body") ;
						if (body3 != null) {
							JSONObject authUse =body3.getJSONObject("result") ;
							if (authUse != null) {
								StringTokenizer token =new StringTokenizer(authUse.getString("RESULT"), SPLIT) ;
								ArrayList<String> response =new ArrayList<String> () ;
								while (token.hasMoreTokens()) response.add(token.nextToken()) ;
								
								return response ;
							}
						}
					}
				}
			}
			
			return null ;
		} catch (JSONException e) {
			e.printStackTrace() ;
			Log.e ("JSON Exception", "responseData html or xml") ;
			
			return null ;
		} catch (Exception e) {
			return null ;
		}
	}
	
	public ArrayList<String> confirmJsonParser (String responseData) {
		responseData ="[" +responseData +"]" ;
		try {
			JSONArray jsonArray =new JSONArray(responseData) ;
			for (int i =0 ; i < jsonArray.length() ; i++) {
				JSONObject body1 =jsonArray.getJSONObject(i) ;
				if (body1 != null) {
					JSONObject body2 =body1.getJSONObject("body") ;
					if (body2 != null) {
						JSONObject body3 =body2.getJSONObject("body") ;
						if (body3 != null) {
							JSONObject authUse =body3.getJSONObject("authUse") ;
							if (authUse != null) {
								StringTokenizer token =new StringTokenizer(authUse.getString("AUTH_USE"), SPLIT) ;
								ArrayList<String> response =new ArrayList<String> () ;
								while (token.hasMoreTokens()) response.add(token.nextToken()) ;
								
								return response ;
							}
						}
					}
				}
			}
			
			return null ;
		} catch (JSONException e) {
			e.printStackTrace() ;
			Log.e ("JSON Exception", "responseData html or xml") ;
			
			return null ;
		} catch (Exception e) {
			return null ;
		}
	}
}
