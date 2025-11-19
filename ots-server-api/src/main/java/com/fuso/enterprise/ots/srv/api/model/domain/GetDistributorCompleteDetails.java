package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.List;

public class GetDistributorCompleteDetails {
	
	private UserDetails distributorDetails;
	private List<DistributorCompanyDetails> distributorCompanyDetails;
	private List<DistributorPaymentDetails> distributorPaymentDetails;
	
	public UserDetails getDistributorDetails() {
		return distributorDetails;
	}
	public void setDistributorDetails(UserDetails distributorDetails) {
		this.distributorDetails = distributorDetails;
	}
	public List<DistributorCompanyDetails> getDistributorCompanyDetails() {
		return distributorCompanyDetails;
	}
	public void setDistributorCompanyDetails(List<DistributorCompanyDetails> distributorCompanyDetails) {
		this.distributorCompanyDetails = distributorCompanyDetails;
	}
	public List<DistributorPaymentDetails> getDistributorPaymentDetails() {
		return distributorPaymentDetails;
	}
	public void setDistributorPaymentDetails(List<DistributorPaymentDetails> distributorPaymentDetails) {
		this.distributorPaymentDetails = distributorPaymentDetails;
	}

}
