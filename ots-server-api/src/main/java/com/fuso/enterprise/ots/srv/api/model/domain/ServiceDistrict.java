package com.fuso.enterprise.ots.srv.api.model.domain;

public class ServiceDistrict {
	private String otsDistrictId;
	private String otsDistrictName;
	private String otsStateId;
	private String otsStateName;
	private String otsDistrictStatus;
	
	public String getOtsDistrictId() {
		return otsDistrictId;
	}
	public void setOtsDistrictId(String otsDistrictId) {
		this.otsDistrictId = otsDistrictId;
	}
	public String getOtsDistrictName() {
		return otsDistrictName;
	}
	public void setOtsDistrictName(String otsDistrictName) {
		this.otsDistrictName = otsDistrictName;
	}
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
	public String getOtsDistrictStatus() {
		return otsDistrictStatus;
	}
	public void setOtsDistrictStatus(String otsDistrictStatus) {
		this.otsDistrictStatus = otsDistrictStatus;
	}

}
