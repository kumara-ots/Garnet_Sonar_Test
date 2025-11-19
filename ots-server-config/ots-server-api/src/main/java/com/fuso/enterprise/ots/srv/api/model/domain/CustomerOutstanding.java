package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.List;

import javax.validation.constraints.Size;

public class CustomerOutstanding {
	
	 @Size(max = 10)
	 private int customerId;
	 
	 @Size(max = 10)
	 private String customerName;
	 
	 @Size(max = 10)
	 private String outstandingAmount ;
	 
	 @Size(max = 10)
	 private String outstandingCan ;
	 
	 private List<BalanceCan> balanceCan;

	public List<BalanceCan> getBalanceCan() {
		return balanceCan;
	}

	public void setBalanceCan(List<BalanceCan> balanceCan) {
		this.balanceCan = balanceCan;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getOutstandingAmount() {
		return outstandingAmount;
	}

	public void setOutstandingAmount(String outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}

	public String getOutstandingCan() {
		return outstandingCan;
	}

	public void setOutstandingCan(String outstandingCan) {
		this.outstandingCan = outstandingCan;
	}
	 
	 

}
