package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.AddCustomerChangeAddressModel;

public class AddCustomerChangeAddressRequest {
	private AddCustomerChangeAddressModel request;

	public AddCustomerChangeAddressModel getRequest() {
		return request;
	}

	public void setRequest(AddCustomerChangeAddressModel request) {
		this.request = request;
	}
}
