package com.fuso.enterprise.ots.srv.api.service.response;

public class CcAvenueOrderDetailsResponse {
	private String ccAvenueMerchantId;
	
	private String ccAvenueAccessCode;
	
	private String ccAvenueWorkingKey;

	
	public String getCcAvenueMerchantId() {
		return ccAvenueMerchantId;
	}

	public void setCcAvenueMerchantId(String ccAvenueMerchantId) {
		this.ccAvenueMerchantId = ccAvenueMerchantId;
	}

	public String getCcAvenueAccessCode() {
		return ccAvenueAccessCode;
	}

	public void setCcAvenueAccessCode(String ccAvenueAccessCode) {
		this.ccAvenueAccessCode = ccAvenueAccessCode;
	}

	public String getCcAvenueWorkingKey() {
		return ccAvenueWorkingKey;
	}

	public void setCcAvenueWorkingKey(String ccAvenueWorkingKey) {
		this.ccAvenueWorkingKey = ccAvenueWorkingKey;
	}

	
}
