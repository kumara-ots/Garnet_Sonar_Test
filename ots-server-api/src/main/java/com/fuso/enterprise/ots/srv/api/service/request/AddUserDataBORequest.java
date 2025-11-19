package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.UserDetails;

public class AddUserDataBORequest {
	
	public UserDetails requestData;

	public UserDetails getRequestData() {
		return requestData;
	}

	public void setRequestData(UserDetails requestData) {
		this.requestData = requestData;
	}

	

}
