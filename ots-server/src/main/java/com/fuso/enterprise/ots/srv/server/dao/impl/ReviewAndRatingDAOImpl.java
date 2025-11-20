package com.fuso.enterprise.ots.srv.server.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.fuso.enterprise.ots.srv.api.service.request.AddReviewAndRatingRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetReviewRatingRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateReviewStatusRequest;
import com.fuso.enterprise.ots.srv.api.service.response.GetReviewAndRatingResponse;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.ReviewAndRatingDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsOrder;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsProduct;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsRatingReview;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsUsers;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class ReviewAndRatingDAOImpl extends AbstractIptDao<OtsRatingReview, String> implements ReviewAndRatingDAO {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public ReviewAndRatingDAOImpl() {
		super(OtsRatingReview.class);
	}

	@Override
	public String addReviewAndRating(AddReviewAndRatingRequest addReviewAndRatingRequest) {
		Map<String, Object> queryParameter = new HashMap<>();
		java.util.Date utilDate = new java.util.Date();
		java.sql.Timestamp currentDate = new java.sql.Timestamp(utilDate.getTime());
		System.out.println("Date time = "+ currentDate); 

		try {
			OtsProduct productId = new OtsProduct();
			productId.setOtsProductId(UUID.fromString(addReviewAndRatingRequest.getRequestData().getProductId()));
			queryParameter.put("otsProductId",productId);
			
			OtsUsers customerId = new OtsUsers();
			customerId.setOtsUsersId(UUID.fromString(addReviewAndRatingRequest.getRequestData().getCustomerId()));
			queryParameter.put("otsCustomerId",customerId);
			
			OtsRatingReview ratingReview = new OtsRatingReview();
			ratingReview.setOtsProductId(productId);
			ratingReview.setOtsCustomerId(customerId);
			ratingReview.setOtsRatingReviewTitle(addReviewAndRatingRequest.getRequestData().getOtsRatingReviewTitle());
			ratingReview.setOtsRatingReviewComment(addReviewAndRatingRequest.getRequestData().getOtsRatingReviewComment());
			ratingReview.setOtsRatingReviewRating(Integer.parseInt(addReviewAndRatingRequest.getRequestData().getOtsRatingReviewRating()));
			ratingReview.setOtsRatingReviewAddedDate(currentDate);
			ratingReview.setOtsRatingReviewStatus("active");
			
			OtsOrder orderId= new OtsOrder();	
			orderId.setOtsOrderId(UUID.fromString(addReviewAndRatingRequest.getRequestData().getOrderId()));
			ratingReview.setOtsOrderId(orderId);
			super.getEntityManager().merge(ratingReview);
		}catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return "Inserted";
	}

	@Override
	public List<GetReviewAndRatingResponse> getReviewAndRating(GetReviewRatingRequest getReviewRatingRequest) {	
		List<OtsRatingReview> ratingReviews = new ArrayList<OtsRatingReview>();	
		List<GetReviewAndRatingResponse> getReviewAndRatingResponses= new ArrayList<GetReviewAndRatingResponse>();
		
		String searchKey=getReviewRatingRequest.getRequest().getSearchKey();
		Map<String, Object> queryParameter = new HashMap<>();
		
		try{
			switch(searchKey) {
		    case "product":
			    	OtsProduct productId = new OtsProduct();
			        productId.setOtsProductId(UUID.fromString(getReviewRatingRequest.getRequest().getSearchvalue()));
			        queryParameter.put("otsProductId", productId);
			        ratingReviews = super.getResultListByNamedQuery("OtsRatingReview.getReviewAndRatingByProductIdAndStatus", queryParameter);
			        break;
			        
		    case "customer":
			    	OtsUsers customerId = new OtsUsers();
			        customerId.setOtsUsersId(UUID.fromString(getReviewRatingRequest.getRequest().getSearchvalue()));
			        queryParameter.put("otsCustomerId", customerId);
			        ratingReviews = super.getResultListByNamedQuery("OtsRatingReview.getReviewAndRatingByCustomerIdAndStatus", queryParameter);
			        break;  
			        
		    case "distributor":
			    	OtsUsers distributorId = new OtsUsers();
			        distributorId.setOtsUsersId(UUID.fromString(getReviewRatingRequest.getRequest().getSearchvalue()));
			        queryParameter.put("otsDistributorId", distributorId);
			        ratingReviews = super.getResultListByNamedQuery("OtsRatingReview.getReviewAndRatingByDistributorId", queryParameter);
			        break;
		    default:
		        return null;
            }
			getReviewAndRatingResponses = ratingReviews.stream().map(ratingReview -> convertEntityToModel(ratingReview)).collect(Collectors.toList());
		}catch (NoResultException e) {
        	logger.error("Exception while fetching data from DB :"+e.getMessage());
        	throw new BusinessException("review and rating list is empty", e);
        }
		return getReviewAndRatingResponses;
	}
	
	GetReviewAndRatingResponse convertEntityToModel(OtsRatingReview ratingReview) {
		GetReviewAndRatingResponse getreviewAndRatingResponse = new GetReviewAndRatingResponse();
		
		getreviewAndRatingResponse.setProductId(ratingReview.getOtsProductId()==null?"":ratingReview.getOtsProductId().getOtsProductId().toString());
		getreviewAndRatingResponse.setProductName(ratingReview.getOtsProductId().getOtsProductName()==null?"":ratingReview.getOtsProductId().getOtsProductName());
		getreviewAndRatingResponse.setOtsRatingReviewId(ratingReview.getOtsRatingReviewId()==null?"":ratingReview.getOtsRatingReviewId().toString());
		getreviewAndRatingResponse.setOtsRatingReviewTitle(ratingReview.getOtsRatingReviewTitle()==null?"":ratingReview.getOtsRatingReviewTitle());
		getreviewAndRatingResponse.setOtsRatingReviewRating(ratingReview.getOtsRatingReviewRating()==null?"":ratingReview.getOtsRatingReviewRating().toString());
		getreviewAndRatingResponse.setOtsRatingReviewComment(ratingReview.getOtsRatingReviewComment()==null?"":ratingReview.getOtsRatingReviewComment());
		getreviewAndRatingResponse.setOtsRatingReviewAddedDate(ratingReview.getOtsRatingReviewAddedDate()==null?"":ratingReview.getOtsRatingReviewAddedDate().toString());	
		getreviewAndRatingResponse.setCustomerId(ratingReview.getOtsCustomerId().getOtsUsersId()==null?"":ratingReview.getOtsCustomerId().getOtsUsersId().toString());	
		getreviewAndRatingResponse.setCustomerName(ratingReview.getOtsCustomerId().getOtsUsersFirstname()==null?"":ratingReview.getOtsCustomerId().getOtsUsersFirstname());
		getreviewAndRatingResponse.setOrderId(ratingReview.getOtsOrderId().getOtsOrderId()==null?"":ratingReview.getOtsOrderId().getOtsOrderId().toString());
		getreviewAndRatingResponse.setOtsRatingReviewStatus(ratingReview.getOtsRatingReviewStatus()==null?"":ratingReview.getOtsRatingReviewStatus());
		
		return getreviewAndRatingResponse;
	}
	
	@Override
	public String updateReviewStatus(UpdateReviewStatusRequest updateReviewStatusRequest) {
		try {
			OtsRatingReview otsRatingReview = new OtsRatingReview();
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsRatingReviewId",Integer.parseInt(updateReviewStatusRequest.getRequest().getOtsReviewRatingId()));
			otsRatingReview = super.getResultByNamedQuery("OtsRatingReview.getReviewAndRatingByReviewAndRatingId", queryParameter);
			
			otsRatingReview.setOtsRatingReviewStatus(updateReviewStatusRequest.getRequest().getOtsReviewRatingStatus());
			super.getEntityManager().merge(otsRatingReview);
		}catch (NoResultException e) {
        	logger.error("Exception while fetching data from DB :"+e.getMessage());
        	throw new BusinessException("review and rating list is empty", e);
        }
		return "Updated";
	}
	
	@Override
	public String updateReviewAndRating(AddReviewAndRatingRequest addReviewAndRatingRequest) {
		try {
			OtsRatingReview ratingReview = new OtsRatingReview();
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsRatingReviewId",Integer.parseInt(addReviewAndRatingRequest.getRequestData().getOtsRatingReviewId()));
			ratingReview = super.getResultByNamedQuery("OtsRatingReview.getReviewAndRatingByReviewAndRatingId", queryParameter);

			ratingReview.setOtsRatingReviewTitle(addReviewAndRatingRequest.getRequestData().getOtsRatingReviewTitle());
			ratingReview.setOtsRatingReviewComment(addReviewAndRatingRequest.getRequestData().getOtsRatingReviewComment());
			ratingReview.setOtsRatingReviewRating(Integer.parseInt(addReviewAndRatingRequest.getRequestData().getOtsRatingReviewRating()));

			super.getEntityManager().merge(ratingReview);
		}catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return "Updated";
	}
	
	@Override
	public List<GetReviewAndRatingResponse> getReviewAndRatingForProduct(String productId) {	
		List<OtsRatingReview> ratingReviews = new ArrayList<OtsRatingReview>();	
		List<GetReviewAndRatingResponse> getReviewAndRatingResponses= new ArrayList<GetReviewAndRatingResponse>();
		try{
			OtsProduct ProductId = new OtsProduct();
			ProductId.setOtsProductId(UUID.fromString(productId)); 
			
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsProductId",ProductId);
			ratingReviews = super.getResultListByNamedQuery("OtsRatingReview.getReviewAndRatingByProductId", queryParameter);
			getReviewAndRatingResponses = ratingReviews.stream().map(ratingReview -> convertEntityToModel(ratingReview)).collect(Collectors.toList());
		}catch (NoResultException e) {
        	logger.error("Exception while fetching data from DB :"+e.getMessage());
        	throw new BusinessException("review and rating list is empty", e);
        }
		return getReviewAndRatingResponses;
	}
	
	@Override
	public List<GetReviewAndRatingResponse> getReviewAndRatingByOrderId(String orderId,String productId, String customerId) {	
		List<OtsRatingReview> ratingReviews = new ArrayList<OtsRatingReview>();	
		List<GetReviewAndRatingResponse> getReviewAndRatingResponses= new ArrayList<GetReviewAndRatingResponse>();
		try{
			OtsOrder OrderId = new OtsOrder();
			OrderId.setOtsOrderId(UUID.fromString(orderId));
			OtsUsers CustomerId = new OtsUsers();
			CustomerId.setOtsUsersId(UUID.fromString(customerId));
			OtsProduct ProductId = new OtsProduct();
			ProductId.setOtsProductId(UUID.fromString(productId)); 
			
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsOrderId",OrderId);
			queryParameter.put("otsProductId",ProductId);
			queryParameter.put("otsCustomerId",CustomerId);
			ratingReviews = super.getResultListByNamedQuery("OtsRatingReview.getReviewAndRatingByOrderId", queryParameter);
			getReviewAndRatingResponses = ratingReviews.stream().map(ratingReview -> convertEntityToModel(ratingReview)).collect(Collectors.toList());
		}catch (NoResultException e) {
        	logger.error("Exception while fetching data from DB :"+e.getMessage());
        	throw new BusinessException("review and rating list is empty", e);
        }
		return getReviewAndRatingResponses;
	}
	
}
