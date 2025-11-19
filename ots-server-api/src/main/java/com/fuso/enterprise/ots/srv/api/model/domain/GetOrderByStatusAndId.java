package com.fuso.enterprise.ots.srv.api.model.domain;

public class GetOrderByStatusAndId {

	private String DistrubitorId;
	private String Status;
	public String getDistrubitorId() {
		return DistrubitorId;
	}
	public void setDistrubitorId(String distrubitorId) {
		DistrubitorId = distrubitorId;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	
	
}
