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

import com.fuso.enterprise.ots.srv.api.model.domain.DistributorPaymentDetails;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.DistributorPaymentDetailsDAO;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsDistributerPaymentDetails;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsUsers;

@Repository
public class DistributorPaymentDetailsDAOImpl extends AbstractIptDao<OtsDistributerPaymentDetails, String> implements DistributorPaymentDetailsDAO{

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public DistributorPaymentDetailsDAOImpl() {
		super(OtsDistributerPaymentDetails.class);
		// TODO Auto-generated constructor stub
	}
	
	private DistributorPaymentDetails convertDistributorPaymentDetailsFromEntityToDomain(OtsDistributerPaymentDetails detailsList) {
	    DistributorPaymentDetails paymentDetails = new DistributorPaymentDetails();

	    paymentDetails.setPaymentDetailsId((detailsList.getPaymentDetailsId() == null) ? null : detailsList.getPaymentDetailsId().toString());
	    paymentDetails.setPaymentDetailsPanNumber((detailsList.getPaymentDetailsPannumber() == null) ? "" : detailsList.getPaymentDetailsPannumber());
	    paymentDetails.setPaymentDetailsAadhaarNumber((detailsList.getPaymentDetailsAadhaarNumber() == null) ? "" : detailsList.getPaymentDetailsAadhaarNumber());
	    paymentDetails.setPaymentDetailsGst((detailsList.getPaymentDetailsGst() == null) ? null : detailsList.getPaymentDetailsGst());
	    paymentDetails.setPaymentDetailsName((detailsList.getPaymentDetailsName() == null) ? "" : detailsList.getPaymentDetailsName());
	    paymentDetails.setPaymentDetailsBillableAddress((detailsList.getPaymentDetailsBillableAddress() == null) ? null : detailsList.getPaymentDetailsBillableAddress());
	    paymentDetails.setPaymentDetailsAddress((detailsList.getPaymentDetailsAddress() == null) ? null : detailsList.getPaymentDetailsAddress());
	    paymentDetails.setPaymentDetailsEmail((detailsList.getPaymentEmail() == null) ? null : detailsList.getPaymentEmail());
	    paymentDetails.setPaymentDetailsPhoneNumber((detailsList.getPaymentDetailsPhonenumber() == null) ? "" : detailsList.getPaymentDetailsPhonenumber());
	    paymentDetails.setPaymentDetailsDescription((detailsList.getPaymentDetailsDescription() == null) ? null : detailsList.getPaymentDetailsDescription());
	    paymentDetails.setPaymentDetailsAccountNumber((detailsList.getPaymentDetailsAccountNumber() == null) ? "" : detailsList.getPaymentDetailsAccountNumber());
	    paymentDetails.setPaymentDetailsBankName((detailsList.getPaymentDetailsBankname() == null) ? "" : detailsList.getPaymentDetailsBankname());
	    paymentDetails.setPaymentDetailsIfscCode((detailsList.getPaymentDetailsIfseCode() == null) ? "" : detailsList.getPaymentDetailsIfseCode());
	    paymentDetails.setPaymentDetailsBranchName((detailsList.getPaymentDetailsBranchName() == null) ? "" : detailsList.getPaymentDetailsBranchName());
	    paymentDetails.setPaymentDetailsBankDetails((detailsList.getPaymentDetailsBankDetails() == null) ? null : detailsList.getPaymentDetailsBankDetails());
	    paymentDetails.setPaymentDetailsCODStatus((detailsList.getCodStatus() == null) ? null : detailsList.getCodStatus());
	    if (detailsList.getDistributerId() != null && detailsList.getDistributerId().getOtsUsersId() != null) {paymentDetails.setPaymentDetailsDistributorId(detailsList.getDistributerId().getOtsUsersId().toString());
	      } else {paymentDetails.setPaymentDetailsDistributorId("");}
	    if (detailsList.getAdminId() != null && detailsList.getAdminId().getCreatedUser() != null) { paymentDetails.setPaymentDetailsAdminId(detailsList.getAdminId().getCreatedUser().toString());
	    } else { paymentDetails.setPaymentDetailsAdminId("");}
        paymentDetails.setPaymentDetailsCreatedDate((detailsList.getPaymentDetailsCreatedDate() == null) ? null : detailsList.getPaymentDetailsCreatedDate().toString());
	    paymentDetails.setPaymentDetailsUpdatedDate((detailsList.getPaymentDetailsUpdatedDate() == null) ? null : detailsList.getPaymentDetailsUpdatedDate().toString());

	    return paymentDetails;
	}
	
	@Override
	public List<DistributorPaymentDetails> getDistributorPaymentDetails(String distributorId) {
		List<DistributorPaymentDetails> distributorPaymentDetails = new ArrayList<DistributorPaymentDetails>();
		List<OtsDistributerPaymentDetails> paymentDetails = new ArrayList<OtsDistributerPaymentDetails>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			OtsUsers DistributorId = new OtsUsers();
			DistributorId.setOtsUsersId(UUID.fromString(distributorId));
			queryParameter.put("distributerId", DistributorId);
			paymentDetails = super.getResultListByNamedQuery("OtsDistributerPaymentDetails.getDistributorPaymentDetails", queryParameter);
			distributorPaymentDetails = paymentDetails.stream().map(Details -> convertDistributorPaymentDetailsFromEntityToDomain(Details)).collect(Collectors.toList());
		}catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return distributorPaymentDetails;
	}
	
}
