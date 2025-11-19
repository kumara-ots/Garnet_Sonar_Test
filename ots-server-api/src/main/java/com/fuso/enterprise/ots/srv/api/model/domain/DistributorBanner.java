package com.fuso.enterprise.ots.srv.api.model.domain;

public class DistributorBanner {
	private String BannerId;
	private String BannerImage;
	private String BannerImageLink;
	private String BannerContent;
	private String DistributorId;

	public String getBannerId() {
		return BannerId;
	}
	public void setBannerId(String bannerId) {
		BannerId = bannerId;
	}
	public String getBannerImage() {
		return BannerImage;
	}
	public void setBannerImage(String bannerImage) {
		BannerImage = bannerImage;
	}
	public String getBannerImageLink() {
		return BannerImageLink;
	}
	public void setBannerImageLink(String bannerImageLink) {
		BannerImageLink = bannerImageLink;
	}
	public String getBannerContent() {
		return BannerContent;
	}
	public void setBannerContent(String bannerContent) {
		BannerContent = bannerContent;
	}
	public String getDistributorId() {
		return DistributorId;
	}
	public void setDistributorId(String distributorId) {
		DistributorId = distributorId;
	}
	
}
