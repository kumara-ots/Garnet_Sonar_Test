package com.fuso.enterprise.ots.srv.api.model.domain;

public class AddOrUpdateService {

	private ServiceDetails serviceDetails;
	private ServicePriceDetails servicePriceDetails;
	private ServiceAvailability serviceAvailability;
	private ServicePolicy servicePolicy;
	private ServiceSlot serviceSlot;

	public ServiceDetails getServiceDetails() {
		return serviceDetails;
	}

	public void setServiceDetails(ServiceDetails serviceDetails) {
		this.serviceDetails = serviceDetails;
	}

	public ServiceAvailability getServiceAvailability() {
		return serviceAvailability;
	}

	public void setServiceAvailability(ServiceAvailability serviceAvailability) {
		this.serviceAvailability = serviceAvailability;
	}

	public ServicePriceDetails getServicePriceDetails() {
		return servicePriceDetails;
	}

	public void setServicePriceDetails(ServicePriceDetails servicePriceDetails) {
		this.servicePriceDetails = servicePriceDetails;
	}

	public ServicePolicy getServicePolicy() {
		return servicePolicy;
	}

	public void setServicePolicy(ServicePolicy servicePolicy) {
		this.servicePolicy = servicePolicy;
	}

	public ServiceSlot getServiceSlot() {
		return serviceSlot;
	}

	public void setServiceSlot(ServiceSlot serviceSlot) {
		this.serviceSlot = serviceSlot;
	}

}
