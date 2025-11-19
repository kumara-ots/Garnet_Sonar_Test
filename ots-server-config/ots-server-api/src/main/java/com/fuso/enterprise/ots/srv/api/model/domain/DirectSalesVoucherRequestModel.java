package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.List;

public class DirectSalesVoucherRequestModel {

	private String customerId;
	
	private String employeeId;
	
	private String orderCost;
	
	private String deliveryDate;
	
	private String amountRecived;
	
	private String outstandingAmount;

	private List<OrderProductListRequest> orderProductlist;
	
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getOrderCost() {
		return orderCost;
	}

	public void setOrderCost(String orderCost) {
		this.orderCost = orderCost;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}


	public List<OrderProductListRequest> getOrderProductlist() {
		return orderProductlist;
	}

	public void setOrderProductlist(List<OrderProductListRequest> orderProductlist) {
		this.orderProductlist = orderProductlist;
	}

	public String getAmountRecived() {
		return amountRecived;
	}

	public void setAmountRecived(String amountRecived) {
		this.amountRecived = amountRecived;
	}

	public String getOutstandingAmount() {
		return outstandingAmount;
	}

	public void setOutstandingAmount(String outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}

	
}
