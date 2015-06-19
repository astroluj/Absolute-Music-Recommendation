package com.chartrecommend.task;

import java.util.ArrayList;

import com.amr.AMR;
import com.amr.data.AMRData;
import com.chartrecommend.adapter.MusicAdapter;
import com.chartrecommend.adapter.RecommendAdapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

public class RecommendTask extends AsyncTask<String, Void, ArrayList<AMRData>>{

	private final String TAG = "RecommendTask : " ;
	
	// Custom Class
	private MusicAdapter musicAdapter ;
	private RecommendAdapter recommendAdapter ;
	
	private Context context ;
	private ProgressDialog progressDialog ;
	private ListView recommendListView ;
	private int position ;
	
	public RecommendTask (Context context,
			MusicAdapter musicAdapter, RecommendAdapter recommendAdapter, 
			ListView recommendListView, int position) {
	
		this.context = context ;
		this.musicAdapter = musicAdapter ;
		this.recommendAdapter = recommendAdapter ;
		this.recommendListView = recommendListView ;
		this.position = position ;
	}
	
	@Override
    protected void onPreExecute() {
        super.onPreExecute();
        
	     // Loading Progress
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Music Chart Recommendation");
        progressDialog.setMessage("Recommend List Loading...");
        progressDialog.setIndeterminate(false);
        progressDialog.show();
	}
		
	@Override
	protected ArrayList<AMRData> doInBackground(String... params) {
		
		return new AMR().getKeywordToRecommendLists(params[0],
				musicAdapter.getMusicArtist(position),
				musicAdapter.getMusicTitle(position), 10) ;
	}
	
	@Override
    protected void onPostExecute(ArrayList<AMRData> result) {
        super.onPostExecute(result);
         
        Log.d (TAG + "come :", "onPostExecute") ;
        
        // Recommed Adatper Init
        recommendAdapter.clearAdapter() ;
        
        // Set List View
        if(result == null || result.size() == 0) 
        	// Debug lists
			recommendAdapter.putRecommendList(null, null, null, null) ;
		else recommendAdapter.putRecommendList(result) ;
        
		// List View Shows
		recommendListView.setAdapter(recommendAdapter);
		
		// Clear Progress
        progressDialog.dismiss() ;
	}
}
