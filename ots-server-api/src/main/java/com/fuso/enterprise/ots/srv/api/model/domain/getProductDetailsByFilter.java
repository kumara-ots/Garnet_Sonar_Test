package com.fuso.enterprise.ots.srv.api.model.domain;

public class getProductDetailsByFilter {
	private String pricemin;
	private String pricemax;
	private String discountmin;
	private String discountthreshold;
	private String ratingmin;
	private String ratingthreshold;
	private String stockqtymin;
	private String stockqtythreshold;
	public String getPricemin() {
		return pricemin;
	}
	public void setPricemin(String pricemin) {
		this.pricemin = pricemin;
	}
	public String getPricemax() {
		return pricemax;
	}
	public void setPricemax(String pricemax) {
		this.pricemax = pricemax;
	}
	public String getDiscountmin() {
		return discountmin;
	}
	public void setDiscountmin(String discountmin) {
		this.discountmin = discountmin;
	}
	public String getDiscountthreshold() {
		return discountthreshold;
	}
	public void setDiscountthreshold(String discountthreshold) {
		this.discountthreshold = discountthreshold;
	}
	public String getRatingmin() {
		return ratingmin;
	}
	public void setRatingmin(String ratingmin) {
		this.ratingmin = ratingmin;
	}
	public String getRatingthreshold() {
		return ratingthreshold;
	}
	public void setRatingthreshold(String ratingthreshold) {
		this.ratingthreshold = ratingthreshold;
	}
	public String getStockqtymin() {
		return stockqtymin;
	}
	public void setStockqtymin(String stockqtymin) {
		this.stockqtymin = stockqtymin;
	}
	public String getStockqtythreshold() {
		return stockqtythreshold;
	}
	public void setStockqtythreshold(String stockqtythreshold) {
		this.stockqtythreshold = stockqtythreshold;
	}
	

}
