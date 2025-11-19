package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.CancelOrderProductModel;

public class CancelOrderRequest {
	
	private CancelOrderProductModel request;

	public CancelOrderProductModel getRequest() {
		return request;
	}

	public void setRequest(CancelOrderProductModel request) {
		this.request = request;
	}
	
}
