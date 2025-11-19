package com.fuso.enterprise.ots.srv.api.model.domain;

public class GetUserDetailsBORequest {
	
	String searchKey;
	String searchvalue;
	String distributorId;
	String userLat;
	String UserLong;
	String CreatedUser;
	
	public String getCreatedUser() {
		return CreatedUser;
	}
	public void setCreatedUser(String createdUser) {
		CreatedUser = createdUser;
	}
	public String getUserLat() {
		return userLat;
	}
	public void setUserLat(String userLat) {
		this.userLat = userLat;
	}
	public String getUserLong() {
		return UserLong;
	}
	public void setUserLong(String userLong) {
		UserLong = userLong;
	}
	public String getDistributorId() {
		return distributorId;
	}
	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public String getSearchvalue() {
		return searchvalue;
	}
	public void setSearchvalue(String searchvalue) {
		this.searchvalue = searchvalue;
	}
	
}
