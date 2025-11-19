package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.List;
import java.util.Map;

public class GetServiceSlot {

	private String date;
	private String day;
	private String serviceId;

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

}
