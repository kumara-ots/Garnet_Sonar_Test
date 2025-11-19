package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.CloseEmployeeOrder;

public class CloseEmployeeOrderRequest {
	private CloseEmployeeOrder request;

	public CloseEmployeeOrder getRequest() {
		return request;
	}

	public void setRequest(CloseEmployeeOrder request) {
		this.request = request;
	}
}

