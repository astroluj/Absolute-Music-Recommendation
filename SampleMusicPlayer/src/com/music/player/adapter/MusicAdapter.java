package com.music.player.adapter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.music.player.R;
import com.music.player.util.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
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
    
    // Album ID로 부터 Bitmap을 생성해 리턴해 주는 메소드
    private static final BitmapFactory.Options BitmapOptionsCache = new BitmapFactory.Options() ;
    private static final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
     
    // List view arrays
    private ArrayList<String> musicIDList;
    private ArrayList<String> albumImageList;
    private ArrayList<String> musicTitleList;
    private ArrayList<String> musicArtistList;
    
    public MusicAdapter(Context context){
    	
    	this.context =  context ;
    	
    	// ArrayList<String>
    	musicIDList = new ArrayList<String> () ;
    	albumImageList = new ArrayList<String> () ;
    	musicTitleList = new ArrayList<String> () ;
    	musicArtistList = new ArrayList<String> () ;
    	
        getMusicInfo();
    }
    
    private void getMusicInfo(){
        // get Media의 ID, Album의 ID
        String[] musicInfo = {MediaStore.Audio.Media._ID, 
                MediaStore.Audio.Media.ALBUM_ID, 
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.IS_MUSIC
        } ;

        Cursor musicCursor = context.getContentResolver()
        		.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
        				musicInfo, null, null, null) ;
     
        if (musicCursor != null && musicCursor.moveToFirst()){
        	String musicID, albumImageID, musicTitle, musicArtist ;

            int musicIDCol = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int albumImageIDCol = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            int musicTitleCol = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int musicArtistCol = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int musicDurationCol = musicCursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC);
            
            do {
            	try {
            		// 짧은 음악 제외 시키기
            		if (musicCursor.getInt(musicDurationCol) > 0) {
		                musicID = musicCursor.getString(musicIDCol) ;
		                albumImageID = musicCursor.getString(albumImageIDCol) ;
		                musicTitle = musicCursor.getString(musicTitleCol) ;
		                musicArtist = musicCursor.getString(musicArtistCol) ;
		                
		                // Media ID와 Album ID를 각각의 리스트에 저장해 둔다 
		                musicIDList.add(musicID);
		                albumImageList.add(albumImageID);
		                musicTitleList.add(musicTitle);
		                musicArtistList.add(musicArtist);
            		}
            	} catch (Exception e) {}
            }while (musicCursor.moveToNext());
        }
        // close cursor
        musicCursor.close();
  
        return;
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
    public ArrayList<String> getAlbumImageList () {
    	return this.albumImageList ;
    }
    public void setAlbumImageList (ArrayList<String> albumImageList) {
    	this.albumImageList = albumImageList ;
    }
    
    // MusicIDList GetSet
    public ArrayList<String> getMusicIDList () {
    	return this.musicIDList ;
    }
    public void setMusicIDList (ArrayList<String> musicIDList) {
    	this.musicIDList = musicIDList ;
    }
    
    public Bitmap getAlbumImage (int position) {
    	
    	Bitmap albumImage = getArtworkQuick(context, Integer.parseInt((albumImageList.get(position))), 50, 50);
    	// Default Album Image
    	
		return (albumImage == null) ? BitmapFactory.decodeResource(
				context.getResources(), R.drawable.equalizer) : albumImage ;
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
        Bitmap albumImage = getArtworkQuick(context, Integer.parseInt((albumImageList.get(position))), 50, 50);
        
        // Default Album Image
        if (albumImage == null)
        	albumImageView.setImageResource(R.drawable.equalizer);
        else albumImageView.setImageBitmap(albumImage) ;
         
        // Title 설정 
        TextView titleTextView = (TextView) listViewItem.findViewById (R.id.title) ;
        titleTextView.setText(musicTitleList.get(position)) ;
         
        // Artist 설정
        TextView artistTextView = (TextView) listViewItem.findViewById(R.id.artist) ;
        artistTextView.setText(musicArtistList.get(position)) ;            
 
        //구성된 ListView Item을 리턴해 준다.
        return listViewItem;
    }
    
    // Get album art for specified album. This method will not try to
    // fall back to getting artwork directly from the file, nor will
    // it attempt to repair the database.
    private static Bitmap getArtworkQuick(Context context, int album_id, int width, int height) {
        // NOTE: There is in fact a 1 pixel frame in the ImageView used to
        // display this drawable. 
    	// Take it into account now, so we don't have to
        // scale later.
        width -= 2 ;
        height -= 2 ;
        
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);
        if (uri != null) {
            ParcelFileDescriptor fileDescriptor = null ;
            try {
            	fileDescriptor = contentResolver.openFileDescriptor(uri, "r") ;
                int sampleSize = 1 ;
                 
                // Compute the closest power-of-two scale factor 
                // and pass that to sBitmapOptionsCache.inSampleSize, which will
                // result in faster decoding and better quality
                BitmapOptionsCache.inJustDecodeBounds = true;
                BitmapFactory.decodeFileDescriptor(
                		fileDescriptor.getFileDescriptor(), null, BitmapOptionsCache) ;
                
                int nextWidth = BitmapOptionsCache.outWidth >> 1 ;
                int nextHeight = BitmapOptionsCache.outHeight >> 1 ;
                
                while (nextWidth > width && nextHeight > height) {
                    sampleSize <<= 1 ;
                    nextWidth >>= 1 ;
                    nextHeight >>= 1 ;
                }
     
                BitmapOptionsCache.inSampleSize = sampleSize ;
                BitmapOptionsCache.inJustDecodeBounds = false ;
                Bitmap bitmap = BitmapFactory.decodeFileDescriptor(
                		fileDescriptor.getFileDescriptor(), null, BitmapOptionsCache) ;
     
                if (bitmap != null) {
                    // finally rescale to exactly the size we need
                    if (BitmapOptionsCache.outWidth != width
                    		|| BitmapOptionsCache.outHeight != height) {
                        Bitmap tmp = Bitmap.createScaledBitmap(bitmap, width, height, true) ; 
                        bitmap.recycle() ;
                        bitmap = tmp ;
                    }
                }
                 
                return bitmap ;
            } catch (FileNotFoundException e) {
            } catch (IllegalStateException e) {
            	
            } finally {
                try {
                    if (fileDescriptor != null)
                    	fileDescriptor.close() ;
                } catch (IOException e) {
                }
            }
        }
        
        return null ;
    }
}
