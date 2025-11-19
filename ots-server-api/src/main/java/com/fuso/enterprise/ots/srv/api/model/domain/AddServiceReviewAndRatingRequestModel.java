package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.Date;

public class AddServiceReviewAndRatingRequestModel {

	private String otsServiceRatingReviewComment;
	private Integer otsReviewRating;
	private String otsRatingReviewTittle;
	private Date otsRatingReviewAddedDate;
	private String otsRatingReviewStatus;
	private String otsCustomerId;
	private String otsServiceId;
	private String otsServiceOrderId;

	public String getOtsServiceRatingReviewComment() {
		return otsServiceRatingReviewComment;
	}

	public void setOtsServiceRatingReviewComment(String otsServiceRatingReviewComment) {
		this.otsServiceRatingReviewComment = otsServiceRatingReviewComment;
	}

	public Integer getOtsReviewRating() {
		return otsReviewRating;
	}

	public void setOtsReviewRating(Integer otsReviewRating) {
		this.otsReviewRating = otsReviewRating;
	}

	public String getOtsRatingReviewTittle() {
		return otsRatingReviewTittle;
	}

	public void setOtsRatingReviewTittle(String otsRatingReviewTittle) {
		this.otsRatingReviewTittle = otsRatingReviewTittle;
	}

	public Date getOtsRatingReviewAddedDate() {
		return otsRatingReviewAddedDate;
	}

	public void setOtsRatingReviewAddedDate(Date otsRatingReviewAddedDate) {
		this.otsRatingReviewAddedDate = otsRatingReviewAddedDate;
	}

	public String getOtsRatingReviewStatus() {
		return otsRatingReviewStatus;
	}

	public void setOtsRatingReviewStatus(String otsRatingReviewStatus) {
		this.otsRatingReviewStatus = otsRatingReviewStatus;
	}

	public String getOtsCustomerId() {
		return otsCustomerId;
	}

	public void setOtsCustomerId(String otsCustomerId) {
		this.otsCustomerId = otsCustomerId;
	}

	public String getOtsServiceId() {
		return otsServiceId;
	}

	public void setOtsServiceId(String otsServiceId) {
		this.otsServiceId = otsServiceId;
	}

	public String getOtsServiceOrderId() {
		return otsServiceOrderId;
	}

	public void setOtsServiceOrderId(String otsServiceOrderId) {
		this.otsServiceOrderId = otsServiceOrderId;
	}

}
