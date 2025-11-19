package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.getProductDetailsByFilter;

public class FilterProductsByGeneralPropertiesRequest {
	private getProductDetailsByFilter request;

	public getProductDetailsByFilter getRequest() {
		return request;
	}

	public void setRequest(getProductDetailsByFilter request) {
		this.request = request;
	}

}
