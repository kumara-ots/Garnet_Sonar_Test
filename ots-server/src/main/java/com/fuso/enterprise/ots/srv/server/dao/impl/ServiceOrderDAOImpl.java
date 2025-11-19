package com.fuso.enterprise.ots.srv.server.dao.impl;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fuso.enterprise.ots.srv.api.model.domain.ServiceOrder;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.ServiceOrderDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsServiceOrder;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsUsers;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class ServiceOrderDAOImpl extends AbstractIptDao <OtsServiceOrder, String> implements ServiceOrderDAO {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${ots.order.number.format}")
	public String orderNoFormat;
	
	public ServiceOrderDAOImpl() {
		super(OtsServiceOrder.class);
		// TODO Auto-generated constructor stub
	}
	
	private ServiceOrder convertServiceOrderFromEntityToDomain(OtsServiceOrder otsServiceOrder) throws IOException {
		ServiceOrder orderDetails =  new ServiceOrder();
		orderDetails.setOtsServiceOrderId(otsServiceOrder.getOtsServiceOrderId() == null ? "" : otsServiceOrder.getOtsServiceOrderId().toString());
		orderDetails.setOtsServiceCustomerId(otsServiceOrder.getOtsServiceCustomerId().getOtsUsersId() == null ? "" : otsServiceOrder.getOtsServiceCustomerId().getOtsUsersId().toString());
		orderDetails.setOtsServiceOrderNumber(otsServiceOrder.getOtsServiceOrderNumber() == null ? "" : otsServiceOrder.getOtsServiceOrderNumber());
		orderDetails.setOtsServiceCustomerName(otsServiceOrder.getOtsServiceCustomerName() == null ? "" : otsServiceOrder.getOtsServiceCustomerName());
		orderDetails.setOtsServiceCustomerContactNumber(otsServiceOrder.getOtsServiceCustomerContactNo() == null ? "" :otsServiceOrder.getOtsServiceCustomerContactNo());
		orderDetails.setOtsServiceCustomerHouseNo(otsServiceOrder.getOtsServiceCustomerHouseNo() == null ? "" : otsServiceOrder.getOtsServiceCustomerHouseNo());
		orderDetails.setOtsServiceCustomerBuildingName(otsServiceOrder.getOtsServiceCustomerBuildingName() == null ? "" :otsServiceOrder.getOtsServiceCustomerBuildingName());
		orderDetails.setOtsServiceCustomerStreetName(otsServiceOrder.getOtsServiceCustomerStreet() == null ? "" : otsServiceOrder.getOtsServiceCustomerStreet());
		orderDetails.setOtsServiceCustomerCityName(otsServiceOrder.getOtsServiceCustomerCity() == null ? "" : otsServiceOrder.getOtsServiceCustomerCity());
		orderDetails.setOtsServiceCustomerPincode(otsServiceOrder.getOtsServiceCompanyPincode() == null ? "" :otsServiceOrder.getOtsServiceCompanyPincode() );
		orderDetails.setOtsServiceCustomerDistrict(otsServiceOrder.getOtsServiceCustomerDistrict() == null ? "" : otsServiceOrder.getOtsServiceCustomerDistrict());
		orderDetails.setOtsServiceCustomerState(otsServiceOrder.getOtsServiceCustomerState() == null ? "": otsServiceOrder.getOtsServiceCustomerState());
		orderDetails.setOtsServiceCustomerChangeAddressId(otsServiceOrder.getOtsServiceCustomerChangeAddressId() == null ? "" : otsServiceOrder.getOtsServiceCustomerChangeAddressId());
		orderDetails.setOtsServiceImage(otsServiceOrder.getOtsServiceImage() == null ? "": otsServiceOrder.getOtsServiceImage());
		orderDetails.setOtsServiceCompanyEmail(otsServiceOrder.getOtsServiceCompanyEmail() == null ? "": otsServiceOrder.getOtsServiceCompanyEmail());
		orderDetails.setOtsServiceCompanyName(otsServiceOrder.getOtsServiceCompanyName() == null ? "": otsServiceOrder.getOtsServiceCompanyName());
		orderDetails.setOtsServiceCompanyAddress(otsServiceOrder.getOtsServiceCompanyAddress() == null ? "": otsServiceOrder.getOtsServiceCompanyAddress());
		orderDetails.setOtsServiceCompanyDistrict(otsServiceOrder.getOtsServiceCompanyDistrict() == null ? "": otsServiceOrder.getOtsServiceCompanyDistrict());
		orderDetails.setOtsServiceCompanyState(otsServiceOrder.getOtsServiceCompanyState() == null ? "": otsServiceOrder.getOtsServiceCompanyState());
		orderDetails.setOtsServiceCompanyPincode(otsServiceOrder.getOtsServiceCompanyPincode() == null ? "": otsServiceOrder.getOtsServiceCompanyPincode());
		orderDetails.setOtsServiceCompanyContactNo(otsServiceOrder.getOtsServiceCompanyContactNo() == null ? "": otsServiceOrder.getOtsServiceCompanyContactNo());
		orderDetails.setOtsServiceName(otsServiceOrder.getOtsServiceName() == null ? "": otsServiceOrder.getOtsServiceName());
		orderDetails.setOtsServiceDescription(otsServiceOrder.getOtsServiceDescription() == null ? "": otsServiceOrder.getOtsServiceDescription());
		orderDetails.setOtsServiceDescriptionLong(otsServiceOrder.getOtsServiceDescriptionLong() == null ? "": otsServiceOrder.getOtsServiceDescriptionLong());
		orderDetails.setOtsServiceOrderCustomerInvoice(otsServiceOrder.getOtsServiceOrderCustomerInvoice() == null ? "": otsServiceOrder.getOtsServiceOrderCustomerInvoice());
		orderDetails.setCustomerName(otsServiceOrder.getOtsServiceCustomerId().getOtsUsersId() == null ? "": otsServiceOrder.getOtsServiceCustomerId().getOtsUsersFirstname()
				+" "+otsServiceOrder.getOtsServiceCustomerId().getOtsUsersLastname());
		orderDetails.setCustomerContactNo(otsServiceOrder.getOtsServiceCustomerId().getOtsUsersId() == null ? "": otsServiceOrder.getOtsServiceCustomerId().getOtsUsersContactNo());
		orderDetails.setCustomerEmailId(otsServiceOrder.getOtsServiceCustomerId().getOtsUsersId() == null ? "": otsServiceOrder.getOtsServiceCustomerId().getOtsUsersEmailid());

        return orderDetails;
	}
	
	@Override
	@Transactional
	public ServiceOrder insertServiceOrder(ServiceOrder serviceOrder) {
	    try {
	        OtsServiceOrder otsServiceOrder = new OtsServiceOrder();

	        // ✅ Generate UUID & set as primary key
	        UUID uuid = UUID.randomUUID();
	        otsServiceOrder.setOtsServiceOrderId(uuid);

	        // ✅ Generate 10-digit random order number
//	        Random objGenerator = new Random();
	        SecureRandom secureRandom = new SecureRandom();
	        long randomNumber = 1000000000L + secureRandom.nextInt(900000000);
	        System.out.println("Random No : " + randomNumber);

	        String OrderNumber = orderNoFormat + randomNumber;
	        otsServiceOrder.setOtsServiceOrderNumber(OrderNumber);
	        
	        System.out.println("customerId = "+serviceOrder.getOtsServiceCustomerId());
	        // ✅ Set customer reference
	        OtsUsers customerId = new OtsUsers();
	        customerId.setOtsUsersId(UUID.fromString(serviceOrder.getOtsServiceCustomerId()));
	        otsServiceOrder.setOtsServiceCustomerId(customerId);

	        otsServiceOrder.setOtsServiceCustomerName(serviceOrder.getOtsServiceCustomerName() == null ? null : serviceOrder.getOtsServiceCustomerName());
	        otsServiceOrder.setOtsServiceCustomerContactNo(serviceOrder.getOtsServiceCustomerContactNumber() == null ? null : serviceOrder.getOtsServiceCustomerContactNumber());
	        otsServiceOrder.setOtsServiceCustomerHouseNo(serviceOrder.getOtsServiceCustomerHouseNo() == null ? null : serviceOrder.getOtsServiceCustomerHouseNo());
	        otsServiceOrder.setOtsServiceCustomerBuildingName(serviceOrder.getOtsServiceCustomerBuildingName() == null ? null : serviceOrder.getOtsServiceCustomerBuildingName());
	        otsServiceOrder.setOtsServiceCustomerStreet(serviceOrder.getOtsServiceCustomerStreetName() == null ? null : serviceOrder.getOtsServiceCustomerStreetName());
	        otsServiceOrder.setOtsServiceCustomerCity(serviceOrder.getOtsServiceCustomerCityName() == null ? null : serviceOrder.getOtsServiceCustomerCityName());
	        otsServiceOrder.setOtsServiceCustomerPincode(serviceOrder.getOtsServiceCustomerPincode() == null ? null : serviceOrder.getOtsServiceCustomerPincode());
	        otsServiceOrder.setOtsServiceCustomerDistrict(serviceOrder.getOtsServiceCustomerDistrict() == null ? null : serviceOrder.getOtsServiceCustomerDistrict());
	        otsServiceOrder.setOtsServiceCustomerState(serviceOrder.getOtsServiceCustomerState() == null ? null : serviceOrder.getOtsServiceCustomerState());
	        otsServiceOrder.setOtsServiceCustomerChangeAddressId(serviceOrder.getOtsServiceCustomerChangeAddressId() == null ? null : serviceOrder.getOtsServiceCustomerChangeAddressId());
	        otsServiceOrder.setOtsServiceImage(serviceOrder.getOtsServiceImage() == null ? null : serviceOrder.getOtsServiceImage());
	        otsServiceOrder.setOtsServiceCompanyEmail(serviceOrder.getOtsServiceCompanyEmail() == null ? null : serviceOrder.getOtsServiceCompanyEmail().toString());
	        otsServiceOrder.setOtsServiceCompanyName(serviceOrder.getOtsServiceCompanyName() == null ? null : serviceOrder.getOtsServiceCompanyName());
	        otsServiceOrder.setOtsServiceCompanyAddress(serviceOrder.getOtsServiceCompanyAddress() == null ? null : serviceOrder.getOtsServiceCompanyAddress());
	        otsServiceOrder.setOtsServiceCompanyDistrict(serviceOrder.getOtsServiceCompanyDistrict() == null ? null : serviceOrder.getOtsServiceCompanyDistrict());
	        otsServiceOrder.setOtsServiceCompanyState(serviceOrder.getOtsServiceCompanyState() == null ? null : serviceOrder.getOtsServiceCompanyState());
	        otsServiceOrder.setOtsServiceCompanyPincode(serviceOrder.getOtsServiceCompanyPincode() == null ? null : serviceOrder.getOtsServiceCompanyPincode());
	        otsServiceOrder.setOtsServiceCompanyContactNo(serviceOrder.getOtsServiceCompanyContactNo() == null ? null : serviceOrder.getOtsServiceCompanyContactNo());
	        otsServiceOrder.setOtsServiceName(serviceOrder.getOtsServiceName() == null ? null : serviceOrder.getOtsServiceName());
	        otsServiceOrder.setOtsServiceDescription(serviceOrder.getOtsServiceDescription() == null ? null : serviceOrder.getOtsServiceDescription());
	        otsServiceOrder.setOtsServiceDescriptionLong(serviceOrder.getOtsServiceDescriptionLong() == null ? null : serviceOrder.getOtsServiceDescriptionLong());
	        otsServiceOrder.setOtsServiceOrderCustomerInvoice(serviceOrder.getOtsServiceOrderCustomerInvoice() == null ? null : serviceOrder.getOtsServiceOrderCustomerInvoice());
	     
	        // ✅ Save the order
	        save(otsServiceOrder);

	        serviceOrder = convertServiceOrderFromEntityToDomain(otsServiceOrder);

	        return serviceOrder;
	    }catch(Exception e){
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	

	@Override
	public ServiceOrder getServiceOrderByOrderId(String serviceOrderId) {
		OtsServiceOrder otsServiceOrder = new OtsServiceOrder();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsServiceOrderId",UUID.fromString(serviceOrderId));
			try {
				otsServiceOrder = super.getResultByNamedQuery("OtsServiceOrder.findByOtsServiceOrderId", queryParameter);
			}catch(NoResultException e)
			{
				return null;
			}
			ServiceOrder serviceOrder = convertServiceOrderFromEntityToDomain(otsServiceOrder);
			return serviceOrder;
		}catch(Exception e){
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}

	}

}
