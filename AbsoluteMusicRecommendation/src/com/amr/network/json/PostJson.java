package com.amr.network.json;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;
 







import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
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

import com.amr.util.util;

import android.util.Log;

public class PostJson {

	private ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
 
        @Override
        public String handleResponse(HttpResponse response) throws IOException {
 
            int status = response.getStatusLine().getStatusCode(); // HTTP 상태코드         
                         
            if (status == HttpStatus.SC_OK) { // 200 인경우 성공
                HttpEntity entity = response.getEntity();
                
                return entity != null ?EntityUtils.toString(entity) : null ;
            } else {
                ClientProtocolException e = new ClientProtocolException("Unexpected response status: " + status);
                throw e; // 상태코드 200이 아닌경우 예외발생
            }
 
        }
         
    };
     
    // Get Post type list
	public String sendData(ArrayList<BasicNameValuePair> post, String URL) throws ClientProtocolException, IOException {
		
		// 연결 HttpClient 객체 생성
		HttpClient client = new DefaultHttpClient();
		try {
			// 객체 연결 설정 부분, 연결 시간, 데이터 대기 시간
			HttpParams params = client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, util.CONNECT_DELAY_TIME);
			HttpConnectionParams.setSoTimeout(params, util.CONNECT_DELAY_TIME);
		 
			// Post객체 생성
			HttpPost httpPost = new HttpPost(URL);
			httpPost.setEntity(new UrlEncodedFormEntity (post, HTTP.UTF_8));
			
			return client.execute(httpPost, responseHandler);
		} catch (Exception e) {
			if (client != null) client.getConnectionManager().shutdown() ;
			e.printStackTrace(); 
			return null ;
		}
	}
	
	public ArrayList<String> getRecommendLists (String responseData) {
		responseData = responseData ;

		Log.d (util.TAG, responseData) ;
		return null ;
		/*‘track_id’: <track_id string>,
		‘artist’: <string>,
		‘title’: <string>,
		‘url’: <string>
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
		}*/
	}
	
	public void getRecommendLists (MakeJson json, URL url) {
		
		Log.d (util.TAG, json.keywordMakeJson()) ;
		// URL Connection
		try {
			HttpURLConnection  httpConnection = (HttpURLConnection)url.openConnection () ;
			// Server Connection Time
			httpConnection.setConnectTimeout(util.CONNECT_DELAY_TIME);
			// Data Read Time
			httpConnection.setReadTimeout(util.READ_DELAY_TIME);
			// Request Type
			httpConnection.setRequestMethod(util.POST);
			// 컨트롤 캐쉬 설정
			httpConnection.setRequestProperty("Cache-Control", "no-cache");
			// 타입설정(application/json) 형식으로 전송 (Request Body 전달시 application/json로 서버에 전달.)
			httpConnection.setRequestProperty("Content-Type", "application/json");
			/* Other type
			httpConnection.setRequestProperty("Content-Type", "text/html");
			httpConnection.setRequestProperty("Content-Type", "application/xml"); */
			// Server Response JSON Type
			httpConnection.setRequestProperty("Accept", "application/json");
			/* Other type
			 httpConnection.setRequestProperty("Accept", "application/xml"); */
			// Request to OutputStream 
			httpConnection.setDoOutput(true);
			// Response to InputStream
			httpConnection.setDoInput(true);
			
			// Request Body에 Data를 담기위해 OutputStream 객체를 생성.
			OutputStream outStream = httpConnection.getOutputStream() ;
			// Write Bytes
			outStream.write(json.keywordMakeJson().getBytes()) ;
			outStream.flush(); 
			 
			// Get ResponseCode
			int responseCode = httpConnection.getResponseCode();
			
			// CODE == 200
			if(responseCode == HttpURLConnection.HTTP_OK) {
				
				InputStream inStream = httpConnection.getInputStream() ;
				ByteArrayOutputStream byteArrayOutStream = new ByteArrayOutputStream() ;
				
			    byte[] byteBuffer = new byte[1024];
			    int length = 0;
			    while((length = inStream.read(byteBuffer, 0, byteBuffer.length)) != -1) {
			    	byteArrayOutStream.write(byteBuffer, 0, length);
			    }
			     
			    Log.d (util.TAG, new String(byteArrayOutStream.toByteArray()));
			}
			else Log.d (util.TAG, "Return Response Code Not 200") ;
		} catch (IOException e) {
			Log.e (util.TAG," IOException") ;
		} catch (Exception e) {}
	}
}
