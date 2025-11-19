package com.fuso.enterprise.ots.srv.api.model.domain;

public class ServicePriceDetails {
	private String otsServiceId;
	private String otsServiceStatus;
	private String otsServiceBasePrice;
	private String otsEquipmentRentalCost;
	private String otsServiceDiscountPercentage;
	private String otsServiceDiscountPrice;
	private String otsServiceStrikenPrice;
	private String otsServiceFinalPriceWithoutGst;
	private String otsServiceGstPercentage;
	private String otsServiceGstPrice;
	private String otsServiceFinalPriceWithGst;

	public String getOtsServiceId() {
		return otsServiceId;
	}

	public void setOtsServiceId(String otsServiceId) {
		this.otsServiceId = otsServiceId;
	}

	public String getOtsServiceStatus() {
		return otsServiceStatus;
	}

	public void setOtsServiceStatus(String otsServiceStatus) {
		this.otsServiceStatus = otsServiceStatus;
	}

	public String getOtsServiceBasePrice() {
		return otsServiceBasePrice;
	}

	public void setOtsServiceBasePrice(String otsServiceBasePrice) {
		this.otsServiceBasePrice = otsServiceBasePrice;
	}

	public String getOtsServiceDiscountPercentage() {
		return otsServiceDiscountPercentage;
	}

	public void setOtsServiceDiscountPercentage(String otsServiceDiscountPercentage) {
		this.otsServiceDiscountPercentage = otsServiceDiscountPercentage;
	}

	public String getOtsServiceDiscountPrice() {
		return otsServiceDiscountPrice;
	}

	public void setOtsServiceDiscountPrice(String otsServiceDiscountPrice) {
		this.otsServiceDiscountPrice = otsServiceDiscountPrice;
	}

	public String getOtsServiceStrikenPrice() {
		return otsServiceStrikenPrice;
	}

	public void setOtsServiceStrikenPrice(String otsServiceStrikenPrice) {
		this.otsServiceStrikenPrice = otsServiceStrikenPrice;
	}

	public String getOtsServiceFinalPriceWithoutGst() {
		return otsServiceFinalPriceWithoutGst;
	}

	public void setOtsServiceFinalPriceWithoutGst(String otsServiceFinalPriceWithoutGst) {
		this.otsServiceFinalPriceWithoutGst = otsServiceFinalPriceWithoutGst;
	}

	public String getOtsServiceGstPercentage() {
		return otsServiceGstPercentage;
	}

	public void setOtsServiceGstPercentage(String otsServiceGstPercentage) {
		this.otsServiceGstPercentage = otsServiceGstPercentage;
	}

	public String getOtsServiceGstPrice() {
		return otsServiceGstPrice;
	}

	public void setOtsServiceGstPrice(String otsServiceGstPrice) {
		this.otsServiceGstPrice = otsServiceGstPrice;
	}

	public String getOtsServiceFinalPriceWithGst() {
		return otsServiceFinalPriceWithGst;
	}

	public void setOtsServiceFinalPriceWithGst(String otsServiceFinalPriceWithGst) {
		this.otsServiceFinalPriceWithGst = otsServiceFinalPriceWithGst;
	}

	public String getOtsEquipmentRentalCost() {
		return otsEquipmentRentalCost;
	}

	public void setOtsEquipmentRentalCost(String otsEquipmentRentalCost) {
		this.otsEquipmentRentalCost = otsEquipmentRentalCost;
	}

}
