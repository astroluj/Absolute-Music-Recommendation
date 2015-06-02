package com.amr.aidl ;

interface amrAIDL {
	
	void getAnalyzeToRecommendLists (String recvAction, String analyzeData, int count) ;
	
	void getKeywordToRecommendLists (String recvAction, String artist, String title, int count) ;
	
}