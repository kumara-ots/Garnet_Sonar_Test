package com.fuso.enterprise.ots.srv.api.model.domain;

public class PaymentTransactionCancelRecords {
	private String otsTransactionCancelId;
	private String otsTransactionCancelOrderId;
	private String otsTransactionCancelTrackingId;
	private String otsTransactionCancelOrderStatus;
	private String otsTransactionCancelAmount;

	public String getOtsTransactionCancelId() {
		return otsTransactionCancelId;
	}
	public void setOtsTransactionCancelId(String otsTransactionCancelId) {
		this.otsTransactionCancelId = otsTransactionCancelId;
	}
	public String getOtsTransactionCancelOrderId() {
		return otsTransactionCancelOrderId;
	}
	public void setOtsTransactionCancelOrderId(String otsTransactionCancelOrderId) {
		this.otsTransactionCancelOrderId = otsTransactionCancelOrderId;
	}
	public String getOtsTransactionCancelTrackingId() {
		return otsTransactionCancelTrackingId;
	}
	public void setOtsTransactionCancelTrackingId(String otsTransactionCancelTrackingId) {
		this.otsTransactionCancelTrackingId = otsTransactionCancelTrackingId;
	}
	public String getOtsTransactionCancelOrderStatus() {
		return otsTransactionCancelOrderStatus;
	}
	public void setOtsTransactionCancelOrderStatus(String otsTransactionCancelOrderStatus) {
		this.otsTransactionCancelOrderStatus = otsTransactionCancelOrderStatus;
	}
	public String getOtsTransactionCancelAmount() {
		return otsTransactionCancelAmount;
	}
	public void setOtsTransactionCancelAmount(String otsTransactionCancelAmount) {
		this.otsTransactionCancelAmount = otsTransactionCancelAmount;
	}

}
