package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.ProductStockDetail;

public class GetProductStockListBOResponse {
	
	List<ProductStockDetail> productStockDetail;

	private String pdf;
	
	public List<ProductStockDetail> getProductStockDetail() {
		return productStockDetail;
	}

	public void setProductStockDetail(List<ProductStockDetail> productStockDetail) {
		this.productStockDetail = productStockDetail;
	}

	public String getPdf() {
		return pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

}
