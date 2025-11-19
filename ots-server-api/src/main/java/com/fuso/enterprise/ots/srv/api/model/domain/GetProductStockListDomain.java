package com.fuso.enterprise.ots.srv.api.model.domain;

import java.sql.Date;

public class GetProductStockListDomain {
	
	private String userId;
	
	private String productId;
	
	private Date todaysDate;
	
	private String pdf;
	
	public Date getTodaysDate() {
		return todaysDate;
	}

	public void setTodaysDate(Date todaysDate) {
		this.todaysDate = todaysDate;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPdf() {
		return pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}
	

}
