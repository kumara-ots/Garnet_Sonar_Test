package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.UpdateServiceOrderStatus;

public class UpdateServiceOrderRequest {
	
	private UpdateServiceOrderStatus request;

	public UpdateServiceOrderStatus getRequest() {
		return request;
	}

	public void setRequest(UpdateServiceOrderStatus request) {
		this.request = request;
	}
	

}
