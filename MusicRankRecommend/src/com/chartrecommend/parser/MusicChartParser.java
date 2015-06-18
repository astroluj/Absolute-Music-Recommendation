package com.chartrecommend.parser;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
			
			for (int i = 0, size = musicInfoRows.size() ; i < size ; i++) {
				
				String thumbnailSrc = thumbnailRows.get(i).attr("src"), 
				title = musicInfoRows.get(i).getElementsByClass("tit").text() ;
				
				String[] result = splitStepTwo(" - ", musicInfoRows.get(i).getElementsByClass("stit").text()) ;
				
				musicDataList.add(new MusicData (thumbnailSrc, title, result[1], result[0])) ;
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
			
			for (int i = 0, size = musicInfoRows.size() ; i < size ; i++) {
				
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
				
				musicDataList.add(new MusicData (thumbnailSrc, title, artist, null)) ;
			}
			
			return musicDataList ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null ;
	}
	
	protected ArrayList<MusicData> responseItunesPaser (String url) {
		try {
			ArrayList<MusicData> musicDataList = new ArrayList<MusicData> () ;
			
			Document document = Jsoup.connect(url).get();
			
			// Thumbnail
			Elements thumbnailRows = document.select ("div.section-content ul li a img"),
					// MusicInfo
					musicInfoRows = document.select ("div.section-content ul li") ;
			
			for (int i = 0, size = musicInfoRows.size() ; i < size ; i++) {
				
				String thumbnailSrc = thumbnailRows.get(i).attr("src"), 
				title = musicInfoRows.get(i).getElementsByTag("H3").text(), 
				artist = musicInfoRows.get(i).getElementsByTag("H4").text() ;
				
				musicDataList.add(new MusicData (thumbnailSrc, title, artist, null)) ;
			}
			
			return musicDataList ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null ;
	}
	
	protected ArrayList<MusicData> responseGaonPaser (String url) {
		try {
			ArrayList<MusicData> musicDataList = new ArrayList<MusicData> () ;
			
			Document document = Jsoup.connect(url).get();
			
			// Thumbnail
			Elements thumbnailRows = document.select ("div.chart table tbody tr td.albumimg img"),
					// MusicInfo
					musicInfoRows = document.select ("div.chart table tbody tr td.subject") ;
			
			for (int i = 0, size = musicInfoRows.size() ; i < size ; i++) {
				
				String thumbnailSrc = null, title = null ;
				Elements tempElements = musicInfoRows.get(i).getElementsByTag("p") ;
				
				// thumbnail set
				if (thumbnailRows.size() > i)
					thumbnailSrc = thumbnailRows.get(i).attr("src") ;
				
				// musicinfo set
				title = tempElements.get(0).text() ;
				
				// arist, album separate and artist '(' remove
				String[] result = splitStepTwo ("|", tempElements.get(1).text()) ;
				
				musicDataList.add(new MusicData (thumbnailSrc, title, result[1], result[0])) ;
			}
			
			return musicDataList ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null ;
	}

	protected ArrayList<MusicData> responseMNetPaser (String url) {
		try {
			ArrayList<MusicData> musicDataList = new ArrayList<MusicData> () ;
			
			// Get Current Time ETC.YYYYMMddHH
			Date date = new Date (System.currentTimeMillis()) ;
			String currentDate = new SimpleDateFormat("yyyyMMddHH").format(date) ;
			
			for (int loop = 1 ; loop <= 2 ; loop++) {
				Document document = Jsoup.connect(url + currentDate + "?pNum=" + loop).get();
				
				// Thumbnail
				Elements thumbnailRows = document.select ("div.MMLTable table tbody tr td.MMLItemTitle div.MMLITitle_Wrap div.MMLITitle_Album a img"),
						// MusicInfo
						musicInfoRows = document.select ("div.MMLTable table tbody tr td.MMLItemTitle div.MMLITitle_Wrap div.MMLITitle_Box") ;
				
				// Site Recommend Music ignore
				for (int i = 1, size = musicInfoRows.size() ; i < size ; i++) {
					
					String thumbnailSrc = null ;
					
					// thumbnail set
					if (thumbnailRows.size() > i)
						thumbnailSrc = thumbnailRows.get(i).attr("src") ;
					
					String title = musicInfoRows.get(i).getElementsByClass("MMLI_Song").text(),
							artist = musicInfoRows.get(i).getElementsByClass("MMLIInfo_Artist").text(),
							album = musicInfoRows.get(i).getElementsByClass("MMLIInfo_Album").text() ;
					
					// artist '(' remove
					// '(' ignore
					int ignoreIndex = artist.indexOf(" (") ;
					if (ignoreIndex < 0)
						ignoreIndex = artist.indexOf("(") ;
					if (ignoreIndex > 0) artist = artist.substring(0, ignoreIndex) ;
					
					Log.d (TAG, thumbnailSrc + "   " + title +"   " + artist +"   " +album) ;
					musicDataList.add(new MusicData (thumbnailSrc, title, artist, album)) ;
				}
			}
			
			return musicDataList ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null ;
	}
	private String[] splitStepTwo (String split, String artist) {
	
		String[] result = new String[2] ;
		
		// Split str
		// artist & album split
		int exposeIndex = artist.indexOf(split) ;
		if (exposeIndex > 0)
			result[0] = artist.substring (exposeIndex + split.length(), artist.length()) ;
		// '(' ignore
		int ignoreIndex = artist.substring(0, (exposeIndex > 0) ? exposeIndex : artist.length()).indexOf(" (") ;
		if (ignoreIndex < 0)
			ignoreIndex = artist.substring(0, (exposeIndex > 0) ? exposeIndex : artist.length()).indexOf("(") ;
		
		result[1] = artist.substring(0, (ignoreIndex > 0) ? ignoreIndex : exposeIndex) ;
		
		return result ;
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
				
			case util.ITUNES :
				return responseItunesPaser(params[0]) ;
				
			case util.GAON :
				return responseGaonPaser(params[0]) ;
				
			case util.MNET :
				return responseMNetPaser(params[0]) ;
			}
		} catch (NullPointerException e) {
			Log.e (TAG + "Response : " , "null") ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
