package com.fuso.enterprise.ots.srv.server.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fuso.enterprise.ots.srv.api.model.domain.ProductAttributesMapping;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.ProductAttributesMappingDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsAttributeKey;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsAttributeValue;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsProduct;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsProductAttributeMapping;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class ProductAttributesMappingDAOImpl extends AbstractIptDao<OtsProductAttributeMapping,String>implements ProductAttributesMappingDAO{

	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public ProductAttributesMappingDAOImpl() {
		super(OtsProductAttributeMapping.class);
		// TODO Auto-generated constructor stub
	}

	private ProductAttributesMapping convertAttributeKeyDetailsFromEntityToDomain(OtsProductAttributeMapping otsProductAttributeMapping) {
		ProductAttributesMapping  productAttributesMapping = new ProductAttributesMapping();
		productAttributesMapping.setAttributeKeyId(otsProductAttributeMapping.getOtsAttributeKeyId()== null ? null: otsProductAttributeMapping.getOtsAttributeKeyId().toString());
		productAttributesMapping.setAttributeValueId(otsProductAttributeMapping.getOtsAttributeValueId() == null ? "":otsProductAttributeMapping.getOtsAttributeValueId().toString());
		productAttributesMapping.setProductId(otsProductAttributeMapping.getOtsProductId() == null ? "":otsProductAttributeMapping.getOtsProductId().toString());
		return productAttributesMapping;
	}
	
    @Override
	public List<ProductAttributesMapping> addProductAttributesMapping(List<ProductAttributesMapping> addProductAttributeMappingRequest){
		List<ProductAttributesMapping> attributeMapping = new ArrayList<ProductAttributesMapping>();
		try {
			
			for(int i=0; i<addProductAttributeMappingRequest.size();i++) {
				OtsProductAttributeMapping productAttributeMapping = new OtsProductAttributeMapping();
				
				OtsAttributeKey attributeKeyId = new OtsAttributeKey();
				attributeKeyId.setOtsAttributeKeyId(Integer.parseInt(addProductAttributeMappingRequest.get(i).getAttributeKeyId()));
	            
	            OtsProduct productId = new OtsProduct();
	            productId.setOtsProductId(UUID.fromString(addProductAttributeMappingRequest.get(i).getProductId()));
	            
	            OtsAttributeValue attributeValueId = new OtsAttributeValue();
	            attributeValueId.setOtsAttributeValueId(Integer.parseInt(addProductAttributeMappingRequest.get(i).getAttributeValueId()));
	            
	            productAttributeMapping.setOtsAttributeKeyId(attributeKeyId);
	            productAttributeMapping.setOtsAttributeValueId(attributeValueId);
	            productAttributeMapping.setOtsProductId(productId);
	            save(productAttributeMapping);
	            attributeMapping.add(convertAttributeKeyDetailsFromEntityToDomain(productAttributeMapping));
			}
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return attributeMapping;
	}
	

}

