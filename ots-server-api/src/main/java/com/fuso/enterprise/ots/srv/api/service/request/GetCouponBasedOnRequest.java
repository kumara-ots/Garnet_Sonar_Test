package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.GetCouponBasedOnRequestModel;

public class GetCouponBasedOnRequest {
	private GetCouponBasedOnRequestModel request;

	public GetCouponBasedOnRequestModel getRequest() {
		return request;
	}

	public void setRequest(GetCouponBasedOnRequestModel request) {
		this.request = request;
	}
}
