package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.UpdateProductStatusRequestModel;

public class UpdateProductStatusRequest {

	private UpdateProductStatusRequestModel request;

	public UpdateProductStatusRequestModel getRequest() {
		return request;
	}

	public void setRequest(UpdateProductStatusRequestModel request) {
		this.request = request;
	}
}
