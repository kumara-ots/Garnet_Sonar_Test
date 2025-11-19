package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.AddCouponRequestModel;

public class AddCouponRequest {
	private AddCouponRequestModel request;

	public AddCouponRequestModel getRequest() {
		return request;
	}

	public void setRequest(AddCouponRequestModel request) {
		this.request = request;
	}
}
