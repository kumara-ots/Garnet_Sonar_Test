package com.fuso.enterprise.ots.srv.api.model.domain;

public class ChangePassword {

	private String UserID;
	
	private String pasword;

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getPasword() {
		return pasword;
	}

	public void setPasword(String pasword) {
		this.pasword = pasword;
	}

	
}
