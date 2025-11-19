package com.fuso.enterprise.ots.srv.server.dao.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fuso.enterprise.ots.srv.api.model.domain.SubCategoryAttributeMapping;
import com.fuso.enterprise.ots.srv.api.service.request.AddAttributeKeyForSubCategoryRequest;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.SubcategoryAttributeMappingDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsAttributeKey;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsProduct;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsSubcategoryAttributeMapping;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class SubcategoryAttributeMappingDaoImpl extends AbstractIptDao<OtsSubcategoryAttributeMapping, String> implements SubcategoryAttributeMappingDAO{

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public SubcategoryAttributeMappingDaoImpl() {
		super(OtsSubcategoryAttributeMapping.class);
		// TODO Auto-generated constructor stub
	}
	
	private SubCategoryAttributeMapping convertSubcategoryAttributeMappingFromEntityToDomain(OtsSubcategoryAttributeMapping otsSubcategoryAttributeMapping) {
		SubCategoryAttributeMapping subCategoryAttributeMapping = new SubCategoryAttributeMapping();
		subCategoryAttributeMapping.setOtsAttributeKeyId((otsSubcategoryAttributeMapping.getOtsAttributeKeyId() == null) ? "": otsSubcategoryAttributeMapping.getOtsAttributeKeyId().getOtsAttributeKeyId().toString());
		subCategoryAttributeMapping.setOtsSubcategoryId((otsSubcategoryAttributeMapping.getOtsSubcategoryId() == null) ? "": otsSubcategoryAttributeMapping.getOtsSubcategoryId().getOtsProductId().toString());
		subCategoryAttributeMapping.setOtsSubcategoryAttributeMappingId((otsSubcategoryAttributeMapping.getOtsSubcategoryAttributeMappingId() == null) ? "": otsSubcategoryAttributeMapping.getOtsSubcategoryAttributeMappingId().toString());
		subCategoryAttributeMapping.setOtsAttributeKeyName((otsSubcategoryAttributeMapping.getOtsAttributeKeyId().getOtsAttributeKeyName() == null) ? "": otsSubcategoryAttributeMapping.getOtsAttributeKeyId().getOtsAttributeKeyName());
		subCategoryAttributeMapping.setOtsAttributeKeyDescription((otsSubcategoryAttributeMapping.getOtsAttributeKeyId().getOtsAttributeKeyDescription() == null) ? "": otsSubcategoryAttributeMapping.getOtsAttributeKeyId().getOtsAttributeKeyDescription());
		
		return subCategoryAttributeMapping;
	}
	
	@Override
	public List<SubCategoryAttributeMapping> addAttributeKeyForSubCategory(AddAttributeKeyForSubCategoryRequest addAttributeKeyForSubCategoryRequest) {
		List<SubCategoryAttributeMapping> attributeKeyIdSubCatgory = new ArrayList<SubCategoryAttributeMapping>();
		
		try {
			for (int i = 0; i < addAttributeKeyForSubCategoryRequest.getRequest().getOtsAttributeKeyId().size(); i++) {
				OtsSubcategoryAttributeMapping subcategoryAttributeMapping = new OtsSubcategoryAttributeMapping();

				OtsAttributeKey attributekeyId = new OtsAttributeKey();
				attributekeyId.setOtsAttributeKeyId(Integer.parseInt(addAttributeKeyForSubCategoryRequest.getRequest().getOtsAttributeKeyId().get(i)));
				
				OtsProduct subcategoryId = new OtsProduct();
				subcategoryId.setOtsProductId(UUID.fromString(addAttributeKeyForSubCategoryRequest.getRequest().getOtsSubcategoryId()));
				
				subcategoryAttributeMapping.setOtsAttributeKeyId(attributekeyId);
				subcategoryAttributeMapping.setOtsSubcategoryId(subcategoryId);
				  
				save(subcategoryAttributeMapping);

				attributeKeyIdSubCatgory.add(convertSubcategoryAttributeMappingFromEntityToDomain(subcategoryAttributeMapping));
			}
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return attributeKeyIdSubCatgory;
	}	

	@Override
	public List<SubCategoryAttributeMapping> getAttributeKeyBySubcategory(String subcategoryId) {
		List<SubCategoryAttributeMapping> subcategoryAttributeMapping = new ArrayList<SubCategoryAttributeMapping>();
		List<OtsSubcategoryAttributeMapping> otssubcategoryAttributeMapping  = new ArrayList<OtsSubcategoryAttributeMapping>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			OtsProduct otsProductId = new OtsProduct();
			otsProductId.setOtsProductId(UUID.fromString(subcategoryId));
			queryParameter.put("otsSubcategoryId", otsProductId);
			otssubcategoryAttributeMapping = super.getResultListByNamedQuery("OtsSubcategoryAttributeMapping.getAttributeKeyForSubcategory", queryParameter);
			subcategoryAttributeMapping = otssubcategoryAttributeMapping.stream().map(subcategoryMapping -> convertSubcategoryAttributeMappingFromEntityToDomain(subcategoryMapping)).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return subcategoryAttributeMapping;
	}
	
	
}
