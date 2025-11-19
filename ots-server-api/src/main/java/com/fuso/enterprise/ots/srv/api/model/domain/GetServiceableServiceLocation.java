package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.List;

public class GetServiceableServiceLocation {

	private String providerId;
	private String serviceId;
	private List<String> state;
	private List<String> district;
	private List<String> pincode;
	private List<ServiceDistrict> districtList;
	private List<ServicePincode> pincodeList;
	private List<ServiceState> statelist;

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

	public List<String> getState() {
		return state;
	}

	public void setState(List<String> state) {
		this.state = state;
	}

	public List<String> getDistrict() {
		return district;
	}

	public void setDistrict(List<String> district) {
		this.district = district;
	}

	public List<String> getPincode() {
		return pincode;
	}

	public void setPincode(List<String> pincode) {
		this.pincode = pincode;
	}

	public List<ServiceDistrict> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<ServiceDistrict> districtList) {
		this.districtList = districtList;
	}

	public List<ServicePincode> getPincodeList() {
		return pincodeList;
	}

	public void setPincodeList(List<ServicePincode> pincodeList) {
		this.pincodeList = pincodeList;
	}

	public List<ServiceState> getStatelist() {
		return statelist;
	}

	public void setStatelist(List<ServiceState> statelist) {
		this.statelist = statelist;
	}

}
