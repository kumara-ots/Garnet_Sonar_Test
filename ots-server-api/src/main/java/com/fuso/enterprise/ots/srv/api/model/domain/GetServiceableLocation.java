package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.List;

public class GetServiceableLocation {
	
	private String distributorId;
	private String productId;
	private List<String> state;
	private List<String> district;
	private List<String> pincode;
	private List<ServiceDistrict> districtList;
	private List<ServicePincode>  pincodeList;
	private List<ServiceState> statelist;
	public String getDistributorId() {
		return distributorId;
	}
	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
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
