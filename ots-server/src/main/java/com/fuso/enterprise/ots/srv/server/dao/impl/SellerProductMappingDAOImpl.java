package com.fuso.enterprise.ots.srv.server.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.fuso.enterprise.ots.srv.api.model.domain.UserDetails;
import com.fuso.enterprise.ots.srv.api.service.request.GetSellerForProductRequest;
import com.fuso.enterprise.ots.srv.server.dao.SellerProductMappingDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsProduct;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsSellerProductMapping;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class SellerProductMappingDAOImpl extends AbstractIptDao<OtsSellerProductMapping, String> implements SellerProductMappingDAO{

	public SellerProductMappingDAOImpl() {
		super(OtsSellerProductMapping.class);
		
	}

	@Override
	public List<UserDetails> getSellerForProduct(GetSellerForProductRequest getSellerForProductRequest) {
		List<OtsSellerProductMapping> sellerProductMappingList = new ArrayList<OtsSellerProductMapping>();
		Map<String, Object> queryParameter = new HashMap<>();
		List<UserDetails> userDetailsList = new ArrayList<UserDetails>();
		try {
			OtsProduct productId = new OtsProduct();
			productId.setOtsProductId(UUID.fromString(getSellerForProductRequest.getProductId()));
			queryParameter.put("otsProductId", productId);
			sellerProductMappingList = super.getResultListByNamedQuery("OtsSellerProductMapping.getByProductId", queryParameter);
			//productList.stream().map(otsProduct -> convertProductDetailsFromEntityToDomain(otsProduct)).collect(Collectors.toList()); 
			userDetailsList = sellerProductMappingList.stream().map(sellerProductMapping -> convertEntityToModel(sellerProductMapping)).collect(Collectors.toList()); 
		}catch(Exception e) {
			System.out.println(e);
		}
		
		return userDetailsList;
	}

	UserDetails convertEntityToModel(OtsSellerProductMapping sellerProductMapping) {
		UserDetails userDetails = new UserDetails();
		userDetails.setFirstName(sellerProductMapping.getOtsSellerId().getOtsUsersFirstname());
		userDetails.setLastName(sellerProductMapping.getOtsSellerId().getOtsUsersLastname());
		userDetails.setUserId(sellerProductMapping.getOtsSellerId().getOtsUsersId().toString());
		userDetails.setEmailId(sellerProductMapping.getOtsSellerId().getOtsUsersEmailid());
		return userDetails;
	}
}
