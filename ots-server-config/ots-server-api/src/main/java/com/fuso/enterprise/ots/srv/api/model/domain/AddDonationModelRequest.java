package com.fuso.enterprise.ots.srv.api.model.domain;

public class AddDonationModelRequest {

	private String productId;
	
	private String donorId;
	
	private String dontaionAmount;
	
	private String donatedQty;
	
	private String paymentId;
	
	private String presentStock;
	
	private String requestStatus;
	
	private String donationRequestId;
	
	private String donationStatus;
	
	private String orderId;
	
	private String description;
	
	private String panNumber;
	
	private String otherNumber;
	
	private String donationMethod;
	
	private String orderQty;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getOtherNumber() {
		return otherNumber;
	}

	public void setOtherNumber(String otherNumber) {
		this.otherNumber = otherNumber;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getDontaionAmount() {
		return dontaionAmount;
	}

	public void setDontaionAmount(String dontaionAmount) {
		this.dontaionAmount = dontaionAmount;
	}

	public String getDonatedQty() {
		return donatedQty;
	}

	public void setDonatedQty(String donatedQty) {
		this.donatedQty = donatedQty;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getPresentStock() {
		return presentStock;
	}

	public void setPresentStock(String presentStock) {
		this.presentStock = presentStock;
	}

	public String getDonationStatus() {
		return donationStatus;
	}

	public void setDonationStatus(String donationStatus) {
		this.donationStatus = donationStatus;
	}

	public String getDonationRequestId() {
		return donationRequestId;
	}

	public void setDonationRequestId(String donationRequestId) {
		this.donationRequestId = donationRequestId;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getDonorId() {
		return donorId;
	}

	public void setDonorId(String donorId) {
		this.donorId = donorId;
	}

	public String getDonationMethod() {
		return donationMethod;
	}

	public void setDonationMethod(String donationMethod) {
		this.donationMethod = donationMethod;
	}

	public String getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(String orderQty) {
		this.orderQty = orderQty;
	}
	
}
