package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.LoginAuthenticationModel;

public class LoginAuthenticationBOrequest {

	private LoginAuthenticationModel requestData;

	public LoginAuthenticationModel getRequestData() {
		return requestData;
	}

	public void setRequestData(LoginAuthenticationModel requestData) {
		this.requestData = requestData;
	}
	
}
