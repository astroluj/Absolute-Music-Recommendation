package com.amr.data;

public class UserData {

	private String user_id ;
	private boolean isRemove ;
	
	public UserData () {
		this.user_id = "" ;
		this.isRemove = false ;
	}
	
	public UserData (String user_id, boolean isRemove) {
		this.user_id = user_id ;
		this.isRemove = isRemove ;
	}
	
	public String getUserID () {
		return this.user_id ;
	}
	public void setUserID (String user_id) {
		this.user_id = user_id ;
	}
	
	public boolean getIsRemove () {
		return this.isRemove ;
	}
	public void setIsRemove (boolean isRemove) {
		this.isRemove = isRemove ;
	}
}
