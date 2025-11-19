package com.fuso.enterprise.ots.srv.api.model.domain;

public class GetServiceCustomerOrdersByStatus {
	
	private String otsServiceCustomerId;
	private String otsServiceOrderStatus;
	
	public String getOtsServiceCustomerId() {
		return otsServiceCustomerId;
	}
	public void setOtsServiceCustomerId(String otsServiceCustomerId) {
		this.otsServiceCustomerId = otsServiceCustomerId;
	}
	public String getOtsServiceOrderStatus() {
		return otsServiceOrderStatus;
	}
	public void setOtsServiceOrderStatus(String otsServiceOrderStatus) {
		this.otsServiceOrderStatus = otsServiceOrderStatus;
	}
	
	

}
