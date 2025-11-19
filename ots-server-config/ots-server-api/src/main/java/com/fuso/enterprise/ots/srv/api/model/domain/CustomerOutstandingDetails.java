package com.fuso.enterprise.ots.srv.api.model.domain;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CustomerOutstandingDetails {
	
	@Size(max = 10)
	private String customerId;
	
	@Size(max = 10)
	private String customerOutstandingAmt;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerOutstandingAmt() {
		return customerOutstandingAmt;
	}

	public void setCustomerOutstandingAmt(String customerOutstandingAmt) {
		this.customerOutstandingAmt = customerOutstandingAmt;
	}

}
