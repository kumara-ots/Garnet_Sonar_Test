package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.UpdateReviewStatusModel;

public class UpdateReviewStatusRequest {
	private UpdateReviewStatusModel request;

	public UpdateReviewStatusModel getRequest() {
		return request;
	}

	public void setRequest(UpdateReviewStatusModel request) {
		this.request = request;
	}

}