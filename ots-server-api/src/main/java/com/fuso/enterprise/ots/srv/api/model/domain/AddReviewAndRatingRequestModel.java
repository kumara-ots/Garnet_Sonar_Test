package com.fuso.enterprise.ots.srv.api.model.domain;

public class AddReviewAndRatingRequestModel 
{

	private String productId;
	
	private String customerId;
	
	private String orderId;
	
	private String otsRatingReviewComment;

	private String otsRatingReviewRating;
	
	private String otsRatingReviewTitle;
	
	private String otsRatingReviewId;

	public String getOtsRatingReviewId() {
		return otsRatingReviewId;
	}

	public void setOtsRatingReviewId(String otsRatingReviewId) {
		this.otsRatingReviewId = otsRatingReviewId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOtsRatingReviewComment() {
		return otsRatingReviewComment;
	}

	public void setOtsRatingReviewComment(String otsRatingReviewComment) {
		this.otsRatingReviewComment = otsRatingReviewComment;
	}

	public String getOtsRatingReviewRating() {
		return otsRatingReviewRating;
	}

	public void setOtsRatingReviewRating(String otsRatingReviewRating) {
		this.otsRatingReviewRating = otsRatingReviewRating;
	}

	public String getOtsRatingReviewTitle() {
		return otsRatingReviewTitle;
	}

	public void setOtsRatingReviewTitle(String otsRatingReviewTitle) {
		this.otsRatingReviewTitle = otsRatingReviewTitle;
	}

}
