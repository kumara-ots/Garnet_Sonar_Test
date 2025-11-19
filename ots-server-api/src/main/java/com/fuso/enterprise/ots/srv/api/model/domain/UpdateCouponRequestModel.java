package com.fuso.enterprise.ots.srv.api.model.domain;

public class UpdateCouponRequestModel {
	private String couponId;
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
	
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
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
	
}
