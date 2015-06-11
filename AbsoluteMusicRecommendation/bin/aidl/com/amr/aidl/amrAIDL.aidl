package com.amr.aidl ;

interface amrAIDL {
	
	void getKeywordToRecommendLists (String uri, String recvAction, String artist, String title, int count) ;
	
	void setUserRegistered (String recvAction, String user_id) ;
	void setUserUnregistered (String recvAction, String user_id) ;
	void setUserPlay (String recvAction, String user_id, String artist, String title, String album) ;

	void setReview (String recvAction, String user_id, String track_id, String content) ;
	void getReview (String recvAction, int start, int count) ;
	void getUserReview (String recvAction, int start, int count) ;
}