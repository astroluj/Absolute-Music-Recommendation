package com.chartrecommend.adapter;

import java.util.ArrayList;

import com.amr.data.AMRData;
import com.chartrecommend.R;
import com.chartrecommend.util.util;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RecommendAdapter extends BaseAdapter {

	private Context context ;
	
	// List view arrays
    private ArrayList<String> musicIDList;
    private ArrayList<String> musicTitleList;
    private ArrayList<String> musicArtistList;
    private ArrayList<String> musicUriList;
    
    public RecommendAdapter (Context context) {
    	
    	this.context = context ;
    	
    	// ArrayList<String>
    	musicIDList = new ArrayList<String> () ;
    	musicTitleList = new ArrayList<String> () ;
    	musicArtistList = new ArrayList<String> () ;
    	musicUriList = new ArrayList<String> () ;
    }
    
    public boolean deleteSelected(int musicIndex){
        return true;
    }
    
    public int getCount() {
        return musicIDList.size();
    }
    
    public Object getItem(int position) {
        return position;
    }
    
    public long getItemId(int position) { 
        return position;
    }
    
    public int getMusicID(int position) {
        return Integer.parseInt((musicIDList.get(position)));
    }
    
    public String getMusicArtist (int position) {
    	try {
    		String artist = musicArtistList.get(position) ;
    		return (artist.equals(util.UNKNOWN)) ? null : artist ;
    	} catch (NullPointerException e) {
    		return null ;
    	}
    	
    }
    
    public String getMusicTitle (int position) {
    	try {
    		String title = musicTitleList.get(position) ;
    		return (title.equals(util.UNKNOWN)) ? null : title ;
    	} catch (NullPointerException e) {
    		return null ;
    	}
    }
    
    public String getMusicUri (int position) {
		return musicUriList.get(position) ;
    }
    
    // MusicUriList GetSet
    public ArrayList<String> getMusicUriList () {
    	return this.musicUriList ;
    }
    public void setMusicUriList (ArrayList<String> musicUriList) {
    	this.musicUriList = musicUriList ;
    }
    
    // MusicArtistList GetSet
    public ArrayList<String> getMusicArtisList () {
    	return this.musicArtistList ;
    }
    public void setMusicArtistList (ArrayList<String> musicArtistList) {
    	this.musicArtistList = musicArtistList ;
    }
    
    // MusicTitleList GetSet
    public ArrayList<String> getMusicTitleList () {
    	return this.musicTitleList ;
    }
    public void setMusicTitleList (ArrayList<String> musicTitleList) {
    	this.musicTitleList = musicTitleList ;
    }
    
    // MusicIDList GetSet
    public ArrayList<String> getMusicIDList () {
    	return this.musicIDList ;
    }
    public void setMusicIDList (ArrayList<String> musicIDList) {
    	this.musicIDList = musicIDList ;
    }

    public void putRecommendList (String track_id, String artist, String title, String uri) {
    	// put
    	musicIDList.add(track_id) ;
    	musicArtistList.add(artist) ;
    	musicTitleList.add(title) ;
    	musicUriList.add(uri) ;
    }

    public void putRecommendList (AMRData amrData) {
    	// put
    	musicIDList.add(amrData.getTrackID()) ;
    	musicArtistList.add(amrData.getArtist()) ;
    	musicTitleList.add(amrData.getTitle()) ;
    	musicUriList.add(amrData.getURL()) ;
    }
    
    public void putRecommendList (ArrayList<AMRData> amrDataList) {
    	
    	for (AMRData amrData : amrDataList) 
    		putRecommendList (amrData) ;
    }
    
    public void clearAdapter () {
    	// Remove all
    	musicIDList.removeAll(musicIDList) ;
    	musicArtistList.removeAll(musicArtistList) ;
    	musicTitleList.removeAll(musicTitleList) ;
    	musicUriList.removeAll(musicUriList) ;
    }
    
	public View getView(int position, View convertView, ViewGroup parent) {

		View listViewItem = convertView ; 
        if (listViewItem == null) {
            // Item.xml을 Inflate해 Layout 구성된 View를 얻는다.
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listViewItem = layoutInflater.inflate(R.layout.recommend_item, null);
        }
        // Album Bitmap을 얻는다. 
        ImageView albumImageView = (ImageView) listViewItem.findViewById (R.id.album_image);
        // Default Album Image
        albumImageView.setImageResource(R.drawable.equalizer);
         
        // Title 설정 
        TextView titleTextView = (TextView) listViewItem.findViewById (R.id.title) ;
        String title = musicTitleList.get(position) ;
        
        if (title == null)
        	titleTextView.setText("비슷한 음악을 찾을 수 없습니다.") ;
        else {
        	titleTextView.setText(title + "  ") ;
        	// Artist 설정
            TextView artistTextView = (TextView) listViewItem.findViewById(R.id.artist) ;
            artistTextView.setText(musicArtistList.get(position)) ;      
        }
 
        //구성된 ListView Item을 리턴해 준다.
        return listViewItem;
	}

}
