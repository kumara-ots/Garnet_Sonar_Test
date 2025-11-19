package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.ServiceDistrict;

public interface ServiceDistrictDAO {

	List<ServiceDistrict> getDistrictByStateId(String stateId);

	List<ServiceDistrict> getAllDistricts();

}
