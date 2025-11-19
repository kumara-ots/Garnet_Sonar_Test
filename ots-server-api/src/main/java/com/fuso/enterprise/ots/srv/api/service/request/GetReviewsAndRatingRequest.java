package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.GetReviewsAndRatingRequestModel;

public class GetReviewsAndRatingRequest {
	private GetReviewsAndRatingRequestModel request;

	public GetReviewsAndRatingRequestModel getRequest() {
		return request;
	}

	public void setRequest(GetReviewsAndRatingRequestModel request) {
		this.request = request;
	}
	
}
