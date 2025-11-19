package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.AddOrderPaymentDetailsModel;

public class AddOrderPaymentDetailsRequest {
	private AddOrderPaymentDetailsModel request;

	public AddOrderPaymentDetailsModel getRequest() {
		return request;
	}

	public void setRequest(AddOrderPaymentDetailsModel request) {
		this.request = request;
	}
}
