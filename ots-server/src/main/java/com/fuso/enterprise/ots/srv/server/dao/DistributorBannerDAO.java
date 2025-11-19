package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.DistributorBanner;
import com.fuso.enterprise.ots.srv.api.service.request.AddDistributorBannerRequest;

public interface DistributorBannerDAO {

	List<DistributorBanner> getDistributorBanner(String distributorId);

	String addDistributorBanner(AddDistributorBannerRequest addDistributorBannerRequest);

	String updateDistributorBanner(AddDistributorBannerRequest addDistributorBannerRequest);

	DistributorBanner getBannerDetailsByBannerId(String bannerId);

}
