package com.chartrecommend.adapter;

import java.util.ArrayList;

import com.chartrecommend.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**========================================== 
 *              Adapter class 
 * ==========================================*/
public class MusicAdapter extends BaseAdapter {
	
    private Context context;
    
    // List view arrays
    private ArrayList<String> musicAlbumList;
    private ArrayList<Bitmap> albumImageList;
    private ArrayList<String> musicTitleList;
    private ArrayList<String> musicArtistList;
    
    public MusicAdapter(Context context){
    	
    	this.context =  context ;
    	
    	// ArrayList<String>
    	musicAlbumList = new ArrayList<String> () ;
    	albumImageList = new ArrayList<Bitmap> () ;
    	musicTitleList = new ArrayList<String> () ;
    	musicArtistList = new ArrayList<String> () ;
    }
    
    public boolean deleteSelected(int musicIndex){
        return true;
    }
    
    public int getCount() {
        return musicAlbumList.size();
    }
    
    public Object getItem(int position) {
        return position;
    }
    
    public long getItemId(int position) { 
        return position;
    }
    
    public int getMusicID(int position) {
        return Integer.parseInt((musicAlbumList.get(position)));
    }
    
    public String getMusicArtist (int position) {
    	return musicArtistList.get(position) ;
    }
    
    public String getMusicTitle (int position) {
    	return musicTitleList.get(position) ;
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
    
    // AlbumImageList GetSet
    public ArrayList<Bitmap> getAlbumImageList () {
    	return this.albumImageList ;
    }
    public void setAlbumImageList (ArrayList<Bitmap> albumImageList) {
    	this.albumImageList = albumImageList ;
    }
    
    // MusicIDList GetSet
    public ArrayList<String> getMusicIDList () {
    	return this.musicAlbumList ;
    }
    public void setMusicIDList (ArrayList<String> musicAlbumList) {
    	this.musicAlbumList = musicAlbumList ;
    }
    
    public void putRecommendList (Bitmap albumImage, String artist, String title, String album) {
    	// put
    	musicAlbumList.add(album) ;
    	musicArtistList.add(artist) ;
    	musicTitleList.add(title) ;
    	albumImageList.add(albumImage) ;
    }
    
    public void clearAdapter () {
    	// Remove all
    	musicAlbumList.removeAll(musicAlbumList) ;
    	musicArtistList.removeAll(musicArtistList) ;
    	musicTitleList.removeAll(musicTitleList) ;
    	for (Bitmap bitmap : albumImageList)
    		bitmap.recycle() ;
    	albumImageList.removeAll(albumImageList) ;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
    	
        View listViewItem = convertView ; 
        if (listViewItem == null) {
            // Item.xml을 Inflate해 Layout 구성된 View를 얻는다.
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listViewItem = layoutInflater.inflate(R.layout.music_item, null);
        }
        // Album Bitmap을 얻는다. 
        ImageView albumImageView = (ImageView) listViewItem.findViewById (R.id.album_image);
        Bitmap albumImage = albumImageList.get(position) ;
        
        // Default Album Image
        if (albumImage != null)
        	albumImageView.setImageBitmap(albumImage) ;
         
        // Title 설정 
        TextView titleTextView = (TextView) listViewItem.findViewById (R.id.title) ;
        titleTextView.setText(musicTitleList.get(position)) ;
         
        // Artist 설정
        TextView artistTextView = (TextView) listViewItem.findViewById(R.id.artist) ;
        artistTextView.setText(musicArtistList.get(position)) ;            
 
        // Album 설정
        TextView albumTextView = (TextView) listViewItem.findViewById(R.id.album) ;
        albumTextView.setText(musicAlbumList.get(position)) ;
        
        //구성된 ListView Item을 리턴해 준다.
        return listViewItem;
    }
}
