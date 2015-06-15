package com.amr.data;

public class UserData {

	private String userID, name ;
	private boolean isRemove ;
	
	public UserData () {
		this.userID = "" ;
		this.name ="" ;
		this.isRemove = false ;
	}
	
	public UserData (String userID, String name) {
		this.userID = userID ;
		this.name = name ; 
		this.isRemove = false ;
	}
	
	public UserData (String userID, String name, boolean isRemove) {
		this.userID = userID ;
		this.name = name ;
		this.isRemove = isRemove ;
	}
	
	public String getUserID () {
		return this.userID ;
	}
	public void setUserID (String userID) {
		this.userID = userID ;
	}
	
	public String getName () {
		return this.name ;
	}
	public void setName (String name) {
		this.name = name ;
	}
	
	public boolean getIsRemove () {
		return this.isRemove ;
	}
	public void setIsRemove (boolean isRemove) {
		this.isRemove = isRemove ;
	}
}
