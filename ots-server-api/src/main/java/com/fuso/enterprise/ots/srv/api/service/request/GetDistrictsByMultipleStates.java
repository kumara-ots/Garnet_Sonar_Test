package com.fuso.enterprise.ots.srv.api.service.request;

import java.util.List;

public class GetDistrictsByMultipleStates {
	private List<String> stateId;

	public List<String> getStateId() {
		return stateId;
	}

	public void setStateId(List<String> stateId) {
		this.stateId = stateId;
	}
	
}
