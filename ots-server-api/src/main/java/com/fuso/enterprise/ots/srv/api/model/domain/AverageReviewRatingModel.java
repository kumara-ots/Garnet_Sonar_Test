package com.fuso.enterprise.ots.srv.api.model.domain;

public class AverageReviewRatingModel {
	private Object averageRating;
	private Integer totalReviewRating;
	
	public Object getAverageRating() {
		return averageRating;
	}
	public void setAverageRating(Object object) {
		this.averageRating = object;
	}
	public Integer getTotalReviewRating() {
		return totalReviewRating;
	}
	public void setTotalReviewRating(Integer totalReviewRating) {
		this.totalReviewRating = totalReviewRating;
	}
	
}
