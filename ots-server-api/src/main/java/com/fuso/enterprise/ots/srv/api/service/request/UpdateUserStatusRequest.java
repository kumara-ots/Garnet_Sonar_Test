package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.UpdateUserStatusModel;

public class UpdateUserStatusRequest {
	
	private UpdateUserStatusModel request;

	public UpdateUserStatusModel getRequest() {
		return request;
	}

	public void setRequest(UpdateUserStatusModel request) {
		this.request = request;
	}
	
}