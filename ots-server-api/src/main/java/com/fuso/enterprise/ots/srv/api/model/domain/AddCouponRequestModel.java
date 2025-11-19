package com.fuso.enterprise.ots.srv.api.model.domain;

public class AddCouponRequestModel {
	private String couponCode;
	private String couponDescription;
	private String couponDetails;
	private String couponStartDate;
	private String couponEndDate;
	private String couponUnit;
	private String couponPercentage;
	private String couponMaxPrice;
	private String couponMinPurchasePrice;
	private String couponPrice;
	private String couponStatus;
	private String couponBasedOn;
	private String adminId;
	
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public String getCouponDescription() {
		return couponDescription;
	}
	public void setCouponDescription(String couponDescription) {
		this.couponDescription = couponDescription;
	}
	public String getCouponDetails() {
		return couponDetails;
	}
	public void setCouponDetails(String couponDetails) {
		this.couponDetails = couponDetails;
	}
	public String getCouponStartDate() {
		return couponStartDate;
	}
	public void setCouponStartDate(String couponStartDate) {
		this.couponStartDate = couponStartDate;
	}
	public String getCouponEndDate() {
		return couponEndDate;
	}
	public void setCouponEndDate(String couponEndDate) {
		this.couponEndDate = couponEndDate;
	}
	public String getCouponUnit() {
		return couponUnit;
	}
	public void setCouponUnit(String couponUnit) {
		this.couponUnit = couponUnit;
	}
	public String getCouponPercentage() {
		return couponPercentage;
	}
	public void setCouponPercentage(String couponPercentage) {
		this.couponPercentage = couponPercentage;
	}
	public String getCouponMaxPrice() {
		return couponMaxPrice;
	}
	public void setCouponMaxPrice(String couponMaxPrice) {
		this.couponMaxPrice = couponMaxPrice;
	}
	public String getCouponMinPurchasePrice() {
		return couponMinPurchasePrice;
	}
	public void setCouponMinPurchasePrice(String couponMinPurchasePrice) {
		this.couponMinPurchasePrice = couponMinPurchasePrice;
	}
	public String getCouponPrice() {
		return couponPrice;
	}
	public void setCouponPrice(String couponPrice) {
		this.couponPrice = couponPrice;
	}
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
