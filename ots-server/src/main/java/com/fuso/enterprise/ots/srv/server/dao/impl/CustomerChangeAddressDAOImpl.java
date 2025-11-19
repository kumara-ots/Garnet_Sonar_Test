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

import com.fuso.enterprise.ots.srv.api.model.domain.CustomerChangeAddress;
import com.fuso.enterprise.ots.srv.api.service.request.AddCustomerChangeAddressRequest;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.CustomerChangeAddressDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsCustomerChangeAddress;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsUsers;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class CustomerChangeAddressDAOImpl extends AbstractIptDao<OtsCustomerChangeAddress, String> implements CustomerChangeAddressDAO{
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public CustomerChangeAddressDAOImpl() {
		super(OtsCustomerChangeAddress.class);
		// TODO Auto-generated constructor stub
	}
	
	private String getValueOrNull(String value) {
		    return (value == null || value.equals("")) ? null : value;
	}
	
	private CustomerChangeAddress convertCustomerChangeAddressDetailsFromEntityToDomain(OtsCustomerChangeAddress customerDetails) {
		CustomerChangeAddress customerDetailsList = new CustomerChangeAddress();
		customerDetailsList.setCustomerChangeAddressId((customerDetails.getOtsCustomerChangeAddressId()== null) ? null: customerDetails.getOtsCustomerChangeAddressId().toString());
		customerDetailsList.setCustomerId((customerDetails.getOtsCustomerId()== null) ? null: customerDetails.getOtsCustomerId().getOtsUsersId().toString());
		customerDetailsList.setCustomerFirstName((customerDetails.getOtsCustomerFirstName()== null) ? null: customerDetails.getOtsCustomerFirstName().toString());
		customerDetailsList.setCustomerSecondName((customerDetails.getOtsCustomerSecondName()== null) ? null: customerDetails.getOtsCustomerSecondName().toString());
		customerDetailsList.setCustomerContactNo((customerDetails.getOtsCustomerContactNo()== null) ? null: customerDetails.getOtsCustomerContactNo().toString());
		customerDetailsList.setOtsHouseNo((customerDetails.getOtsHouseNo()== null) ? null: customerDetails.getOtsHouseNo());
		customerDetailsList.setOtsBuildingName((customerDetails.getOtsBuildingName()== null) ? null: customerDetails.getOtsBuildingName());
		customerDetailsList.setOtsStreetName((customerDetails.getOtsStreetName()== null) ? null: customerDetails.getOtsStreetName());
		customerDetailsList.setOtsCityName((customerDetails.getOtsCityName()== null) ? null: customerDetails.getOtsCityName());
		customerDetailsList.setOtsPinCode((customerDetails.getOtsPincode()== null) ? null: customerDetails.getOtsPincode());
		customerDetailsList.setOtsDistrictName((customerDetails.getOtsDistrictName()== null) ? "": customerDetails.getOtsDistrictName());
		customerDetailsList.setOtsStateName((customerDetails.getOtsStateName()== null) ? "": customerDetails.getOtsStateName());
		customerDetailsList.setOtsCountryName((customerDetails.getOtsCountryName()== null) ? "": customerDetails.getOtsCountryName());
		customerDetailsList.setCustomerAccountStatus((customerDetails.getOtsCustomerAccountStatus()== null) ? null: customerDetails.getOtsCustomerAccountStatus());
		
		return customerDetailsList;
	}
	
	@Override
	public String addCustomerChangeAddress(AddCustomerChangeAddressRequest addCustomerChangeAddressRequest) {
		try {
			OtsCustomerChangeAddress customerDetails = new OtsCustomerChangeAddress();
			OtsUsers CustomerId = new OtsUsers();
			CustomerId.setOtsUsersId(UUID.fromString(addCustomerChangeAddressRequest.getRequest().getCustomerId()));
			customerDetails.setOtsCustomerId(CustomerId);
			customerDetails.setOtsCustomerFirstName(addCustomerChangeAddressRequest.getRequest().getCustomerFirstName());
			customerDetails.setOtsCustomerSecondName(addCustomerChangeAddressRequest.getRequest().getCustomerSecondName());
			customerDetails.setOtsCustomerContactNo(addCustomerChangeAddressRequest.getRequest().getCustomerContactNo());
			customerDetails.setOtsHouseNo(addCustomerChangeAddressRequest.getRequest().getOtsHouseNo());
			customerDetails.setOtsBuildingName(addCustomerChangeAddressRequest.getRequest().getOtsBuildingName());
			customerDetails.setOtsStreetName(addCustomerChangeAddressRequest.getRequest().getOtsStreetName());
			customerDetails.setOtsCityName(addCustomerChangeAddressRequest.getRequest().getOtsCityName());
			customerDetails.setOtsPincode(addCustomerChangeAddressRequest.getRequest().getOtsPinCode());
			customerDetails.setOtsCustomerAccountStatus("active");
			customerDetails.setOtsStateName(addCustomerChangeAddressRequest.getRequest().getOtsStateName());
			customerDetails.setOtsDistrictName(getValueOrNull(addCustomerChangeAddressRequest.getRequest().getOtsDistrictName()));
			customerDetails.setOtsCountryName(addCustomerChangeAddressRequest.getRequest().getOtsCountryName());

			//To generate random UUID & insert into table  
			UUID uuid=UUID.randomUUID();
			customerDetails.setOtsCustomerChangeAddressId(uuid);
			save(customerDetails);
		} catch (Exception e) {
			logger.error("Exception while inserting data in DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while inserting data in DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return "Delivery Address Added Successfully";
	}
	
	@Override
	public String updateCustomerChangeAddress(AddCustomerChangeAddressRequest addCustomerChangeAddressRequest) {
		try {
			OtsCustomerChangeAddress customerDetails = new OtsCustomerChangeAddress();
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsCustomerChangeAddressId", UUID.fromString(addCustomerChangeAddressRequest.getRequest().getCustomerChangeAddressId()));
			customerDetails = super.getResultByNamedQuery("OtsCustomerChangeAddress.findByOtsCustomerChangeAddressId", queryParameter);

			customerDetails.setOtsCustomerFirstName(addCustomerChangeAddressRequest.getRequest().getCustomerFirstName());
			customerDetails.setOtsCustomerSecondName(addCustomerChangeAddressRequest.getRequest().getCustomerSecondName());
			customerDetails.setOtsCustomerContactNo(addCustomerChangeAddressRequest.getRequest().getCustomerContactNo());
			customerDetails.setOtsHouseNo(addCustomerChangeAddressRequest.getRequest().getOtsHouseNo());
			customerDetails.setOtsBuildingName(addCustomerChangeAddressRequest.getRequest().getOtsBuildingName());
			customerDetails.setOtsStreetName(addCustomerChangeAddressRequest.getRequest().getOtsStreetName());
			customerDetails.setOtsCityName(addCustomerChangeAddressRequest.getRequest().getOtsCityName());
			customerDetails.setOtsPincode(addCustomerChangeAddressRequest.getRequest().getOtsPinCode());
			customerDetails.setOtsStateName(addCustomerChangeAddressRequest.getRequest().getOtsStateName());
			customerDetails.setOtsDistrictName(getValueOrNull(addCustomerChangeAddressRequest.getRequest().getOtsDistrictName()));
			customerDetails.setOtsCountryName(addCustomerChangeAddressRequest.getRequest().getOtsCountryName());
			save(customerDetails);
		} catch (Exception e) {
			logger.error("Exception while inserting data in DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while inserting data in DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return "Updated";
	}
	
	@Override
	public List<CustomerChangeAddress> getCustomerChangeAddressByCustomerId(String customerId) {
		List<CustomerChangeAddress> customerDetailsList = new ArrayList<CustomerChangeAddress>();
		List<OtsCustomerChangeAddress> customerDetails  = new ArrayList<OtsCustomerChangeAddress>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			OtsUsers CustomerId = new OtsUsers();
			CustomerId.setOtsUsersId(UUID.fromString(customerId));
			queryParameter.put("otsCustomerId", CustomerId);
			customerDetails = super.getResultListByNamedQuery("OtsCustomerChangeAddress.getCustomerChangeAddressByCustomerId", queryParameter);
			customerDetailsList = customerDetails.stream().map(changeAddress -> convertCustomerChangeAddressDetailsFromEntityToDomain(changeAddress)).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return customerDetailsList;
	}
	
	@Override
	public List<CustomerChangeAddress> getCustomerChangeAddressById(String customerChangeAddressId) {
		List<CustomerChangeAddress> customerDetailsList = new ArrayList<CustomerChangeAddress>();
		List<OtsCustomerChangeAddress> customerDetails  = new ArrayList<OtsCustomerChangeAddress>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsCustomerChangeAddressId", UUID.fromString(customerChangeAddressId));
			customerDetails = super.getResultListByNamedQuery("OtsCustomerChangeAddress.findByOtsCustomerChangeAddressId", queryParameter);
			customerDetailsList = customerDetails.stream().map(changeAddress -> convertCustomerChangeAddressDetailsFromEntityToDomain(changeAddress)).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return customerDetailsList;
	}
	
}
