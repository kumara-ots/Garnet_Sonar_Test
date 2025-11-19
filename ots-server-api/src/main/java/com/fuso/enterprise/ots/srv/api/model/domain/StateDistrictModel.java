package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.List;

public class StateDistrictModel {
	private String otsStateId;
	private String otsStateName;
	private String otsStateStatus;
	private List<ServiceDistrict> districts;
	
	public List<ServiceDistrict> getDistricts() {
		return districts;
	}
	public void setDistricts(List<ServiceDistrict> districts) {
		this.districts = districts;
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
	public String getOtsStateStatus() {
		return otsStateStatus;
	}
	public void setOtsStateStatus(String otsStateStatus) {
		this.otsStateStatus = otsStateStatus;
	}
}
