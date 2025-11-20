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
import org.springframework.transaction.annotation.Transactional;

import com.fuso.enterprise.ots.srv.api.model.domain.ProductLocationMapping;
import com.fuso.enterprise.ots.srv.api.service.request.AddProductLocationMappingRequest;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.ProductLocationMappingDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsProductLocationMapping;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsUsers;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class ProductLocationMappingDAOImpl extends AbstractIptDao<OtsProductLocationMapping, String> implements ProductLocationMappingDAO {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public ProductLocationMappingDAOImpl() {
		super(OtsProductLocationMapping.class);
		// TODO Auto-generated constructor stub
	}
	
	private ProductLocationMapping convertProductLocationMappingFromEntityToDomain(OtsProductLocationMapping otsProductLocationMapping) {
		ProductLocationMapping productLocationMapping = new ProductLocationMapping();
		productLocationMapping.setDistributorId((otsProductLocationMapping.getOtsDistributorId()== null) ? null: otsProductLocationMapping.getOtsDistributorId().getOtsUsersId().toString());
		productLocationMapping.setLocatioId((otsProductLocationMapping.getOtsProductLocationId()== null) ? null: otsProductLocationMapping.getOtsProductLocationId());
		productLocationMapping.setLocationName((otsProductLocationMapping.getOtsProductLocationName()== null) ? null: otsProductLocationMapping.getOtsProductLocationName().toString());
		productLocationMapping.setProductId((otsProductLocationMapping.getOtsProductId()== null) ? null: otsProductLocationMapping.getOtsProductId());
		productLocationMapping.setProductLocationMappingId((otsProductLocationMapping.getOtsProductLocationMappingId()== null) ? null: otsProductLocationMapping.getOtsProductLocationMappingId().toString());

		return productLocationMapping;
	}
	
	@Override
	public String addProductLocationMapping(AddProductLocationMappingRequest addProductLocationMappingRequest) {
		try {
			for(int i=0; i<addProductLocationMappingRequest.getRequest().getProductLocationIDName().size(); i++) {
				OtsProductLocationMapping otsProductLocationMapping = new OtsProductLocationMapping();
				
				OtsUsers DistributorId = new OtsUsers();
				DistributorId.setOtsUsersId(UUID.fromString(addProductLocationMappingRequest.getRequest().getDistributorId()));
				
				otsProductLocationMapping.setOtsDistributorId(DistributorId);
				otsProductLocationMapping.setOtsProductLocationId(addProductLocationMappingRequest.getRequest().getProductLocationIDName().get(i).getLocationId());
				otsProductLocationMapping.setOtsProductLocationName(addProductLocationMappingRequest.getRequest().getProductLocationIDName().get(i).getLocationName());
				if(addProductLocationMappingRequest.getRequest().getProductId() == null || addProductLocationMappingRequest.getRequest().getProductId().equals("")) {
					otsProductLocationMapping.setOtsProductId(null);
				}else {
					otsProductLocationMapping.setOtsProductId(addProductLocationMappingRequest.getRequest().getProductId());
				}
		     	save(otsProductLocationMapping);
		     	System.out.println("am insreredted my data ="+otsProductLocationMapping.getOtsProductLocationId());
			}
		}catch(Exception e){
			logger.error("Exception while inserting data into DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		catch (Throwable e) {
			logger.error("Exception while inserting data into DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return "Inserted";
	}
	
	@Override
	public List<ProductLocationMapping> addProductLocationMappings(AddProductLocationMappingRequest addProductLocationMappingRequest) {
	    List<ProductLocationMapping> productLocationMapping = new ArrayList<>();
	    
	    try {
	        for (int i = 0; i < addProductLocationMappingRequest.getRequest().getProductLocationIDName().size(); i++) {
	            OtsProductLocationMapping otsProductLocationMapping = new OtsProductLocationMapping();
	            
	            OtsUsers distributorId = new OtsUsers();
	            distributorId.setOtsUsersId(UUID.fromString(addProductLocationMappingRequest.getRequest().getDistributorId()));
	            
	            otsProductLocationMapping.setOtsDistributorId(distributorId);
	            otsProductLocationMapping.setOtsProductLocationId(addProductLocationMappingRequest.getRequest().getProductLocationIDName().get(i).getLocationId());
	            otsProductLocationMapping.setOtsProductLocationName(addProductLocationMappingRequest.getRequest().getProductLocationIDName().get(i).getLocationName());
	            
	            if (addProductLocationMappingRequest.getRequest().getProductId() == null || addProductLocationMappingRequest.getRequest().getProductId().isEmpty()) {
	                otsProductLocationMapping.setOtsProductId(null);
	            } else {
	                otsProductLocationMapping.setOtsProductId(addProductLocationMappingRequest.getRequest().getProductId());
	            }
	            
	            save(otsProductLocationMapping);
	            System.out.println("Inserted my data = " + otsProductLocationMapping.getOtsProductLocationId());
	        }

	        // Fetch and convert after the loop
	        Map<String, Object> queryParameter = new HashMap<>();
	        OtsUsers distributorId = new OtsUsers();
	        distributorId.setOtsUsersId(UUID.fromString(addProductLocationMappingRequest.getRequest().getDistributorId()));
	        queryParameter.put("otsDistributorId", distributorId);
	        
	        List<OtsProductLocationMapping> productLocationMap = super.getResultListByNamedQuery("OtsProductLocationMapping.getAllLocationsMappedToDistributor", queryParameter);
	        productLocationMapping = productLocationMap.stream() .map(this::convertProductLocationMappingFromEntityToDomain).collect(Collectors.toList());
	        
	    } catch (Exception e) {
	        logger.error("Exception while inserting data into DB: " + e.getMessage(), e);
	        throw new BusinessException(e.getMessage(), e);
	    }
	    
	    return productLocationMapping;
	}
	
	@Override
	public List<ProductLocationMapping> getLocationsMappedToProduct(String productId) {
		List<ProductLocationMapping> productLocationMapping = new ArrayList<ProductLocationMapping>();
		List<OtsProductLocationMapping> productLocationMap = new ArrayList<OtsProductLocationMapping>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsProductId", productId);
			
			productLocationMap = super.getResultListByNamedQuery("OtsProductLocationMapping.getLocationsMappedToProduct", queryParameter);
			productLocationMapping = productLocationMap.stream().map(productLocation -> convertProductLocationMappingFromEntityToDomain(productLocation)).collect(Collectors.toList());
			System.out.println("am getting output productLocationMapping ="+productLocationMapping.size());
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return productLocationMapping;
	}
	
	@Override
	public List<ProductLocationMapping> getProductsMappedToLocation(String locationId) {
		List<ProductLocationMapping> productLocationMapping = new ArrayList<ProductLocationMapping>();
		List<OtsProductLocationMapping> productLocationMap = new ArrayList<OtsProductLocationMapping>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsProductLocationId", locationId);
			
			productLocationMap = super.getResultListByNamedQuery("OtsProductLocationMapping.findByOtsProductLocationId", queryParameter);
			productLocationMapping = productLocationMap.stream().map(productLocation -> convertProductLocationMappingFromEntityToDomain(productLocation)).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return productLocationMapping;
	}
	
	@Transactional
	@Override
	public List<ProductLocationMapping> getLocationsMappedToDistributorOnly(String distributorId) {
		List<ProductLocationMapping> productLocationMapping = new ArrayList<ProductLocationMapping>();
		List<OtsProductLocationMapping> productLocationMap = new ArrayList<OtsProductLocationMapping>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			OtsUsers DistrubutorId = new OtsUsers();
			DistrubutorId.setOtsUsersId(UUID.fromString(distributorId));
			queryParameter.put("otsDistributorId", DistrubutorId);
			
			productLocationMap = super.getResultListByNamedQuery("OtsProductLocationMapping.getLocationsMappedToDistributorOnly", queryParameter);
			System.out.println("am printing data in dao class  ="+productLocationMap.size());
			productLocationMapping = productLocationMap.stream().map(productLocation -> convertProductLocationMappingFromEntityToDomain(productLocation)).collect(Collectors.toList());
			
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return productLocationMapping;
	}
	
	@Override
	public List<ProductLocationMapping> getLocationsMappedToDistributorAndProduct(String distributorId, String productId) {
		List<ProductLocationMapping> productLocationMapping = new ArrayList<ProductLocationMapping>();
		List<OtsProductLocationMapping> productLocationMap = new ArrayList<OtsProductLocationMapping>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			OtsUsers DistrubutorId = new OtsUsers();
			DistrubutorId.setOtsUsersId(UUID.fromString(distributorId));
			queryParameter.put("otsDistributorId", DistrubutorId);
			queryParameter.put("otsProductId", productId);
			
			productLocationMap = super.getResultListByNamedQuery("OtsProductLocationMapping.getLocationsMappedToDistributorAndProduct", queryParameter);
			productLocationMapping = productLocationMap.stream().map(productLocation -> convertProductLocationMappingFromEntityToDomain(productLocation)).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return productLocationMapping;
	}
	

}
