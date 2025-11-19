package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.SchedulerResponceOrderModel;

public class GetSchedulerResponse {

	private List<SchedulerResponceOrderModel> response;

	public List<SchedulerResponceOrderModel> getResponse() {
		return response;
	}

	public void setResponse(List<SchedulerResponceOrderModel> response) {
		this.response = response;
	}

	
}
