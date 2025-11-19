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

import com.fuso.enterprise.ots.srv.api.model.domain.DistributorBanner;
import com.fuso.enterprise.ots.srv.api.service.request.AddDistributorBannerRequest;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.DistributorBannerDAO;

import com.fuso.enterprise.ots.srv.server.model.entity.OtsDistributorBanner;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsUsers;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class DistributorBannerDAOImpl extends AbstractIptDao<OtsDistributorBanner, String>
		implements DistributorBannerDAO {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public DistributorBannerDAOImpl() {
		super(OtsDistributorBanner.class);
		// TODO Auto-generated constructor stub
	}

	private DistributorBanner convertBannerDetailsFromEntityToDomain(OtsDistributorBanner bannerList) {
		DistributorBanner bannerDetails = new DistributorBanner();
		bannerDetails.setBannerId((bannerList.getOtsBannerId() == null) ? null : bannerList.getOtsBannerId().toString());
		bannerDetails.setBannerImage((bannerList.getOtsBannerImage() == null) ? null : bannerList.getOtsBannerImage().toString());
		bannerDetails.setBannerImageLink((bannerList.getOtsBannerImageLink() == null) ? null : bannerList.getOtsBannerImageLink().toString());
		bannerDetails.setBannerContent((bannerList.getOtsBannerContent() == null) ? null : bannerList.getOtsBannerContent().toString());
		bannerDetails.setDistributorId((bannerList.getOtsDistributorId().getOtsUsersId() == null) ? null: bannerList.getOtsDistributorId().getOtsUsersId().toString());
		return bannerDetails;
	}

	@Override
	public String addDistributorBanner(AddDistributorBannerRequest addDistributorBannerRequest) {
		try {
			OtsDistributorBanner banner = new OtsDistributorBanner();
			OtsUsers DistributorId = new OtsUsers();
			
			DistributorId.setOtsUsersId(UUID.fromString(addDistributorBannerRequest.getRequest().getDistributorId()));
			banner.setOtsDistributorId(DistributorId);
			banner.setOtsBannerImage(addDistributorBannerRequest.getRequest().getBannerImage());
			banner.setOtsBannerImageLink(addDistributorBannerRequest.getRequest().getBannerImageLink());
			banner.setOtsBannerContent(addDistributorBannerRequest.getRequest().getBannerContent());
			save(banner);
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return "Inserted";
	}

	@Override
	public List<DistributorBanner> getDistributorBanner(String distributorId) {
		List<DistributorBanner> bannerDetails = new ArrayList<DistributorBanner>();
		List<OtsDistributorBanner> bannerList = new ArrayList<OtsDistributorBanner>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			OtsUsers DistributorId = new OtsUsers();
			DistributorId.setOtsUsersId(UUID.fromString(distributorId));
			queryParameter.put("otsDistributorId", DistributorId);
			bannerList = super.getResultListByNamedQuery("OtsDistributorBanner.getDistributorBanner", queryParameter);
			bannerDetails = bannerList.stream().map(Banner -> convertBannerDetailsFromEntityToDomain(Banner)).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return bannerDetails;
	}
	
	@Override
	public String updateDistributorBanner(AddDistributorBannerRequest addDistributorBannerRequest) {
		DistributorBanner bannerDetails = new DistributorBanner();
		OtsDistributorBanner banner = new OtsDistributorBanner();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsBannerId", Integer.parseInt(addDistributorBannerRequest.getRequest().getBannerId()));
			banner = super.getResultByNamedQuery("OtsDistributorBanner.findByOtsBannerId", queryParameter);	
			banner.setOtsBannerImage(addDistributorBannerRequest.getRequest().getBannerImage());
			banner.setOtsBannerImageLink(addDistributorBannerRequest.getRequest().getBannerImageLink());
			banner.setOtsBannerContent(addDistributorBannerRequest.getRequest().getBannerContent());
			save(banner);
			bannerDetails = convertBannerDetailsFromEntityToDomain(banner);
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return "Updated";
	}
	
	@Override
	public DistributorBanner getBannerDetailsByBannerId(String bannerId) {
		DistributorBanner bannerDetails = new DistributorBanner();
		OtsDistributorBanner bannerList = new OtsDistributorBanner();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsBannerId", Integer.parseInt(bannerId));
			bannerList = super.getResultByNamedQuery("OtsDistributorBanner.findByOtsBannerId", queryParameter);
			bannerDetails = convertBannerDetailsFromEntityToDomain(bannerList);
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return bannerDetails;
	}

}
