package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;
import java.util.Map;

import com.fuso.enterprise.ots.srv.api.model.domain.Notification;

public class NotificationResponse 
{
	List<Map<String, Object>> notification;
	List<Map<String, Object>> Bulkdetails;

	public List<Map<String, Object>> getBulkdetails() {
		return Bulkdetails;
	}

	public void setBulkdetails(List<Map<String, Object>> bulkdetails) {
		Bulkdetails = bulkdetails;
	}

	public List<Map<String, Object>> getNotification() {
		return notification;
	}

	public void setNotification(List<Map<String, Object>> notification) {
		this.notification = notification;
	}

}
