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

import com.fuso.enterprise.ots.srv.api.service.request.AddWishListRequest;
import com.fuso.enterprise.ots.srv.api.service.response.GetwishListResponse;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.OtsProductWishlistDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsProduct;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsProductWishlist;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsUsers;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class OtsProductWishlistDAOImpl extends AbstractIptDao<OtsProductWishlist, String> implements  OtsProductWishlistDAO{

	private final Logger logger = LoggerFactory.getLogger(getClass()); 
	
	public OtsProductWishlistDAOImpl() {
		super(OtsProductWishlist.class);
	}

	@Override
	public String addWishList(AddWishListRequest addWishListRequest) {	
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			OtsProduct productId = new OtsProduct();
			productId.setOtsProductId(UUID.fromString(addWishListRequest.getRequestData().getProductId()));
			queryParameter.put("otsProductId",productId);
			
			OtsUsers customerId = new OtsUsers();
			customerId.setOtsUsersId(UUID.fromString(addWishListRequest.getRequestData().getCustomerId()));
			queryParameter.put("otsCustomerId",customerId);
			OtsProductWishlist productWishList = new OtsProductWishlist();
			
			List<OtsProductWishlist> otsProductWishlist = super.getResultListByNamedQuery("OtsProductWishlist.getWhishListByCustomerIdAndProductId", queryParameter);
			if(otsProductWishlist.size() == 0)
			{	
				productWishList.setOtsProductId(productId);
				productWishList.setOtsCustomerId(customerId);
				super.getEntityManager().merge(productWishList);
				return "Product Added To Wishlist";
			}
			else
			{
				return "Product Already Added To Wishlist";
			}
		}catch(Exception e) {
    		logger.error("Exception while inserting data into DB:"+e.getMessage());
    		e.printStackTrace();
    		throw new BusinessException(e.getMessage(), e);
    	}catch (Throwable e) {
    		logger.error("Exception while inserting data into DB:"+e.getMessage());
    		e.printStackTrace();
    		throw new BusinessException(e.getMessage(), e);
    	}
		
	}
	
	@Override
	public List<GetwishListResponse> getWishList(String customerId) {
		List<OtsProductWishlist> otsProductWishlist = new ArrayList<OtsProductWishlist>();
		List<GetwishListResponse> getwishListResponseList= new ArrayList<GetwishListResponse>();
		try {
			OtsUsers otsCustomerId = new OtsUsers();
			otsCustomerId.setOtsUsersId(UUID.fromString(customerId));
			
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsCustomerId",otsCustomerId);
			otsProductWishlist = super.getResultListByNamedQuery("OtsProductWishlist.getWhishListByCustomerId", queryParameter);

			getwishListResponseList = otsProductWishlist.stream().map(productWishlist -> convertEntityToModel(productWishlist)).collect(Collectors.toList());
		}catch(Exception e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		e.printStackTrace();
    		throw new BusinessException(e.getMessage(), e);
    	}catch (Throwable e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		e.printStackTrace();
    		throw new BusinessException(e.getMessage(), e);
    	}
		return getwishListResponseList;
	}
	
	GetwishListResponse convertEntityToModel(OtsProductWishlist productWishlist) {
		GetwishListResponse getwishListResponse = new GetwishListResponse();
		getwishListResponse.setProductId(productWishlist.getOtsProductId().getOtsProductId().toString());
		getwishListResponse.setProductName(productWishlist.getOtsProductId().getOtsProductName() == null?"":productWishlist.getOtsProductId().getOtsProductName());
		getwishListResponse.setProductImage(productWishlist.getOtsProductId().getOtsProductImage() == null?"":productWishlist.getOtsProductId().getOtsProductImage());
		getwishListResponse.setProductPrice(productWishlist.getOtsProductId().getOtsProductPrice()== null?"":productWishlist.getOtsProductId().getOtsProductPrice().toString());
		getwishListResponse.setProductBasePrice(productWishlist.getOtsProductId().getOtsProductBasePrice() == null?"":productWishlist.getOtsProductId().getOtsProductBasePrice().toString());
		getwishListResponse.setProductDiscountPrice(productWishlist.getOtsProductId().getOtsProductDiscountPrice() == null?"":productWishlist.getOtsProductId().getOtsProductDiscountPrice());
		getwishListResponse.setProductDiscountPercentage(productWishlist.getOtsProductId().getOtsProductDiscountPercentage()== null?"":productWishlist.getOtsProductId().getOtsProductDiscountPercentage());
		getwishListResponse.setOtsProductCountry(productWishlist.getOtsProductId().getOtsProductCountry()== null?"":productWishlist.getOtsProductId().getOtsProductCountry());
		getwishListResponse.setOtsProductCountryCode(productWishlist.getOtsProductId().getOtsProductCountryCode() == null?"":productWishlist.getOtsProductId().getOtsProductCountryCode());
		getwishListResponse.setOtsProductCurrency(productWishlist.getOtsProductId().getOtsProductCurrency() == null?"":productWishlist.getOtsProductId().getOtsProductCurrency());
		getwishListResponse.setOtsProductCurrencySymbol(productWishlist.getOtsProductId().getOtsProductCurrencySymbol()== null?"":productWishlist.getOtsProductId().getOtsProductCurrencySymbol());
		
		return getwishListResponse;
	}

	@Override
	public String removeFromWishList(AddWishListRequest addWishListRequest) {
		Map<String, Object> queryParameter = new HashMap<>();
		OtsProductWishlist otsProductWishlist = new OtsProductWishlist();

		OtsProduct productId = new OtsProduct();
		productId.setOtsProductId(UUID.fromString(addWishListRequest.getRequestData().getProductId()));
		queryParameter.put("otsProductId",productId);
		
		OtsUsers customerId = new OtsUsers();
		customerId.setOtsUsersId(UUID.fromString(addWishListRequest.getRequestData().getCustomerId()));
		queryParameter.put("otsCustomerId",customerId);
		try {
			otsProductWishlist = super.getResultByNamedQuery("OtsProductWishlist.getWhishListByCustomerIdAndProductId", queryParameter);
			super.getEntityManager().remove(otsProductWishlist);
		}catch(Exception e) {
			e.printStackTrace();
			return "No data found";
		}
		return "success";
	}

}
