package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.List;
import java.util.Map;

public class ServiceSlot {

	private String otsServiceId;

	private String otsServiceStatus;
	private Object otsServiceSlot;

	//private Map<String, List<Slot>> otsServiceSlot;

	
	public String getOtsServiceId() {
		return otsServiceId;
	}

	public void setOtsServiceId(String otsServiceId) {
		this.otsServiceId = otsServiceId;
	}

	public Object getOtsServiceSlot() {
		return otsServiceSlot;
	}

	public void setOtsServiceSlot(Object otsServiceSlot) {
		this.otsServiceSlot = otsServiceSlot;
	}

	public String getOtsServiceStatus() {
		return otsServiceStatus;
	}

	public void setOtsServiceStatus(String otsServiceStatus) {
		this.otsServiceStatus = otsServiceStatus;
	}

	
//	public Map<String, List<Slot>> getOtsServiceSlot() {
//		return otsServiceSlot;
//	}
//
//	public void setOtsServiceSlot(Map<String, List<Slot>> otsServiceSlot) {
//		this.otsServiceSlot = otsServiceSlot;
//	}

}
