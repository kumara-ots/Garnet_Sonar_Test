package com.fuso.enterprise.ots.srv.api.model.domain;

public class ServiceState {
	private String otsStateId;
	private String otsStateName;
	private String otsStateStatus;
	
	public String getOtsStateId() {
		return otsStateId;
	}
	public void setOtsStateId(String otsStateId) {
		this.otsStateId = otsStateId;
	}
	public String getOtsStateName() {
		return otsStateName;
	}
	public void setOtsStateName(String otsStateName) {
		this.otsStateName = otsStateName;
	}
	public String getOtsStateStatus() {
		return otsStateStatus;
	}
	public void setOtsStateStatus(String otsStateStatus) {
		this.otsStateStatus = otsStateStatus;
	}

}
