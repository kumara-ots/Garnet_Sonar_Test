package com.fuso.enterprise.ots.srv.api.model.domain;

public class OrderProductDetailsSaleVocher {
	private String OrderProductId;
	private String OrderdId; 
	private String ProductId; 
	private String OrderedQty; 
	private String ProductCost;
	private String ProductStatus;
	private String DeliveredQty;
	private String ReceivedQty;
	
	public String getDeliveredQty() {
		return DeliveredQty;
	}
	public void setDeliveredQty(String deliveredQty) {
		DeliveredQty = deliveredQty;
	}
	
	public String getOrderdId() {
		return OrderdId;
	}
	public void setOrderdId(String orderdId) {
		OrderdId = orderdId;
	}
	public String getProductId() {
		return ProductId;
	}
	public void setProductId(String productId) {
		ProductId = productId;
	}
	public String getOrderedQty() {
		return OrderedQty;
	}
	public void setOrderedQty(String orderedQty) {
		OrderedQty = orderedQty;
	}
	public String getProductCost() {
		return ProductCost;
	}
	public void setProductCost(String productCost) {
		ProductCost = productCost;
	}
	public String getProductStatus() {
		return ProductStatus;
	}
	public void setProductStatus(String productStatus) {
		ProductStatus = productStatus;
	}
	public String getOts_delivered_qty() {
		return DeliveredQty;
	}
	public void setOts_delivered_qty(String DeliveredQty) {
		this.DeliveredQty = DeliveredQty;
	}
	public String getOrderProductId() {
		return OrderProductId;
	}
	public void setOrderProductId(String orderProductId) {
		OrderProductId = orderProductId;
	}
	public String getReceivedQty() {
		return ReceivedQty;
	}
	public void setReceivedQty(String receivedQty) {
		ReceivedQty = receivedQty;
	}
}
