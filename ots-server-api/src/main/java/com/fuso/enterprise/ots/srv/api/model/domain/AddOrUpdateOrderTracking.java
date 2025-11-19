package com.fuso.enterprise.ots.srv.api.model.domain;

public class AddOrUpdateOrderTracking  {
	private String orderProductId;
	private String otsTrackingId;
	private String otsTrackingUrl;
	private String otsTrackingLogistics;

	public String getOrderProductId() {
		return orderProductId;
	}
	public void setOrderProductId(String orderProductId) {
		this.orderProductId = orderProductId;
	}
	public String getOtsTrackingId() {
		return otsTrackingId;
	}
	public void setOtsTrackingId(String otsTrackingId) {
		this.otsTrackingId = otsTrackingId;
	}
	public String getOtsTrackingUrl() {
		return otsTrackingUrl;
	}
	public void setOtsTrackingUrl(String otsTrackingUrl) {
		this.otsTrackingUrl = otsTrackingUrl;
	}
	public String getOtsTrackingLogistics() {
		return otsTrackingLogistics;
	}
	public void setOtsTrackingLogistics(String otsTrackingLogistics) {
		this.otsTrackingLogistics = otsTrackingLogistics;
	}

}



