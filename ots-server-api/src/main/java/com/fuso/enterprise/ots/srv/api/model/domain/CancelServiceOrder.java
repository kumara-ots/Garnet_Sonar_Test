package com.fuso.enterprise.ots.srv.api.model.domain;

public class CancelServiceOrder {
	private String otsServiceOrderId;
	private String otsServiceOrderCancelledBy;
	private String otsServiceOrderCancellationReason;
	
	public String getOtsServiceOrderId() {
		return otsServiceOrderId;
	}
	public void setOtsServiceOrderId(String otsServiceOrderId) {
		this.otsServiceOrderId = otsServiceOrderId;
	}
	public String getOtsServiceOrderCancelledBy() {
		return otsServiceOrderCancelledBy;
	}
	public void setOtsServiceOrderCancelledBy(String otsServiceOrderCancelledBy) {
		this.otsServiceOrderCancelledBy = otsServiceOrderCancelledBy;
	}
	public String getOtsServiceOrderCancellationReason() {
		return otsServiceOrderCancellationReason;
	}
	public void setOtsServiceOrderCancellationReason(String otsServiceOrderCancellationReason) {
		this.otsServiceOrderCancellationReason = otsServiceOrderCancellationReason;
	}
	
	
	

}
