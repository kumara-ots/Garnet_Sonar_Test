package com.fuso.enterprise.ots.srv.api.service.response;

import com.fuso.enterprise.ots.srv.api.model.domain.AverageReviewRatingModel;

public class AverageReviewRatingResponse {
	private AverageReviewRatingModel averageReviewRating;

	public AverageReviewRatingModel getAverageReviewRating() {
		return averageReviewRating;
	}

	public void setAverageReviewRating(AverageReviewRatingModel averageReviewRating) {
		this.averageReviewRating = averageReviewRating;
	}
	
}