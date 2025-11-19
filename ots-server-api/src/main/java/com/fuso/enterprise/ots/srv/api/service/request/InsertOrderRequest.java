package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.InsertOrderRequestModel;

public class InsertOrderRequest {
	private InsertOrderRequestModel request;

	public InsertOrderRequestModel getRequest() {
		return request;
	}

	public void setRequest(InsertOrderRequestModel request) {
		this.request = request;
	}
	
}
