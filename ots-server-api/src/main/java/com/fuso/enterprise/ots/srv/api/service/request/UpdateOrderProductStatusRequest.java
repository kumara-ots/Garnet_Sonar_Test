package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.UpdateOrderProductStatusModel;

public class UpdateOrderProductStatusRequest {

	private UpdateOrderProductStatusModel request;

	public UpdateOrderProductStatusModel getRequest() {
		return request;
	}

	public void setRequest(UpdateOrderProductStatusModel request) {
		this.request = request;
	}
	
}
