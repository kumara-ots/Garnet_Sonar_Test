package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.AddToCartRequestModel;

public class AddToCartRequest 
{

	private AddToCartRequestModel requestData;

	public AddToCartRequestModel getRequestData() {
		return requestData;
	}

	public void setRequestData(AddToCartRequestModel requestData) {
		this.requestData = requestData;
	}
	
}
