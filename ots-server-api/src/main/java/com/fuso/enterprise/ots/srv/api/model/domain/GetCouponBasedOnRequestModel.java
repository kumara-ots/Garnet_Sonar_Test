package com.fuso.enterprise.ots.srv.api.model.domain;

public class GetCouponBasedOnRequestModel {
	private String couponStatus;
	private String couponBasedOn;
	private String adminId;
	
	public String getCouponStatus() {
		return couponStatus;
	}
	public void setCouponStatus(String couponStatus) {
		this.couponStatus = couponStatus;
	}
	public String getCouponBasedOn() {
		return couponBasedOn;
	}
	public void setCouponBasedOn(String couponBasedOn) {
		this.couponBasedOn = couponBasedOn;
	}
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
}
