package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.OrderDetailsRequest;

public class AddOrUpdateOrderProductBOrequest 
{
	
	private OrderDetailsRequest request;
	private String BulkOrderNumber;
	private String BulkOrdercustStatus;
	private String couponId;
	
	public OrderDetailsRequest getRequest() {
		return request;
	}

	public void setRequest(OrderDetailsRequest request) {
		this.request = request;
	}

	public String getBulkOrderNumber() {
		return BulkOrderNumber;
	}

	public void setBulkOrderNumber(String bulkOrderNumber) {
		BulkOrderNumber = bulkOrderNumber;
	}

	public String getBulkOrdercustStatus() {
		return BulkOrdercustStatus;
	}

	public void setBulkOrdercustStatus(String bulkOrdercustStatus) {
		BulkOrdercustStatus = bulkOrdercustStatus;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
}
