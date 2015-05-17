package com.amr.aidl ;

interface amrAIDL {
	
	boolean getAnalyzeToRecommendLists (String analyzeData) ;
	
	boolean getInformationToRecommendLists (String artist, String title, String recvAction) ;
	
}