package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.List;

public class AddServiceLocationMappingModel {

	private String providerId;
	private String serviceId;
	private List<ServiceLocationIDName> serviceLocationIDName;

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public List<ServiceLocationIDName> getServiceLocationIDName() {
		return serviceLocationIDName;
	}

	public void setServiceLocationIDName(List<ServiceLocationIDName> serviceLocationIDName) {
		this.serviceLocationIDName = serviceLocationIDName;
	}

}
