package com.amr.aidl ;

interface amrAIDL {
	
	boolean getAnalyzeToRecommendLists (String recvAction, String analyzeData, int count) ;
	
	boolean getKeywordToRecommendLists (String recvAction, String artist, String title, int count) ;
	
}