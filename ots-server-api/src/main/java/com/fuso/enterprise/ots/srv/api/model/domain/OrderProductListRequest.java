package com.fuso.enterprise.ots.srv.api.model.domain;

public class OrderProductListRequest {

	private String prodcutId;
	
	private String productCost;
	
	private String orderQty;
	
	private String emptyCan;
	
	private String productbalanceQty;
	
	private String deliveredQty;

	public String getProdcutId() {
		return prodcutId;
	}

	public void setProdcutId(String prodcutId) {
		this.prodcutId = prodcutId;
	}

	public String getProductCost() {
		return productCost;
	}

	public void setProductCost(String productCost) {
		this.productCost = productCost;
	}


	public String getEmptyCan() {
		return emptyCan;
	}

	public void setEmptyCan(String emptyCan) {
		this.emptyCan = emptyCan;
	}

	public String getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(String orderQty) {
		this.orderQty = orderQty;
	}

	public String getProductbalanceQty() {
		return productbalanceQty;
	}

	public void setProductbalanceQty(String productbalanceQty) {
		this.productbalanceQty = productbalanceQty;
	}

	public String getDeliveredQty() {
		return deliveredQty;
	}

	public void setDeliveredQty(String deliveredQty) {
		this.deliveredQty = deliveredQty;
	}

	
}
