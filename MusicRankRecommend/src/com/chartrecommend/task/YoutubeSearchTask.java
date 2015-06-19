package com.chartrecommend.task;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.chartrecommend.util.util;
import com.chartrecommend.youtube.YoutubeOnInitialize;
import com.google.android.youtube.player.YouTubePlayerView;

import android.os.AsyncTask;
import android.util.Log;

public class YoutubeSearchTask extends AsyncTask<String, Void, String> {

	private YouTubePlayerView youtubeView ;
	
	public YoutubeSearchTask(YouTubePlayerView youtubeView) {

		this.youtubeView = youtubeView ;
	}
	
	@Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
 
    @Override
    protected String doInBackground(String... params) {
        try {
            return paringJsonData(getYoutube(params[0])) ;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        	e.printStackTrace();
        }
 
        return null ;
    }
    
    @Override
    protected void onPostExecute(String result) {
    	youtubeView.initialize(util.YOUTTUBE_API_KEY, new YoutubeOnInitialize (result)) ;
    }
 
    public JSONObject getYoutube (String data) {
 
        HttpGet httpGet = new HttpGet(
                "https://www.googleapis.com/youtube/v3/search?"
                        + "part=snippet&q=" + data
                        + "&key="+ util.YOUTTUBE_API_KEY) ; 
                            // part(snippet),  q(검색값) , key(서버키)
        
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();
 
        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }
 
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
 
        return jsonObject;
    }
 
    //파싱을 하면 여러가지 값을 얻을 수 있는데 필요한 값들을 세팅하셔서 사용하시면 됩니다.
    private String paringJsonData(JSONObject jsonObject) throws JSONException {
        //sdata.clear();
    	JSONArray contacts = jsonObject.getJSONArray("items");
 
        // One Catch
        JSONObject c = contacts.getJSONObject(0);
       return c.getJSONObject("id").getString("videoId") ;  //유튜브 동영상 아이디 값입니다. 재생시 필요합니다.
 
        /*
     	String title = c.getJSONObject("snippet").getString("title"); //유튜브 제목을 받아옵니다
        String changString = "";
        try {
            changString = new String(title.getBytes("8859_1"), "utf-8"); //한글이 깨져서 인코딩 해주었습니다
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
 
        String date = c.getJSONObject("snippet").getString("publishedAt") //등록날짜
                .substring(0, 10);
        String imgUrl = c.getJSONObject("snippet").getJSONObject("thumbnails")
                .getJSONObject("default").getString("url");  //썸내일 이미지 URL값*/
    }
}
