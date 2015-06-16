package com.amr.aidl ;

interface amrAIDL {
	
	void getKeywordToRecommendLists (String recvAction, String uri,
		String artist, String title, int count) ;
	
	void setUserRegistered (String recvAction, String userID) ;
	void setUserUnregistered (String recvAction, String userID) ;
	void setUserPlay (String recvAction, String uri, 
		String userID, String artist, String title, String album) ;

	void getMusicViewUserList (String recvAction, String trackID, int start, int count) ;
	void getMatchedViewList (String recvAction, String reqUserID,
		String lookUpUserID, int start, int count) ;
	void getUnmatchedViewList (String recvAction, String reqUserID,
		String lookUpUserID, int start, int count) ;
		
	void setMate (String recvAction, String matingUserID, String matedUserID) ;
	void setUnmate (String recvAction, String unmatingUser_id, String unmatedUserID) ;
	
	void reviewWrite (String recvAction, String userID, String trackID, String content) ;
	void getMusicReview (String recvAction, String trackID, int start, int count) ;
	void getUserReview (String recvAction, String userID, int start, int count) ;
	void getUserMatesViewList (String recvAction, String userID, int start, int count) ;
}