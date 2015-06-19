package com.example.testamr;

import java.util.ArrayList;

import com.amr.AMR;
import com.amr.data.AMRData;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

	public static final String TAG = "TestAMR View :" ;
	
	// Custom Class
	private AMR amr ;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		amr = new AMR () ;
		Log.d (TAG, "onCreate Come") ;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		try {
			switch (id) {
			case R.id.user_register:
				if (item.getTitle().equals(getString(R.string.user_register))) {
					item.setTitle(getString(R.string.user_remove)) ;
					viewAMRDataList(amr.setUserRegistered("test2")) ;
					viewAMRDataList(amr.setUserRegistered("test3")) ;
				}
				else {
					item.setTitle(getString(R.string.user_register)) ;
					viewAMRDataList(amr.setUserUnregistered("test2")) ;
					viewAMRDataList(amr.setUserUnregistered("test3")) ;
				}
				
				break ;
				
			case R.id.music_recommend:
				viewAMRDataList(amr.getKeywordToRecommendLists(null, "김종국", "한 남자", 10)) ;
				break ;
				
			case R.id.user_history:
				break ;
				
			case R.id.music_user:
				viewAMRDataList(amr.getMusicViewUserList("yNrbhmwkoTitukXs", 0, 10)) ;
				break ;
				
			case R.id.user_mate:
				if (item.getTitle().equals(getString(R.string.user_mate))) {
					item.setTitle(getString(R.string.user_unmate)) ;
					viewAMRDataList(amr.setMate("test3", "test2")) ;
				}
				else {
					item.setTitle(getString(R.string.user_mate)) ;
					viewAMRDataList(amr.setUnmate("test3", "teset2")) ;
				}
				
				break ;
				 
			case R.id.user_mate_list:
				viewAMRDataList(amr.getMateList("test3", 0, 10)) ;
				
				break ;
				
			case R.id.review_write:
				viewAMRDataList(amr.reviewWrite("test3", "yNrbhmwkoTitukXs", "ABCD")) ;
				
				break ;

			case R.id.music_review:
				viewAMRDataList(amr.getMusicReview("yNrbhmwkoTitukXs", 0, 10)) ;
				
				break ;
				
			case R.id.user_review:
				viewAMRDataList(amr.getUserReview("test3", 0, 10)) ;

				break ;
				
			case R.id.user_feed:
				viewAMRDataList(amr.getUserMatesViewList("test3", 0, 10)) ;
				
				break ;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	// AMR Data list
	private void viewAMRDataList (ArrayList<AMRData> responseData) {
		
		Log.d (TAG, responseData.size() + "") ;
		try {
			for (AMRData list : responseData) {

				try {
					Log.d (TAG + "track_id : ", list.getTrackID()) ;
				} catch (NullPointerException e) {}
				try {
					Log.d (TAG + "user_id : ", list.getUserID()) ;
				} catch (NullPointerException e) {}
				try {
					Log.d (TAG + "artist : ", list.getArtist()) ;
				} catch (NullPointerException e) {}
				try {
					Log.d (TAG + "title : ", list.getTitle()) ;
				} catch (NullPointerException e) {}
				try {
					Log.d (TAG + "album : ", list.getAlbum()) ;
				} catch (NullPointerException e) {}
				try {
					Log.d (TAG + "score : ", String.format("%.2f", list.getScore())) ;
				} catch (NullPointerException e) {}
				try {
					Log.d (TAG + "timeStamp : ", list.getTimeStamp()) ;
				} catch (NullPointerException e) {}
				try {
					Log.d (TAG + "content : ", list.getContent()) ;
				} catch (NullPointerException e) {}
				try {
					Log.d (TAG + "isAdditionalItems : ", list.isAdditionalItems() +"") ;
				} catch (NullPointerException e) {}
				try {
					Log.d (TAG + "Error Code : ", list.getErrorCode() +"") ;
				} catch (NullPointerException e) {}
				try {
					Log.d (TAG + "Error Description : ", list.getErrorDescription()) ;
				} catch (NullPointerException e) {}
				
				try {
					for (int i = 0 ; i < list.getTrack().size() ; i++) {
						try {
							Log.d (TAG + "track track_id : ", list.getTrack().get(i).getTrackID()) ;
						} catch (NullPointerException e) {}
						try {
							Log.d (TAG + "track artist : ", list.getTrack().get(i).getArtist()) ;
						} catch (NullPointerException e) {}
						try {
							Log.d (TAG + "track title : ", list.getTrack().get(i).getTitle()) ;
						} catch (NullPointerException e) {}
					}
				} catch (NullPointerException e) {}
			}
		} catch (Exception e) {}
	}
}
