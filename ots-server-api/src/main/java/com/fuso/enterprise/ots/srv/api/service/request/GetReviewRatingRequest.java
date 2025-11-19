package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.GetReviewAndRating;

public class GetReviewRatingRequest {
	private GetReviewAndRating request;

	public GetReviewAndRating getRequest() {
		return request;
	}

	public void setRequest(GetReviewAndRating request) {
		this.request = request;
	}
}
