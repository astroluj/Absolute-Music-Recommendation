package com.chartrecommend.parser;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.chartrecommend.adapter.MusicAdapter;
import com.chartrecommend.data.MusicData;
import com.chartrecommend.util.util;

public class MusicChartParser extends AsyncTask<String, Void, ArrayList<MusicData>> {
	
	private final String TAG = "Music Chart Parser :" ;
	
	// Custom Class
	private MusicAdapter musicAdapter;
	
	private Context context ;
	
	private ListView musicListView;
	
	private ProgressDialog progressDialog;
	private int CASE ;
	
	public MusicChartParser (Context context,
			MusicAdapter musicAdapter, ListView musicListView, int CASE) {
		
		this.context = context ;
		this.musicAdapter = musicAdapter ;
		this.musicListView = musicListView ;
		this.CASE = CASE ;
	}
	
	// Paser
	private ArrayList<MusicData> responseNaverPaser (String url) {
		try {
			ArrayList<MusicData> musicDataList = new ArrayList<MusicData> () ;
			
			Document document = Jsoup.connect(url).get();
			// Thumbnail
			Elements thumbnailRows = document.select ("ul.cont_list li a.img img"),
					// MusicInfo
					musicInfoRows = document.select ("ul.cont_list li a.inner div.ab_inf") ;
			
			for (int i = 0, size = musicInfoRows.size() ; i <  size ; i++) {
				
				String thumbnailSrc = thumbnailRows.get(i).attr("src"), 
				title = musicInfoRows.get(i).getElementsByClass("tit").text(), 
				artist = musicInfoRows.get(i).getElementsByClass("stit").text(), album ="" ;
				
				// Split str
				String split = " - " ;
				// artist & album split
				int exposeIndex = artist.indexOf(split) ;
				if (exposeIndex > 0)
					album = artist.substring (exposeIndex + split.length(), artist.length()) ;
				// '(' ignore
				int ignoreIndex = artist.indexOf('(') ;
				artist = artist.substring(0, (ignoreIndex > 0) ? ignoreIndex : exposeIndex) ;
				
				musicDataList.add(new MusicData (thumbnailSrc, title, artist, album)) ;
			}
			
			return musicDataList ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null ;
	}
	
	protected ArrayList<MusicData> responseBillboardPaser (String url) {
		try {
			ArrayList<MusicData> musicDataList = new ArrayList<MusicData> () ;
			
			Document document = Jsoup.connect(url).get();
			
			// Thumbnail
			Elements thumbnailRows = document.select ("article.chart-row div.row-primary div.row-image"),
					// MusicInfo
					musicInfoRows = document.select ("article.chart-row div.row-primary div.row-title") ;
			
			for (int i = 0, size = musicInfoRows.size() ; i <  size ; i++) {
				
				String thumbnailSrc = thumbnailRows.get(i).attr("style"), 
				title = musicInfoRows.get(i).getElementsByTag("H2").text(), 
				artist = musicInfoRows.get(i).getElementsByTag("a").text() ;
				
				// thumbnailSrc getting url
				int urlIndex = thumbnailSrc.indexOf('(') ;
				if (urlIndex > 0)
					thumbnailSrc = thumbnailSrc.substring(urlIndex + 1, thumbnailSrc.length() - 1) ;
				else thumbnailSrc = thumbnailRows.get(i).attr("data-imagesrc") ;
				
				// Split str
				String split = " Featuring " ;
				// artist & album split
				int exposeIndex = artist.indexOf(split) ;
				if (exposeIndex > 0)
					artist = artist.substring(0, exposeIndex) ;
				
				Log.d (TAG, thumbnailSrc + "  " + title + "  "  + artist) ;
				musicDataList.add(new MusicData (thumbnailSrc, title, artist, null)) ;
			}
			
			return musicDataList ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null ;
	}

	@Override
    protected void onPreExecute() {
        super.onPreExecute();
        
        // Loading Progress
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Music Chart Recommendation");
        progressDialog.setMessage("Top 100 List Loading...");
        progressDialog.setIndeterminate(false);
        progressDialog.show();
    }
     
    @Override
    protected void onPostExecute(ArrayList<MusicData> result) {
        super.onPostExecute(result);
         
        // Set List View
        if(result != null){
        	
        	Log.d (TAG + "come :", "onPostExecute") ;
        	musicAdapter.putRecommendList(result) ;
        	musicListView.setAdapter(musicAdapter) ;
        }

        // Clear Progress
        progressDialog.dismiss() ;
    }

	@Override
	protected ArrayList<MusicData> doInBackground(String... params) {
		try {
			//Log.d (TAG + "Response : " , "Response Data : " + document) ;
			switch (CASE) {
			case util.NAVER :
				return responseNaverPaser(params[0]) ;
				
			case util.BILLBOARD :
				return responseBillboardPaser(params[0]) ;
				
			/*case util.ITUNES :
				return responseItunesPaser(params[0]) ;
				
			case util.GAON :
				return responseGaonPaser(params[0]) ;
				
			case util.GOMTV :
				return responseGomTVPaser(params[0]) ;
				
			case util.MNET :
				return responseMNetPaser(params[0]) ;*/
			}
		} catch (NullPointerException e) {
			Log.e (TAG + "Response : " , "null") ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
