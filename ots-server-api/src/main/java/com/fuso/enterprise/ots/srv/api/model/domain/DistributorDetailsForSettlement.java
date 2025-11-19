package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.List;

public class DistributorDetailsForSettlement {
	private List<DistributorPaymentDetails> distributorPaymentDetails;
//	private List<DistributorCompanyDetails> distributorCompanyDetails;
	private String excelTitle;
	private String fromToDate;
	
	public String getFromToDate() {
		return fromToDate;
	}
	public void setFromToDate(String fromToDate) {
		this.fromToDate = fromToDate;
	}
	public List<DistributorPaymentDetails> getDistributorPaymentDetails() {
		return distributorPaymentDetails;
	}
	public void setDistributorPaymentDetails(List<DistributorPaymentDetails> distributorPaymentDetails) {
		this.distributorPaymentDetails = distributorPaymentDetails;
	}
//	public List<DistributorCompanyDetails> getDistributorCompanyDetails() {
//		return distributorCompanyDetails;
//	}
//	public void setDistributorCompanyDetails(List<DistributorCompanyDetails> distributorCompanyDetails) {
//		this.distributorCompanyDetails = distributorCompanyDetails;
//	}
	public String getExcelTitle() {
		return excelTitle;
	}
	public void setExcelTitle(String excelTitle) {
		this.excelTitle = excelTitle;
	}
	
}
