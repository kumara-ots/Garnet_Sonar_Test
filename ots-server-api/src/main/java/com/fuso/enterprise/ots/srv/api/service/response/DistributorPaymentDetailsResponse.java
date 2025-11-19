package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.DistributorPaymentDetails;

public class DistributorPaymentDetailsResponse {
	private List<DistributorPaymentDetails> distributorPaymentDetails;

	public List<DistributorPaymentDetails> getDistributorPaymentDetails() {
		return distributorPaymentDetails;
	}

	public void setDistributorPaymentDetails(List<DistributorPaymentDetails> distributorPaymentDetails) {
		this.distributorPaymentDetails = distributorPaymentDetails;
	}
	
}
