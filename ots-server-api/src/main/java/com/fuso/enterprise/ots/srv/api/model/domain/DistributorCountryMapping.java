package com.fuso.enterprise.ots.srv.api.model.domain;

public class DistributorCountryMapping {
	
	private String distributorCountryMappingId;
	private String productId;
	private String distributorId;
	private String countryCode;
	private String countryName;
	private String countryCurrency;
	private String countryCurrencySymbol;
	
	public String getDistributorCountryMappingId() {
		return distributorCountryMappingId;
	}
	public void setDistributorCountryMappingId(String distributorCountryMappingId) {
		this.distributorCountryMappingId = distributorCountryMappingId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getDistributorId() {
		return distributorId;
	}
	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCountryCurrency() {
		return countryCurrency;
	}
	public void setCountryCurrency(String countryCurrency) {
		this.countryCurrency = countryCurrency;
	}
	public String getCountryCurrencySymbol() {
		return countryCurrencySymbol;
	}
	public void setCountryCurrencySymbol(String countryCurrencySymbol) {
		this.countryCurrencySymbol = countryCurrencySymbol;
	}

}
