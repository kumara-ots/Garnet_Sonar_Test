package com.fuso.enterprise.ots.srv.api.model.domain;

public class ServiceLocationMapping {

	private String serviceLocationMappingId;
	private String serviceId;
	private String providerId;
	private String locationId;
	private String locationName;
	private String serviceName;
	private String serviceImage;

	public String getServiceLocationMappingId() {
		return serviceLocationMappingId;
	}

	public void setServiceLocationMappingId(String serviceLocationMappingId) {
		this.serviceLocationMappingId = serviceLocationMappingId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceImage() {
		return serviceImage;
	}

	public void setServiceImage(String serviceImage) {
		this.serviceImage = serviceImage;
	}

}
