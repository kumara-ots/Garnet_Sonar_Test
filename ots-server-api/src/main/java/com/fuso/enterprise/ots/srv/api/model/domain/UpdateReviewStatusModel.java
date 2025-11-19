package com.fuso.enterprise.ots.srv.api.model.domain;

public class UpdateReviewStatusModel {
	private String otsReviewRatingId;
	private String otsReviewRatingStatus;
	
	public String getOtsReviewRatingId() {
		return otsReviewRatingId;
	}
	public void setOtsReviewRatingId(String otsReviewRatingId) {
		this.otsReviewRatingId = otsReviewRatingId;
	}
	public String getOtsReviewRatingStatus() {
		return otsReviewRatingStatus;
	}
	public void setOtsReviewRatingStatus(String otsReviewRatingStatus) {
		this.otsReviewRatingStatus = otsReviewRatingStatus;
	}
	
}
