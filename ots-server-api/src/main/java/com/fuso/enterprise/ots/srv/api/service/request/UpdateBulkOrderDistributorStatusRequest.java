package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.UpdateBulkOrderDistributorStatusModel;

public class UpdateBulkOrderDistributorStatusRequest {
	private UpdateBulkOrderDistributorStatusModel request;

	public UpdateBulkOrderDistributorStatusModel getRequest() {
		return request;
	}

	public void setRequest(UpdateBulkOrderDistributorStatusModel request) {
		this.request = request;
	}
}
