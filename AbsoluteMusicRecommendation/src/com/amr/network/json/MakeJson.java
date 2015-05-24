package com.amr.network.json;

import org.json.JSONException;
import org.json.JSONStringer;

import com.amr.util.util;

public class MakeJson {

	private String artist, title ;
	private int startIndex, count ;
	
	public MakeJson (String artist, String title, int startIndex, int count) {
		
		this.artist = artist ;
		this.title = title ;
		this.startIndex = startIndex ;
		this.count = count ;
	}
	
	// Music/Search Json
	public String keywordMakeJson () {
		
		String jsonMsg = "" ;
		
		try {
			JSONStringer jsonStringer = new JSONStringer () ;
			
			jsonMsg = jsonStringer.object()
					.key(util.ARTIST).value(this.artist)
					.key(util.TITLE).value(this.title)
					.key(util.START).value(""+this.startIndex)
					.key(util.COUNT).value(""+this.count)
					.endObject().toString() ;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jsonMsg ;
	}
}
