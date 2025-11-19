package com.fuso.enterprise.ots.srv.server.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.fuso.enterprise.ots.srv.api.service.request.AddServiceReviewAndRatingRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetServiceReviewsAndRatingRequest;
import com.fuso.enterprise.ots.srv.api.service.response.GetServiceReviewAndRatingResponse;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.ServiceReviewAndRatingDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsService;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsServiceOrder;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsServiceRatingReview;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsUsers;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class ServiceReviewAndRatingDaoImpl extends AbstractIptDao<OtsServiceRatingReview, String>  implements ServiceReviewAndRatingDAO{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public ServiceReviewAndRatingDaoImpl() {
		super(OtsServiceRatingReview.class);
	}
	
	@Transactional
	@Override
	public String addServiceRatingAndReview(AddServiceReviewAndRatingRequest reviewAndRatingRequest) {
		java.util.Date utilDate = new java.util.Date();
		java.sql.Timestamp currentDate = new java.sql.Timestamp(utilDate.getTime());
		System.out.println("Date time = "+ currentDate); 
		try {
			OtsService serviceId = new OtsService();
			serviceId.setOtsServiceId(UUID.fromString(reviewAndRatingRequest.getRequest().getOtsServiceId()));
			
			OtsUsers customerId = new OtsUsers();
			customerId.setOtsUsersId(UUID.fromString(reviewAndRatingRequest.getRequest().getOtsCustomerId()));
			
			OtsServiceOrder orderId= new OtsServiceOrder();	
			orderId.setOtsServiceOrderId(UUID.fromString(reviewAndRatingRequest.getRequest().getOtsServiceOrderId()));
			
			OtsServiceRatingReview ratingReview = new OtsServiceRatingReview();
			ratingReview.setOtsServiceOrderId(orderId);
			ratingReview.setOtsServiceId(serviceId);
			ratingReview.setOtsCustomerId(customerId);
			ratingReview.setOtsRatingReviewTittle(reviewAndRatingRequest.getRequest().getOtsRatingReviewTittle());
			ratingReview.setOtsServiceRatingReviewComment(reviewAndRatingRequest.getRequest().getOtsServiceRatingReviewComment());
			ratingReview.setOtsReviewRating(reviewAndRatingRequest.getRequest().getOtsReviewRating());
			ratingReview.setOtsRatingReviewAddedDate(currentDate);
			ratingReview.setOtsRatingReviewStatus("active");

			save(ratingReview);
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return "Inserted";
	}

	@Override
	public List<GetServiceReviewAndRatingResponse> getServiceRatingAndReviewDetailsByOrderId(GetServiceReviewsAndRatingRequest getServiceReviewsAndRatingRequest) {
		try {
			OtsServiceOrder serviceOrderId = new OtsServiceOrder();
			serviceOrderId.setOtsServiceOrderId(UUID.fromString(getServiceReviewsAndRatingRequest.getRequest().getServiceOrderId()));
			OtsUsers customerId = new OtsUsers();
			customerId.setOtsUsersId(UUID.fromString(getServiceReviewsAndRatingRequest.getRequest().getCustomerId()));
			OtsService serviceId = new OtsService();
			serviceId.setOtsServiceId(UUID.fromString(getServiceReviewsAndRatingRequest.getRequest().getServiceId()));

			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsServiceOrderId", serviceOrderId);
			queryParameter.put("otsServiceId", serviceId);
			queryParameter.put("otsCustomerId", customerId);
			List<OtsServiceRatingReview> ratingReviews = super.getResultListByNamedQuery("OtsServiceRatingReview.getServiceReviewAndRatingByOrderId", queryParameter);
			List<GetServiceReviewAndRatingResponse> getReviewAndRatingResponses = ratingReviews.stream().map(ratingReview -> convertEntityToModel(ratingReview)).collect(Collectors.toList());
			return getReviewAndRatingResponses;
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	GetServiceReviewAndRatingResponse convertEntityToModel(OtsServiceRatingReview ratingReview) {
		GetServiceReviewAndRatingResponse getreviewAndRatingResponse = new GetServiceReviewAndRatingResponse();
		
		getreviewAndRatingResponse.setServiceId(ratingReview.getOtsServiceId()==null?"":ratingReview.getOtsServiceId().getOtsServiceId().toString());
		getreviewAndRatingResponse.setServiceName(ratingReview.getOtsServiceId().getOtsServiceName()==null?"":ratingReview.getOtsServiceId().getOtsServiceName());
		getreviewAndRatingResponse.setOtsRatingReviewId(ratingReview.getOtsServiceRatingReviewId()==null?"":ratingReview.getOtsServiceRatingReviewId().toString());
		getreviewAndRatingResponse.setOtsRatingReviewTitle(ratingReview.getOtsRatingReviewTittle()==null?"":ratingReview.getOtsRatingReviewTittle());
		getreviewAndRatingResponse.setOtsRatingReviewRating(ratingReview.getOtsReviewRating()==null?"":ratingReview.getOtsReviewRating().toString());
		getreviewAndRatingResponse.setOtsRatingReviewComment(ratingReview.getOtsServiceRatingReviewComment()==null?"":ratingReview.getOtsServiceRatingReviewComment());
		getreviewAndRatingResponse.setOtsRatingReviewAddedDate(ratingReview.getOtsRatingReviewAddedDate()==null?"":ratingReview.getOtsRatingReviewAddedDate().toString());	
		getreviewAndRatingResponse.setCustomerId(ratingReview.getOtsCustomerId().getOtsUsersId()==null?"":ratingReview.getOtsCustomerId().getOtsUsersId().toString());	
		getreviewAndRatingResponse.setCustomerName(ratingReview.getOtsCustomerId().getOtsUsersFirstname()==null?"":ratingReview.getOtsCustomerId().getOtsUsersFirstname());
		getreviewAndRatingResponse.setServiceOrderId(ratingReview.getOtsServiceOrderId().getOtsServiceOrderId()==null?"":ratingReview.getOtsServiceOrderId().getOtsServiceOrderId().toString());
		getreviewAndRatingResponse.setOtsRatingReviewStatus(ratingReview.getOtsRatingReviewStatus()==null?"":ratingReview.getOtsRatingReviewStatus());
		
		return getreviewAndRatingResponse;
	}

	@Override
	public List<GetServiceReviewAndRatingResponse> getAverageRatingOfService(String serviceId) {
		List<OtsServiceRatingReview> serviceRatingReviews = new ArrayList<OtsServiceRatingReview>();
		List<GetServiceReviewAndRatingResponse> reviewAndRatingResponses = new ArrayList<GetServiceReviewAndRatingResponse>();
		try {
			OtsService otsServiceId=new OtsService();
			otsServiceId.setOtsServiceId(UUID.fromString(serviceId));
			
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsServiceId",otsServiceId);
			serviceRatingReviews = super.getResultListByNamedQuery("OtsServiceRatingReview.getServiceReviewAndRatingByServiceId", queryParameter);
			reviewAndRatingResponses = serviceRatingReviews.stream().map(ratingReview -> convertEntityToModel(ratingReview)).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return reviewAndRatingResponses;
	}
	
}
