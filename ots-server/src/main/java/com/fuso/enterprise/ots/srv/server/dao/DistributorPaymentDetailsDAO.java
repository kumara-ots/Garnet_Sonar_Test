package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.DistributorPaymentDetails;

public interface DistributorPaymentDetailsDAO {

	List<DistributorPaymentDetails> getDistributorPaymentDetails(String distributorId);

}
