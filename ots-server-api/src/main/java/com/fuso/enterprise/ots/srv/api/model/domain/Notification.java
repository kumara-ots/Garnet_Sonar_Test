package com.fuso.enterprise.ots.srv.api.model.domain;

public class Notification
{
	String NotificationId;
	String NotificationDueDate;
	String DistributerId;
	String CustomerId;
	String BulkOrderId;
	public String getNotificationId() {
		return NotificationId;
	}
	public void setNotificationId(String notificationId) {
		NotificationId = notificationId;
	}
	public String getNotificationDueDate() {
		return NotificationDueDate;
	}
	public void setNotificationDueDate(String notificationDueDate) {
		NotificationDueDate = notificationDueDate;
	}
	public String getDistributerId() {
		return DistributerId;
	}
	public void setDistributerId(String distributerId) {
		DistributerId = distributerId;
	}
	public String getCustomerId() {
		return CustomerId;
	}
	public void setCustomerId(String customerId) {
		CustomerId = customerId;
	}
	public String getBulkOrderId() {
		return BulkOrderId;
	}
	public void setBulkOrderId(String bulkOrderId) {
		BulkOrderId = bulkOrderId;
	}
	

}
