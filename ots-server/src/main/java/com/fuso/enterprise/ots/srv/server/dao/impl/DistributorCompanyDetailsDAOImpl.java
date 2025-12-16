package com.fuso.enterprise.ots.srv.server.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fuso.enterprise.ots.srv.api.model.domain.DistributorCompanyDetails;
import com.fuso.enterprise.ots.srv.api.service.request.AddDistributorCompanyDetailsRequest;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.DistributorCompanyDetailsDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsDistributorCompanyDetails;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsUsers;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class DistributorCompanyDetailsDAOImpl extends AbstractIptDao<OtsDistributorCompanyDetails, String> implements DistributorCompanyDetailsDAO {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public DistributorCompanyDetailsDAOImpl() {
		super(OtsDistributorCompanyDetails.class);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String addDistributorCompanyDetails(AddDistributorCompanyDetailsRequest addDistributorCompanyDetailsRequest) {
		OtsDistributorCompanyDetails companyDetails = new OtsDistributorCompanyDetails();
		OtsUsers distributorId = new OtsUsers();
		try {
			distributorId.setOtsUsersId(UUID.fromString(addDistributorCompanyDetailsRequest.getRequest().getDistributorId()));
			companyDetails.setOtsDistributorId(distributorId);
			companyDetails.setOtsCompanyName(addDistributorCompanyDetailsRequest.getRequest().getCompanyName());
			companyDetails.setOtsCompanyAddress(addDistributorCompanyDetailsRequest.getRequest().getCompanyAddress());
			companyDetails.setOtsCompanyPincode(addDistributorCompanyDetailsRequest.getRequest().getCompanyPincode());
			companyDetails.setOtsCompanyContactNo(addDistributorCompanyDetailsRequest.getRequest().getCompanyContactNo());
			companyDetails.setOtsCompanyEmailid(addDistributorCompanyDetailsRequest.getRequest().getCompanyEmailId());
			companyDetails.setOtsTaxAvailability(addDistributorCompanyDetailsRequest.getRequest().getTaxAvailability());
			companyDetails.setOtsCompanyTaxNumber(addDistributorCompanyDetailsRequest.getRequest().getCompanyTaxNumber());
			companyDetails.setCompanyBusinessRegistration(addDistributorCompanyDetailsRequest.getRequest().getCompanyBusinessRegistration());
			companyDetails.setAuthorizedSignatoryProof(addDistributorCompanyDetailsRequest.getRequest().getAuthorizedSignatoryProof());
			companyDetails.setBankConfirmationOnBankAccount(addDistributorCompanyDetailsRequest.getRequest().getBankConfirmationOnBankAccount());
			companyDetails.setTaxCard(addDistributorCompanyDetailsRequest.getRequest().getTaxCard());
			save(companyDetails);
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return "Inserted";
	}
	
	@Override
	public String updateDistributorCompanyDetails(AddDistributorCompanyDetailsRequest addDistributorCompanyDetailsRequest) {
		try {
			Query query = super.getEntityManager().createNamedQuery("OtsDistributorCompanyDetails.updateDistributorCompanyDetails");
			query.setParameter("otsDistributorCompanyId", Integer.parseInt(addDistributorCompanyDetailsRequest.getRequest().getDistributorCompanyId()));
			query.setParameter("otsCompanyName", addDistributorCompanyDetailsRequest.getRequest().getCompanyName());
			query.setParameter("otsCompanyAddress", addDistributorCompanyDetailsRequest.getRequest().getCompanyAddress());
			query.setParameter("otsCompanyPincode", addDistributorCompanyDetailsRequest.getRequest().getCompanyPincode());
			query.setParameter("otsCompanyContactNo", addDistributorCompanyDetailsRequest.getRequest().getCompanyContactNo());
			query.setParameter("otsCompanyEmailid", addDistributorCompanyDetailsRequest.getRequest().getCompanyEmailId());
			query.setParameter("otsTaxAvailability", addDistributorCompanyDetailsRequest.getRequest().getTaxAvailability());
			query.setParameter("otsCompanyTaxNumber", addDistributorCompanyDetailsRequest.getRequest().getCompanyTaxNumber());
			query.setParameter("companyBusinessRegistration", addDistributorCompanyDetailsRequest.getRequest().getCompanyBusinessRegistration());
			query.setParameter("authorizedSignatoryProof", addDistributorCompanyDetailsRequest.getRequest().getAuthorizedSignatoryProof());
			query.setParameter("bankConfirmationOnBankAccount", addDistributorCompanyDetailsRequest.getRequest().getBankConfirmationOnBankAccount());
			query.setParameter("taxCard", addDistributorCompanyDetailsRequest.getRequest().getTaxCard());

			int updatedCount = query.executeUpdate();

			if (updatedCount > 0) {
				return "Company Details Updated Successfully";
			} else {
				return "Company Details Not Updated";
			}
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	private DistributorCompanyDetails convertDistributorCompanyDetailsFromEntityToDomain(OtsDistributorCompanyDetails detailsList) {
		DistributorCompanyDetails companyDetails = new DistributorCompanyDetails();
		companyDetails.setDistributorCompanyId((detailsList.getOtsDistributorCompanyId() == null) ? "": detailsList.getOtsDistributorCompanyId().toString());
		companyDetails.setDistributorId((detailsList.getOtsDistributorId().getOtsUsersId() == null) ? "" : detailsList.getOtsDistributorId().getOtsUsersId().toString());
		companyDetails.setCompanyName((detailsList.getOtsCompanyName() == null) ? "" : detailsList.getOtsCompanyName());
		companyDetails.setCompanyAddress((detailsList.getOtsCompanyAddress() == null) ? "" : detailsList.getOtsCompanyAddress());
		companyDetails.setCompanyPincode((detailsList.getOtsCompanyPincode() == null) ? "" : detailsList.getOtsCompanyPincode());
		companyDetails.setCompanyContactNo((detailsList.getOtsCompanyContactNo() == null) ? "" : detailsList.getOtsCompanyContactNo());
		companyDetails.setCompanyEmailId((detailsList.getOtsCompanyEmailid() == null) ? "" : detailsList.getOtsCompanyEmailid());
		companyDetails.setTaxAvailability((detailsList.getOtsTaxAvailability() == null) ? null: detailsList.getOtsTaxAvailability());
		companyDetails.setCompanyTaxNumber((detailsList.getOtsCompanyTaxNumber() == null) ? "": detailsList.getOtsCompanyTaxNumber());
		companyDetails.setDistributorFirstName((detailsList.getOtsDistributorId().getOtsUsersFirstname() == null) ? "" : detailsList.getOtsDistributorId().getOtsUsersFirstname());
		companyDetails.setDistributorLastName((detailsList.getOtsDistributorId().getOtsUsersLastname() == null) ? "" : detailsList.getOtsDistributorId().getOtsUsersLastname());
		companyDetails.setCompanyBusinessRegistration((detailsList.getCompanyBusinessRegistration() == null) ? "": detailsList.getCompanyBusinessRegistration());
		companyDetails.setAuthorizedSignatoryProof((detailsList.getAuthorizedSignatoryProof() == null) ? "": detailsList.getAuthorizedSignatoryProof());
		companyDetails.setBankConfirmationOnBankAccount((detailsList.getBankConfirmationOnBankAccount() == null) ? "": detailsList.getBankConfirmationOnBankAccount());
		companyDetails.setTaxCard((detailsList.getTaxCard() == null) ? "": detailsList.getTaxCard());
		return companyDetails;
	}
	
	@Override
	public List<DistributorCompanyDetails> getDistributorCompanyDetails(String distributorId) {
		List<DistributorCompanyDetails> companyDetails = new ArrayList<DistributorCompanyDetails>();
		List<OtsDistributorCompanyDetails> detailsList = new ArrayList<OtsDistributorCompanyDetails>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			OtsUsers DistributorId = new OtsUsers();
			DistributorId.setOtsUsersId(UUID.fromString(distributorId));
			queryParameter.put("otsDistributorId", DistributorId);
			detailsList = super.getResultListByNamedQuery("OtsDistributorCompanyDetails.getDistributorCompanyDetails", queryParameter);
			companyDetails = detailsList.stream().map(this::convertDistributorCompanyDetailsFromEntityToDomain).collect(Collectors.toList());

		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return companyDetails;
	}
	
	@Override
	public List<DistributorCompanyDetails> getGSTUnRegisteredDistributor() {
		List<DistributorCompanyDetails> companyDetails = new ArrayList<DistributorCompanyDetails>();
		List<OtsDistributorCompanyDetails> detailsList = new ArrayList<OtsDistributorCompanyDetails>();
		boolean gstAvailability = false;
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsGstAvailability", gstAvailability);
			detailsList = super.getResultListByNamedQuery("OtsDistributorCompanyDetails.getGSTUnRegisteredDistributor", queryParameter);
			companyDetails = detailsList.stream().map(this::convertDistributorCompanyDetailsFromEntityToDomain).collect(Collectors.toList());
		
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return companyDetails;
	}

}
