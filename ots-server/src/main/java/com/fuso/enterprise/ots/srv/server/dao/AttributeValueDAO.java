package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.AttributeValue;
import com.fuso.enterprise.ots.srv.api.model.domain.AttributeValueName;
import com.fuso.enterprise.ots.srv.api.service.request.AddAttributeValueRequest;

public interface AttributeValueDAO {

	List<AttributeValue> getAttributeValueForAttributeKeyId(String attributeKeyId);

	List<AttributeValue> addAttributeValue(AddAttributeValueRequest addAttributeValueRequest);

	List<AttributeValueName> checkAttributeValue(AddAttributeValueRequest addAttributeValueRequest);

}
