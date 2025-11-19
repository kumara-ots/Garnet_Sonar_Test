package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.PaymentTransactionCancelRecords;

public class AddTransactionCancelRecordsRequest {
	private PaymentTransactionCancelRecords request;

	public PaymentTransactionCancelRecords getRequest() {
		return request;
	}

	public void setRequest(PaymentTransactionCancelRecords request) {
		this.request = request;
	}

}
