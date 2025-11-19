package com.fuso.enterprise.ots.srv.api.model.domain;

public class UnRegisteredDistWeeklySettlementDetailsForExcel {
	private String slno;
	private String orderNumber;
	private String subOrderNumber;
	private String distributorName;
	private String productName;
	private String orderedQty;
	private String orderDeliveredDate;
	private String orderProductPrice;
	private String productPrice;
	private String sellerPrice;
	private String threePercProfit;
	private String onePercTds;
	private String eighteenPercGst;
	private String llpPayablePrice;
	private String pvtPayablePrice;
	private String distributorPayablePrice;
	private String paymentSettlementStatus;
	
	public String getPaymentSettlementStatus() {
		return paymentSettlementStatus;
	}
	public void setPaymentSettlementStatus(String paymentSettlementStatus) {
		this.paymentSettlementStatus = paymentSettlementStatus;
	}
	public String getSlno() {
		return slno;
	}
	public void setSlno(String slno) {
		this.slno = slno;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getSubOrderNumber() {
		return subOrderNumber;
	}
	public void setSubOrderNumber(String subOrderNumber) {
		this.subOrderNumber = subOrderNumber;
	}
	public String getDistributorName() {
		return distributorName;
	}
	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getOrderedQty() {
		return orderedQty;
	}
	public void setOrderedQty(String orderedQty) {
		this.orderedQty = orderedQty;
	}
	public String getOrderDeliveredDate() {
		return orderDeliveredDate;
	}
	public void setOrderDeliveredDate(String orderDeliveredDate) {
		this.orderDeliveredDate = orderDeliveredDate;
	}
	public String getOrderProductPrice() {
		return orderProductPrice;
	}
	public void setOrderProductPrice(String orderProductPrice) {
		this.orderProductPrice = orderProductPrice;
	}
	public String getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}
	public String getSellerPrice() {
		return sellerPrice;
	}
	public void setSellerPrice(String sellerPrice) {
		this.sellerPrice = sellerPrice;
	}
	public String getThreePercProfit() {
		return threePercProfit;
	}
	public void setThreePercProfit(String threePercProfit) {
		this.threePercProfit = threePercProfit;
	}
	public String getOnePercTds() {
		return onePercTds;
	}
	public void setOnePercTds(String onePercTds) {
		this.onePercTds = onePercTds;
	}
	public String getEighteenPercGst() {
		return eighteenPercGst;
	}
	public void setEighteenPercGst(String eighteenPercGst) {
		this.eighteenPercGst = eighteenPercGst;
	}
	public String getLlpPayablePrice() {
		return llpPayablePrice;
	}
	public void setLlpPayablePrice(String llpPayablePrice) {
		this.llpPayablePrice = llpPayablePrice;
	}
	public String getPvtPayablePrice() {
		return pvtPayablePrice;
	}
	public void setPvtPayablePrice(String pvtPayablePrice) {
		this.pvtPayablePrice = pvtPayablePrice;
	}
	public String getDistributorPayablePrice() {
		return distributorPayablePrice;
	}
	public void setDistributorPayablePrice(String distributorPayablePrice) {
		this.distributorPayablePrice = distributorPayablePrice;
	}
	
}
