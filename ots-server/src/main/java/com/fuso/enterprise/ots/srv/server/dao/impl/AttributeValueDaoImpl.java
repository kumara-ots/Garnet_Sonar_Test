package com.fuso.enterprise.ots.srv.server.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fuso.enterprise.ots.srv.api.model.domain.AttributeValue;
import com.fuso.enterprise.ots.srv.api.model.domain.AttributeValueName;
import com.fuso.enterprise.ots.srv.api.service.request.AddAttributeValueRequest;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.AttributeValueDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsAttributeKey;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsAttributeValue;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class AttributeValueDaoImpl extends AbstractIptDao<OtsAttributeValue, String> implements AttributeValueDAO{
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public AttributeValueDaoImpl() {
		super(OtsAttributeValue.class);
		// TODO Auto-generated constructor stub
	}
	
	private AttributeValue convertAttributeValueDetailsFromEntityToDomain(OtsAttributeValue otsAttributeValue) {
		AttributeValue attributeValue = new AttributeValue();
		attributeValue.setAttributeValueId((otsAttributeValue.getOtsAttributeValueId()== null) ? "": otsAttributeValue.getOtsAttributeValueId().toString());
		attributeValue.setAttributeValueName((otsAttributeValue.getOtsAttributeValueName()== null) ? "": otsAttributeValue.getOtsAttributeValueName());
		attributeValue.setAttributeKeyId((otsAttributeValue.getOtsAttributeKeyId()== null) ? "": otsAttributeValue.getOtsAttributeKeyId().getOtsAttributeKeyId().toString());
		attributeValue.setAttributeKeyName((otsAttributeValue.getOtsAttributeKeyId().getOtsAttributeKeyName()== null) ? "": otsAttributeValue.getOtsAttributeKeyId().getOtsAttributeKeyName().toString());

		return attributeValue;
	}
	
	@Override
	public List<AttributeValue> getAttributeValueForAttributeKeyId(String attributeKeyId) {
		List<AttributeValue> attributeValueList = new ArrayList<AttributeValue>();
		List<OtsAttributeValue> otsAttributeValueList  = new ArrayList<OtsAttributeValue>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsAttributeKeyId", Integer.parseInt(attributeKeyId));
			otsAttributeValueList = super.getResultListByNamedQuery("OtsAttributeValue.getAttributeValueForAttributeKeyId", queryParameter);
			attributeValueList = otsAttributeValueList.stream().map(otsAttributeValue -> convertAttributeValueDetailsFromEntityToDomain(otsAttributeValue)).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return attributeValueList;
	}
	
	@Override
	public List<AttributeValue> addAttributeValue(AddAttributeValueRequest addAttributeValueRequest) {
	    List<AttributeValue> attributeValue = new ArrayList<AttributeValue>();
	    try {
	    	for (int i = 0; i < addAttributeValueRequest.getRequest().size(); i++) {
	            OtsAttributeKey otsAttributeKey = new OtsAttributeKey();
	            otsAttributeKey.setOtsAttributeKeyId(Integer.parseInt(addAttributeValueRequest.getRequest().get(i).getAttributeKeyId()));

	            OtsAttributeValue attributeValuesModel = new OtsAttributeValue();
	            attributeValuesModel.setOtsAttributeValueName(addAttributeValueRequest.getRequest().get(i).getAttributeValueName());
	            attributeValuesModel.setOtsAttributeKeyId(otsAttributeKey);
	            save(attributeValuesModel);

	            attributeValue.add(convertAttributeValueDetailsFromEntityToDomain(attributeValuesModel));
	        }
	    } catch (Exception e) {
	        logger.error("Exception while adding attribute values: " + e.getMessage());
	        throw new BusinessException("Error processing attribute values", e);
	    }
	    return attributeValue;
	}
	
	@Override
	public List<AttributeValueName> checkAttributeValue(AddAttributeValueRequest addAttributeValueRequest) {
	    List<AttributeValueName> attributeValueNameList = new ArrayList<>();
	    Set<String> processedAttributeValues = new HashSet<>();

	    try {
	        // Iterate over each request
	        for (int i = 0; i < addAttributeValueRequest.getRequest().size(); i++) {
	            Map<String, Object> queryParameter = new HashMap<>();

	            // Get attribute key ID from the request
	            Integer otsAttributeKeyId = Integer.parseInt(addAttributeValueRequest.getRequest().get(i).getAttributeKeyId());
	            queryParameter.put("otsAttributeKeyId", otsAttributeKeyId);
	            
	            // Get attribute value name from the request
	            String attributeValueName = addAttributeValueRequest.getRequest().get(i).getAttributeValueName();
	            queryParameter.put("otsAttributeValueName", attributeValueName);

	            // Check if the attribute value has already been processed
	            if (!processedAttributeValues.contains(attributeValueName)) {
	                // Query the database to check if the attribute value already exists
	                List<OtsAttributeValue> otsAttributeValueList = super.getResultListByNamedQuery("OtsAttributeValue.getAttributeValueByValueName", queryParameter);

	                // If the attribute value already exists, add it to the list with a description
	                if (otsAttributeValueList.size() != 0) {
	                    AttributeValueName attributeValuesModel = new AttributeValueName();
	                    attributeValuesModel.setAttributeValueName(attributeValueName);
	                    attributeValuesModel.setDescription("Value Name Already Exists");
	                    attributeValueNameList.add(attributeValuesModel);
	                }

	                // Add the attribute value to the set of processed values
	                processedAttributeValues.add(attributeValueName);
	            }
	        }
	    } catch (Exception e) {
	        // Log and throw BusinessException if an exception occurs
	        logger.error("Exception while fetching data from DB: " + e.getMessage());
	        e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
	    }

	    return attributeValueNameList;
	}

	

}
