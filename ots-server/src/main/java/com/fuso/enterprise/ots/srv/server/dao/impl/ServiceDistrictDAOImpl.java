package com.fuso.enterprise.ots.srv.server.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fuso.enterprise.ots.srv.api.model.domain.ServiceDistrict;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.ServiceDistrictDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsServiceDistrict;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsServiceState;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Service
@Transactional
public class ServiceDistrictDAOImpl extends AbstractIptDao<OtsServiceDistrict, String> implements ServiceDistrictDAO {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public ServiceDistrictDAOImpl() {
		super(OtsServiceDistrict.class);
	}
	
	private ServiceDistrict convertStateDetailsFromEntityToDomain(OtsServiceDistrict otsServiceDistrict) {
		ServiceDistrict serviceDistrictDetails = new ServiceDistrict();
		serviceDistrictDetails.setOtsDistrictId(otsServiceDistrict.getOtsDistrictId()== null ? null: otsServiceDistrict.getOtsDistrictId().toString());
		serviceDistrictDetails.setOtsDistrictName(otsServiceDistrict.getOtsDistrictName()== null ? null: otsServiceDistrict.getOtsDistrictName());
		serviceDistrictDetails.setOtsDistrictStatus(otsServiceDistrict.getOtsDistrictStatus()== null ? null:otsServiceDistrict.getOtsDistrictStatus());
		serviceDistrictDetails.setOtsStateId(otsServiceDistrict.getOtsStateId()== null ? null:otsServiceDistrict.getOtsStateId().getOtsStateId().toString());
		return serviceDistrictDetails;

	}
	
	@Override
	public List<ServiceDistrict> getDistrictByStateId(String stateId) {
		List<ServiceDistrict> districtList = new ArrayList<ServiceDistrict>();
		List<OtsServiceDistrict> otsServiceDistrict = new ArrayList<OtsServiceDistrict>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			OtsServiceState StateId = new OtsServiceState();
			StateId.setOtsStateId(Integer.parseInt(stateId));
			
			queryParameter.put("otsStateId",StateId);
			otsServiceDistrict = super.getResultListByNamedQuery("OtsServiceDistrict.getDistrictByStateId", queryParameter);
			districtList = otsServiceDistrict.stream().map(serviceDistrict -> convertStateDetailsFromEntityToDomain(serviceDistrict)).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return districtList;
	}
	
	@Override
	public List<ServiceDistrict> getAllDistricts() {
		List<ServiceDistrict> districtList = new ArrayList<ServiceDistrict>();
		List<OtsServiceDistrict> otsServiceDistrict = new ArrayList<OtsServiceDistrict>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			otsServiceDistrict = super.getResultListByNamedQuery("OtsServiceDistrict.getAllDistricts", queryParameter);
			districtList = otsServiceDistrict.stream().map(serviceDistrict -> convertStateDetailsFromEntityToDomain(serviceDistrict)).collect(Collectors.toList());   
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return districtList ;
	}	 
	
}
