package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.DistributorCompanyDetails;
import com.fuso.enterprise.ots.srv.api.service.request.AddDistributorCompanyDetailsRequest;

public interface DistributorCompanyDetailsDAO {

	String addDistributorCompanyDetails(AddDistributorCompanyDetailsRequest addDistributorCompanyDetailsRequest);

	String updateDistributorCompanyDetails(AddDistributorCompanyDetailsRequest addDistributorCompanyDetailsRequest);

	List<DistributorCompanyDetails> getDistributorCompanyDetails(String distributorId);

	List<DistributorCompanyDetails> getGSTUnRegisteredDistributor();

}
