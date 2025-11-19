package com.fuso.enterprise.ots.srv.api.model.domain;

public class GetUserStatus {

	private String userRoleId;

	private String userStatus;

	public String getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

}
