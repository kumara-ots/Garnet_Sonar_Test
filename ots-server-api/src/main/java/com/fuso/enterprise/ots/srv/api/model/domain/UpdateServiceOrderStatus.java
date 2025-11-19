package com.fuso.enterprise.ots.srv.api.model.domain;

public class UpdateServiceOrderStatus {
	private String otsServiceOrderId; 
	private String otsServiceOrderStatus;
	
	public String getOtsServiceOrderId() {
		return otsServiceOrderId;
	}
	public void setOtsServiceOrderId(String otsServiceOrderId) {
		this.otsServiceOrderId = otsServiceOrderId;
	}
	public String getOtsServiceOrderStatus() {
		return otsServiceOrderStatus;
	}
	public void setOtsServiceOrderStatus(String otsServiceOrderStatus) {
		this.otsServiceOrderStatus = otsServiceOrderStatus;
	}
	
	

}
