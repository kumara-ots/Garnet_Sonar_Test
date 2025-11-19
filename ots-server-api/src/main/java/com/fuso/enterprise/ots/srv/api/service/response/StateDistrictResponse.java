package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.StateDistrictModel;

public class StateDistrictResponse {
	private List<StateDistrictModel> states;

	public List<StateDistrictModel> getStates() {
		return states;
	}

	public void setStates(List<StateDistrictModel> states) {
		this.states = states;
	}

}
