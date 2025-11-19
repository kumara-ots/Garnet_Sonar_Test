package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.ServicePincode;
import com.fuso.enterprise.ots.srv.api.service.response.GetServiceableLocationResponse;

public interface ServicePincodeDAO {

	List<ServicePincode> getPincodeByDistrict(String districtId);

	List<ServicePincode> getPincodeDetails(String pincode);

}
