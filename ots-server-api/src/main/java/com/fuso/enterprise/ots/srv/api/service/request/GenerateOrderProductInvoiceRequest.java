package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.GenerateOrderProductInvoiceModel;

public class GenerateOrderProductInvoiceRequest {
	private GenerateOrderProductInvoiceModel request;

	public GenerateOrderProductInvoiceModel getRequest() {
		return request;
	}

	public void setRequest(GenerateOrderProductInvoiceModel request) {
		this.request = request;
	}

}
