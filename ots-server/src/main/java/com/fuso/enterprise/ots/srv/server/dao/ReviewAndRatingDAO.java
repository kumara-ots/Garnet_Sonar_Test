package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.service.request.AddReviewAndRatingRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetReviewRatingRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateReviewStatusRequest;
import com.fuso.enterprise.ots.srv.api.service.response.GetReviewAndRatingResponse;

/*Shreekant Rathod 29-1-2021*/

public interface ReviewAndRatingDAO {
	
	String addReviewAndRating(AddReviewAndRatingRequest addReviewAndRatingRequest);

	List<GetReviewAndRatingResponse> getReviewAndRating(GetReviewRatingRequest getReviewRatingRequest);

	List<GetReviewAndRatingResponse> getReviewAndRatingForProduct(String productId);

	List<GetReviewAndRatingResponse> getReviewAndRatingByOrderId(String orderId, String productId, String customerId);

	String updateReviewStatus(UpdateReviewStatusRequest updateReviewStatusRequest);

	String updateReviewAndRating(AddReviewAndRatingRequest addReviewAndRatingRequest);

}
