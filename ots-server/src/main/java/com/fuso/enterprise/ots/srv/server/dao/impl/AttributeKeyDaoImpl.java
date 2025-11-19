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

import com.fuso.enterprise.ots.srv.api.model.domain.AttributeKey;
import com.fuso.enterprise.ots.srv.api.service.request.AddAttributeKeyRequest;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.AttributeKeyDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsAttributeKey;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsProduct;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class AttributeKeyDaoImpl extends AbstractIptDao<OtsAttributeKey, String> implements AttributeKeyDAO{
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public AttributeKeyDaoImpl() {
		super(OtsAttributeKey.class);
		// TODO Auto-generated constructor stub
	}
	
	private AttributeKey convertAttributeKeyDetailsFromEntityToDomain(OtsAttributeKey otsAttributeKey) {
		AttributeKey attributeKey = new AttributeKey();
		attributeKey.setAttributeKeyId((otsAttributeKey.getOtsAttributeKeyId()== null) ? "": otsAttributeKey.getOtsAttributeKeyId().toString());
		attributeKey.setAttributeKeyName((otsAttributeKey.getOtsAttributeKeyName()== null) ? "": otsAttributeKey.getOtsAttributeKeyName());
		attributeKey.setAttributeKeyDescription((otsAttributeKey.getOtsAttributeKeyDescription()== null) ? "": otsAttributeKey.getOtsAttributeKeyDescription());
		
		return attributeKey;
	}
	
	@Override
	public List<AttributeKey> getAllAttributeKey() {
		List<AttributeKey> variantKey = new ArrayList<AttributeKey>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			List<OtsAttributeKey> resultList = super.getResultListByNamedQuery("OtsAttributeKey.findAll", queryParameter);

			variantKey = resultList.stream().map(OtsAttributeKey -> convertAttributeKeyDetailsFromEntityToDomain(OtsAttributeKey)).collect(Collectors.toList());
		}catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return variantKey;
	}

	@Override
	public List<AttributeKey> addAttributeKey(AddAttributeKeyRequest addAttributeKeyRequest) {
		List<AttributeKey> attributeKeyDetails = new ArrayList<>();
		try {
            for(int i=0;i<addAttributeKeyRequest.getRequest().getAttributeKeyName().size();i++) {
				OtsAttributeKey otsAttributeKey = new OtsAttributeKey();
				otsAttributeKey.setOtsAttributeKeyName(addAttributeKeyRequest.getRequest().getAttributeKeyName().get(i));
				otsAttributeKey.setOtsAttributeKeyDescription(addAttributeKeyRequest.getRequest().getAttributeKeyDiscription().get(i));
				save(otsAttributeKey);
				
				attributeKeyDetails.add(convertAttributeKeyDetailsFromEntityToDomain(otsAttributeKey));
            }
		}catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return attributeKeyDetails;
	}
	
	@Override
	public List<AttributeKey> getUnMappedAttributeKeyandValuesForSubcategory(String subcategoryId) {
		List<AttributeKey> attributeKeyList = new ArrayList<AttributeKey>();
		List<OtsAttributeKey> otsAttributeKeyList  = new ArrayList<OtsAttributeKey>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			OtsProduct otsProductId = new OtsProduct();
			otsProductId.setOtsProductId(UUID.fromString(subcategoryId));
			queryParameter.put("otsSubcategoryId", otsProductId);
			otsAttributeKeyList = super.getResultListByNamedQuery("OtsAttributeKey.getUnMappedAttributeKeyandValuesForSubcategory", queryParameter);
			attributeKeyList = otsAttributeKeyList.stream().map(otsAttributeKey -> convertAttributeKeyDetailsFromEntityToDomain(otsAttributeKey)).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return attributeKeyList;
	}

}
