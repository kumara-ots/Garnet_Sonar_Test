package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.ForgotAdminPasswordRequestModel;

public class ForgotAdminPasswordRequest {

	ForgotAdminPasswordRequestModel request;

	public ForgotAdminPasswordRequestModel getRequest() {
		return request;
	}

	public void setRequest(ForgotAdminPasswordRequestModel request) {
		this.request = request;
	}

}
