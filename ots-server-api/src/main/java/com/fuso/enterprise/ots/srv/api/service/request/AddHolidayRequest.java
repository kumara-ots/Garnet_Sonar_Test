package com.fuso.enterprise.ots.srv.api.service.request;

import java.util.List;

public class AddHolidayRequest {

	private String providerId;
	private List<String> holidays;


	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public List<String> getHolidays() {
		return holidays;
	}

	public void setHolidays(List<String> holidays) {
		this.holidays = holidays;
	}

}
