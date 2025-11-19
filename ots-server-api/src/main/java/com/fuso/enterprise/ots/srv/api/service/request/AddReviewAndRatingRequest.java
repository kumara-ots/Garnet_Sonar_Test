package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.AddReviewAndRatingRequestModel;
/*Shreekant Rathod 29-1-2021*/
public class AddReviewAndRatingRequest {
	
	private AddReviewAndRatingRequestModel requestData;

	public AddReviewAndRatingRequestModel getRequestData() {
		return requestData;
	}

	public void setRequestData(AddReviewAndRatingRequestModel requestData) {
		this.requestData = requestData;
	}

}
