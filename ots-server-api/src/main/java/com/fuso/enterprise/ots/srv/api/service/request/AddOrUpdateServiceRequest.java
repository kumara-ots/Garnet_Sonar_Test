package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.AddOrUpdateService;

public class AddOrUpdateServiceRequest {

	private AddOrUpdateService requestData;

	public AddOrUpdateService getRequestData() {
		return requestData;
	}

	public void setRequestData(AddOrUpdateService requestData) {
		this.requestData = requestData;
	}

}
