package com.fuso.enterprise.ots.srv.server.dao.impl;

import java.util.ArrayList;
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
import com.fuso.enterprise.ots.srv.api.model.domain.ServicePincode;
import com.fuso.enterprise.ots.srv.api.service.response.GetServiceableLocationResponse;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.ServiceDistrictDAO;
import com.fuso.enterprise.ots.srv.server.dao.ServicePincodeDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsServiceDistrict;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsServicePincode;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Service
@Transactional
public class ServicePincodeDAOImpl extends AbstractIptDao<OtsServicePincode, String> implements ServicePincodeDAO {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public ServicePincodeDAOImpl() {
		super(OtsServicePincode.class);
	}
	
	private ServicePincode convertPincodeDetailsFromEntityToDomain(OtsServicePincode otsServicePincode) {
		ServicePincode serivicepincodedeatails = new ServicePincode();			
		serivicepincodedeatails.setPincode(otsServicePincode.getOtsPincode()== null ? "":otsServicePincode.getOtsPincode().toString());
		serivicepincodedeatails.setOfficeName(otsServicePincode.getOtsOfficeName()== null ? "" : otsServicePincode.getOtsOfficeName());			
		serivicepincodedeatails.setPincodeId(otsServicePincode.getOtsPincodeId() == null? "" : otsServicePincode.getOtsPincodeId().toString());
		serivicepincodedeatails.setStateName(otsServicePincode.getOtsStateName()== null?"" :otsServicePincode.getOtsStateName() );
		serivicepincodedeatails.setDistrictName(otsServicePincode.getOtsDistrictName()== null?"" :otsServicePincode.getOtsDistrictName() );
		serivicepincodedeatails.setStateId(otsServicePincode.getOtsStateId()== null?"" :otsServicePincode.getOtsStateId().getOtsStateId().toString());
		serivicepincodedeatails.setDistrictId(otsServicePincode.getOtsDistrictId()== null?"" :otsServicePincode.getOtsDistrictId().getOtsDistrictId().toString());
		
		return serivicepincodedeatails;
	}
	
	@Override
	public List<ServicePincode> getPincodeByDistrict(String districtId) {
       List<ServicePincode> pincodeList = new ArrayList<ServicePincode>();
       List<OtsServicePincode> otsPincodeList = new ArrayList<OtsServicePincode>();
       try {
	       Map<String, Object> queryparameter = new HashMap<>();
	       OtsServiceDistrict DistrictId = new OtsServiceDistrict();
	       DistrictId.setOtsDistrictId(Integer.parseInt(districtId));
	       
	       queryparameter.put("otsDistrictId",DistrictId);
	       otsPincodeList = super.getResultListByNamedQuery("OtsServicePincode.getPincodebyDistrict", queryparameter);
	       pincodeList = otsPincodeList.stream().map(servicePincode -> convertPincodeDetailsFromEntityToDomain(servicePincode)).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
       return pincodeList;
	}

	@Override
	public List<ServicePincode> getPincodeDetails(String pincode ){
		List<ServicePincode> pincodeList = new ArrayList<ServicePincode>();
		List<OtsServicePincode> otsPincodeList = new ArrayList<OtsServicePincode>();
		try {
			 Map<String, Object> queryparameter = new HashMap<>();
			 queryparameter.put("otsPincode", Integer.parseInt(pincode));
			 otsPincodeList	 = super.getResultListByNamedQuery("OtsServicePincode.getPincodeDetails", queryparameter);
			 pincodeList = otsPincodeList.stream().map(Pincodes -> convertPincodeDetailsFromEntityToDomain(Pincodes)).collect(Collectors.toList()); 
		}catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		 return pincodeList;
	}


}
	
	
	
	
	
	
	
	
	
	
	
	
	
