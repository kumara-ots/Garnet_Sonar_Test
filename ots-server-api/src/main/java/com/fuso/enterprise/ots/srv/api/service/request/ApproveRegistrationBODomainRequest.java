package com.fuso.enterprise.ots.srv.api.service.request;

import javax.validation.constraints.Size;

public class ApproveRegistrationBODomainRequest {
	
	@Size(max = 100)
	private String registrationId;
	
	private String ProductPrice;
	
	private String ProductId;

	public String getProductPrice() {
		return ProductPrice;
	}

	public void setProductPrice(String productPrice) {
		ProductPrice = productPrice;
	}

	public String getProductId() {
		return ProductId;
	}

	public void setProductId(String productId) {
		ProductId = productId;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

}
