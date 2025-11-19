package com.fuso.enterprise.ots.srv.api.model.domain;

public class AssignServiceOrder {
	private String otsServiceOrderId;
	private String otsEmployeeId;
	
	public String getOtsServiceOrderId() {
		return otsServiceOrderId;
	}
	public void setOtsServiceOrderId(String otsServiceOrderId) {
		this.otsServiceOrderId = otsServiceOrderId;
	}
	public String getOtsEmployeeId() {
		return otsEmployeeId;
	}
	public void setOtsEmployeeId(String otsEmployeeId) {
		this.otsEmployeeId = otsEmployeeId;
	}
	
}
