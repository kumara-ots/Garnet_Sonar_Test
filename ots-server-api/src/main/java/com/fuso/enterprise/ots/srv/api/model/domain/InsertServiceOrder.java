package com.fuso.enterprise.ots.srv.api.model.domain;

public class InsertServiceOrder {
	private String otsCustomerId;
	private String otsCustomerChangeAddressId;
	private String otsServiceOrderTransactionId;
	private String otsServiceOrderPaymentStatus;
	private String otsServiceId;
    private String otsServiceDayOfWeek;
    private String otsServiceStartTime;
    private String otsServiceEndTime;
    private String otsServiceBookedDate;
    
	public String getOtsCustomerId() {
		return otsCustomerId;
	}
	public void setOtsCustomerId(String otsCustomerId) {
		this.otsCustomerId = otsCustomerId;
	}
	public String getOtsCustomerChangeAddressId() {
		return otsCustomerChangeAddressId;
	}
	public void setOtsCustomerChangeAddressId(String otsCustomerChangeAddressId) {
		this.otsCustomerChangeAddressId = otsCustomerChangeAddressId;
	}
	public String getOtsServiceOrderTransactionId() {
		return otsServiceOrderTransactionId;
	}
	public void setOtsServiceOrderTransactionId(String otsServiceOrderTransactionId) {
		this.otsServiceOrderTransactionId = otsServiceOrderTransactionId;
	}
	public String getOtsServiceOrderPaymentStatus() {
		return otsServiceOrderPaymentStatus;
	}
	public void setOtsServiceOrderPaymentStatus(String otsServiceOrderPaymentStatus) {
		this.otsServiceOrderPaymentStatus = otsServiceOrderPaymentStatus;
	}
	public String getOtsServiceId() {
		return otsServiceId;
	}
	public void setOtsServiceId(String otsServiceId) {
		this.otsServiceId = otsServiceId;
	}
	public String getOtsServiceDayOfWeek() {
		return otsServiceDayOfWeek;
	}
	public void setOtsServiceDayOfWeek(String otsServiceDayOfWeek) {
		this.otsServiceDayOfWeek = otsServiceDayOfWeek;
	}
	public String getOtsServiceStartTime() {
		return otsServiceStartTime;
	}
	public void setOtsServiceStartTime(String otsServiceStartTime) {
		this.otsServiceStartTime = otsServiceStartTime;
	}
	public String getOtsServiceEndTime() {
		return otsServiceEndTime;
	}
	public void setOtsServiceEndTime(String otsServiceEndTime) {
		this.otsServiceEndTime = otsServiceEndTime;
	}
	public String getOtsServiceBookedDate() {
		return otsServiceBookedDate;
	}
	public void setOtsServiceBookedDate(String otsServiceBookedDate) {
		this.otsServiceBookedDate = otsServiceBookedDate;
	}
	
	
}
