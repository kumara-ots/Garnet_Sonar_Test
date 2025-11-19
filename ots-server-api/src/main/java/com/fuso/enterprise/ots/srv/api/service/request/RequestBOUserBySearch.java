package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.GetUserDetailsBORequest;

public class RequestBOUserBySearch {
	
	private GetUserDetailsBORequest requestData;

	public GetUserDetailsBORequest getRequestData() {
		return requestData;
	}

	public void setRequestData(GetUserDetailsBORequest requestData) {
		this.requestData = requestData;
	}
}
