package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.AddOrUpdateOrderTracking;

public class AddOrUpdateOrderTrackingRequest {
	private AddOrUpdateOrderTracking request;

	public AddOrUpdateOrderTracking getRequest() {
		return request;
	}

	public void setRequest(AddOrUpdateOrderTracking request) {
		this.request = request;
	}
	
}
