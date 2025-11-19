package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.Slot;

public class GetServcieSlotResponse {

	private String day;
	private List<Slot> slots;

	public GetServcieSlotResponse() {
	}

	public GetServcieSlotResponse(String day, List<Slot> slots) {
		this.day = day;
		this.slots = slots;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public List<Slot> getSlots() {
		return slots;
	}

	public void setSlots(List<Slot> slots) {
		this.slots = slots;
	}

}
