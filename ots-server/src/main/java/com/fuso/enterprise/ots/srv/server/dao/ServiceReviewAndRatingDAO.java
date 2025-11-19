package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.service.request.AddServiceReviewAndRatingRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetServiceReviewsAndRatingRequest;
import com.fuso.enterprise.ots.srv.api.service.response.GetServiceReviewAndRatingResponse;

public interface ServiceReviewAndRatingDAO {
	
	String addServiceRatingAndReview(AddServiceReviewAndRatingRequest reviewAndRatingRequest);
	
	List<GetServiceReviewAndRatingResponse> getServiceRatingAndReviewDetailsByOrderId(GetServiceReviewsAndRatingRequest getServiceReviewsAndRatingRequest);

	List<GetServiceReviewAndRatingResponse> getAverageRatingOfService(String serviceId);
}
