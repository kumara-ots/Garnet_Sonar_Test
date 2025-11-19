package com.fuso.enterprise.ots.srv.api.model.domain;

public class SchedulerResponceOrderModel  {

	private String distributorId;
	
	private String requestOrderId;
	
	private String SchedulerId;
	
	private String customerId;
	
	private String productId;
	
	private String requestedQty;
	
	private String ScheduledDate;
	
	private String nxtScheduledDate;
	
	private String reqStatus;
	
	private String day;

	private ProductDetails productDetails;
	
	private UserDetails userDetails;
	
	public String getRequestOrderId() {
		return requestOrderId;
	}

	public void setRequestOrderId(String requestOrderId) {
		this.requestOrderId = requestOrderId;
	}

	public String getSchedulerId() {
		return SchedulerId;
	}

	public void setSchedulerId(String schedulerId) {
		SchedulerId = schedulerId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getRequestedQty() {
		return requestedQty;
	}

	public void setRequestedQty(String requestedQty) {
		this.requestedQty = requestedQty;
	}

	public String getScheduledDate() {
		return ScheduledDate;
	}

	public void setScheduledDate(String scheduledDate) {
		ScheduledDate = scheduledDate;
	}

	public String getNxtScheduledDate() {
		return nxtScheduledDate;
	}

	public void setNxtScheduledDate(String nxtScheduledDate) {
		this.nxtScheduledDate = nxtScheduledDate;
	}

	public String getReqStatus() {
		return reqStatus;
	}

	public void setReqStatus(String reqStatus) {
		this.reqStatus = reqStatus;
	}

	public String getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}

	public ProductDetails getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(ProductDetails productDetails) {
		this.productDetails = productDetails;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
	
	
}
