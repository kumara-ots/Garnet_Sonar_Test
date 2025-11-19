package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.GetProductDetails;

public class ProductDetailsBORequest {
	
	private GetProductDetails requestData;

	public GetProductDetails getRequestData() {
		return requestData;
	}

	public void setRequestData(GetProductDetails requestData) {
		this.requestData = requestData;
	}

}
