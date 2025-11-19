package com.fuso.enterprise.ots.srv.api.model.domain;

public class GetRRCOrdersByStatus {
	private String distributorId;
	private String rrcOrderStatus;
	
	public String getDistributorId() {
		return distributorId;
	}
	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}
	public String getRrcOrderStatus() {
		return rrcOrderStatus;
	}
	public void setRrcOrderStatus(String rrcOrderStatus) {
		this.rrcOrderStatus = rrcOrderStatus;
	}
	
}
