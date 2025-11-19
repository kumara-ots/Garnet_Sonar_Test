package com.fuso.enterprise.ots.srv.api.service.response;

import com.fuso.enterprise.ots.srv.api.model.domain.AverageServiceReviewRatingModel;

public class AverageServiceReviewRatingResponse {

	private AverageServiceReviewRatingModel averageReviewRating;

	public AverageServiceReviewRatingModel getAverageReviewRating() {
		return averageReviewRating;
	}

	public void setAverageReviewRating(AverageServiceReviewRatingModel averageReviewRating) {
		this.averageReviewRating = averageReviewRating;
	}

}
