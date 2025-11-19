package com.fuso.enterprise.ots.srv.api.service.request;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.AddScheduler;

public class AddSchedulerRequest {
	
	private String typeOfSchedule;
	
	private String distributorId;
	
	private String CustomerId;
	
	private List<AddScheduler> addScheduler;

	public String getTypeOfSchedule() {
		return typeOfSchedule;
	}

	public void setTypeOfSchedule(String typeOfSchedule) {
		this.typeOfSchedule = typeOfSchedule;
	}

	public List<AddScheduler> getAddScheduler() {
		return addScheduler;
	}

	public void setAddScheduler(List<AddScheduler> addScheduler) {
		this.addScheduler = addScheduler;
	}

	public String getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}

	public String getCustomerId() {
		return CustomerId;
	}

	public void setCustomerId(String customerId) {
		CustomerId = customerId;
	}
	
	
}
