package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.ServiceDetailsData;

public class ServiceDetailsBOResponse {

	private String userId;

	List<ServiceDetailsData> serviceDetails;

	private String totalServiceCount;

	private String totalPages;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<ServiceDetailsData> getServiceDetails() {
		return serviceDetails;
	}

	public void setServiceDetails(List<ServiceDetailsData> serviceDetails) {
		this.serviceDetails = serviceDetails;
	}

	public String getTotalServiceCount() {
		return totalServiceCount;
	}

	public void setTotalServiceCount(String totalServiceCount) {
		this.totalServiceCount = totalServiceCount;
	}

	public String getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(String totalPages) {
		this.totalPages = totalPages;
	}

}
