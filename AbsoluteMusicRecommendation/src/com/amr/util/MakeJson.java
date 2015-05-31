package com.amr.util;

import org.json.JSONException;
import org.json.JSONStringer;

public class MakeJson {
	
	// Music/Search Json
	public static String keywordMakeJson (String artist, String title, int startIndex, int count) {
		
		String jsonMsg = "" ;
		
		try {
			JSONStringer jsonStringer = new JSONStringer () ;
			
			jsonMsg = jsonStringer.object()
					.key(util.ARTIST).value(artist)
					.key(util.TITLE).value(title)
					.key(util.START).value(startIndex)
					.key(util.COUNT).value(count)
					.endObject().toString() ;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jsonMsg ;
	}
}
