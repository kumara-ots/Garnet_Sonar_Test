package com.fuso.enterprise.ots.srv.api.model.domain;

public class CouponOrder {
	private String couponOrderId;
	private String couponId;
	private String orderId;
	
	public String getCouponOrderId() {
		return couponOrderId;
	}
	public void setCouponOrderId(String couponOrderId) {
		this.couponOrderId = couponOrderId;
	}
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}
