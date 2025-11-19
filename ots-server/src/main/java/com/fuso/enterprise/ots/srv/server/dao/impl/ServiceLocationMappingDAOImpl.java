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
import com.fuso.enterprise.ots.srv.api.model.domain.ServiceLocationMapping;
import com.fuso.enterprise.ots.srv.api.service.request.AddServiceLocationMappingRequest;
import com.fuso.enterprise.ots.srv.api.service.response.ServiceLocationResponse;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.ServiceLocationMappingDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsProductLocationMapping;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsServiceLocationMapping;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsUsers;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class ServiceLocationMappingDAOImpl extends AbstractIptDao<OtsServiceLocationMapping, String>  implements ServiceLocationMappingDAO {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public ServiceLocationMappingDAOImpl() {
		super(OtsServiceLocationMapping.class);
		// TODO Auto-generated constructor stub
	}
	
	private ServiceLocationMapping convertServiceLocationMappingFromEntityToDomain(OtsServiceLocationMapping otsServiceLocationMapping) {
	    ServiceLocationMapping serviceLocationMapping = new ServiceLocationMapping();

	    serviceLocationMapping.setServiceLocationMappingId(otsServiceLocationMapping.getOtsServiceLocationMappingId() != null ? otsServiceLocationMapping.getOtsServiceLocationMappingId().toString() : null);
	    serviceLocationMapping.setServiceId(otsServiceLocationMapping.getOtsServiceId() != null ? otsServiceLocationMapping.getOtsServiceId() : null);
	    serviceLocationMapping.setLocationId(otsServiceLocationMapping.getOtsServiceLocationId() != null ? otsServiceLocationMapping.getOtsServiceLocationId() : null);
	    serviceLocationMapping.setLocationName(otsServiceLocationMapping.getOtsServiceLocationName() != null ? otsServiceLocationMapping.getOtsServiceLocationName() : null);
	    serviceLocationMapping.setProviderId(otsServiceLocationMapping.getOtsProviderId() != null ? otsServiceLocationMapping.getOtsProviderId().getOtsUsersId().toString() : null);

	    return serviceLocationMapping;
	}

	@Transactional
	@Override
	public String addServiceLocationMapping(AddServiceLocationMappingRequest addServiceLocationMappingRequest) {
		try {
			for(int i=0; i<addServiceLocationMappingRequest.getRequest().getServiceLocationIDName().size(); i++) {
				OtsServiceLocationMapping serviceLocationMapping = new OtsServiceLocationMapping();
				
				OtsUsers providerId = new OtsUsers();
				providerId.setOtsUsersId(UUID.fromString(addServiceLocationMappingRequest.getRequest().getProviderId()));
				
				serviceLocationMapping.setOtsProviderId(providerId);
				serviceLocationMapping.setOtsServiceLocationId(addServiceLocationMappingRequest.getRequest().getServiceLocationIDName().get(i).getLocationId());
				serviceLocationMapping.setOtsServiceLocationName(addServiceLocationMappingRequest.getRequest().getServiceLocationIDName().get(i).getLocationName());
				if(addServiceLocationMappingRequest.getRequest().getServiceId() == null || addServiceLocationMappingRequest.getRequest().getServiceId().equals("")) {
					serviceLocationMapping.setOtsServiceId(null);
				}else {
					serviceLocationMapping.setOtsServiceId(addServiceLocationMappingRequest.getRequest().getServiceId() );
				}
		     	save(serviceLocationMapping);
		     	System.out.println("am insreredted my data ="+serviceLocationMapping.getOtsServiceId());
			}
		}catch(Exception e){
			logger.error("Exception while inserting data into DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		catch (Throwable e) {
			logger.error("Exception while inserting data into DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return "Inserted";
	}

	@Transactional
	@Override
	public List<ServiceLocationMapping> addServiceLocationMappings(AddServiceLocationMappingRequest addServiceLocationMappingRequest) {
		   List<ServiceLocationMapping> serviceLocationMappings = new ArrayList<>();
		    
		    try {
		        for (int i = 0; i < addServiceLocationMappingRequest.getRequest().getServiceLocationIDName().size(); i++) {
		        	OtsServiceLocationMapping otsServiceLocationMapping = new OtsServiceLocationMapping();
		            
		            OtsUsers providerId = new OtsUsers();
		            providerId.setOtsUsersId(UUID.fromString(addServiceLocationMappingRequest.getRequest().getProviderId()));
		            
		            otsServiceLocationMapping.setOtsProviderId(providerId);
		            otsServiceLocationMapping.setOtsServiceLocationId(addServiceLocationMappingRequest.getRequest().getServiceLocationIDName().get(i).getLocationId());
		            otsServiceLocationMapping.setOtsServiceLocationName(addServiceLocationMappingRequest.getRequest().getServiceLocationIDName().get(i).getLocationName());
		            
		            if (addServiceLocationMappingRequest.getRequest().getServiceId() == null || addServiceLocationMappingRequest.getRequest().getServiceId().isEmpty()) {
		            	otsServiceLocationMapping.setOtsServiceId(null);
		            } else {
		            	otsServiceLocationMapping.setOtsServiceId(addServiceLocationMappingRequest.getRequest().getServiceId());
		            }
		            
		            save(otsServiceLocationMapping);
		            System.out.println("Inserted my data = " + otsServiceLocationMapping.getOtsServiceLocationId());
		        }

		        // Fetch and convert after the loop
		        Map<String, Object> queryParameter = new HashMap<>();
		        OtsUsers providerId = new OtsUsers();
		        providerId.setOtsUsersId(UUID.fromString(addServiceLocationMappingRequest.getRequest().getProviderId()));
		        queryParameter.put("otsProviderId", providerId);
		        
		        List<OtsServiceLocationMapping> productLocationMap = super.getResultListByNamedQuery("OtsServiceLocationMapping.getAllLocationsMappedToProvider", queryParameter);
		        serviceLocationMappings = productLocationMap.stream() .map(this::convertServiceLocationMappingFromEntityToDomain).collect(Collectors.toList());
		        
		    } catch (Exception e) {
		        logger.error("Exception while inserting data into DB: " + e.getMessage(), e);
		        throw new BusinessException(e.getMessage(), e);
		    }
		    
		    return serviceLocationMappings;
	}

	@Override
	public List<ServiceLocationMapping> getLocationsMappedToService(String serviceId) {
		List<ServiceLocationMapping> serviceLocationMapping = new ArrayList<ServiceLocationMapping>();
		List<OtsServiceLocationMapping> otsServiceLocationMapping = new ArrayList<OtsServiceLocationMapping>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsServiceId", UUID.fromString(serviceId));
			
			otsServiceLocationMapping = super.getResultListByNamedQuery("OtsProductLocationMapping.getLocationsMappedToProduct", queryParameter);
			serviceLocationMapping = otsServiceLocationMapping.stream().map(serviceLocation -> convertServiceLocationMappingFromEntityToDomain(serviceLocation)).collect(Collectors.toList());
			System.out.println("am getting output productLocationMapping ="+serviceLocationMapping.size());
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return serviceLocationMapping;
	}
	
	@Override
	public List<ServiceLocationMapping> getLocationsMappedToProviderOnly(String providerId) {
		List<ServiceLocationMapping> serviceLocationMapping = new ArrayList<ServiceLocationMapping>();
		List<OtsServiceLocationMapping> serviceLocationMap = new ArrayList<OtsServiceLocationMapping>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			OtsUsers ProviderId = new OtsUsers();
			ProviderId.setOtsUsersId(UUID.fromString(providerId));
			queryParameter.put("otsProviderId", ProviderId);
			
			serviceLocationMap = super.getResultListByNamedQuery("OtsServiceLocationMapping.getLocationsMappedToProviderOnly", queryParameter);
			System.out.println("am printing data in dao class  ="+serviceLocationMap.size());
			serviceLocationMapping = serviceLocationMap.stream().map(serviceLocation -> convertServiceLocationMappingFromEntityToDomain(serviceLocation)).collect(Collectors.toList());
			
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return serviceLocationMapping;
	}

	@Override
	public List<ServiceLocationMapping> getLocationsMappedToProviderAndService(String providerId, String serviceId) {
		List<ServiceLocationMapping> serviceLocationMapping = new ArrayList<ServiceLocationMapping>();
		List<OtsServiceLocationMapping> serviceLocationMap = new ArrayList<OtsServiceLocationMapping>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			OtsUsers otsProviderId = new OtsUsers();
			otsProviderId.setOtsUsersId(UUID.fromString(providerId));
			queryParameter.put("otsProviderId", otsProviderId);
			queryParameter.put("otsServiceId", UUID.fromString(serviceId));
			
			serviceLocationMap = super.getResultListByNamedQuery("OtsServiceLocationMapping.getLocationsMappedToProviderAndService", queryParameter);
			serviceLocationMapping = serviceLocationMap.stream().map(serviceLocation -> convertServiceLocationMappingFromEntityToDomain(serviceLocation)).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return serviceLocationMapping;
	}

	
}
