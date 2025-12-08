package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.OrderProductDetails;

public class GetDistributorSettlementResponse {
	private List<OrderProductDetails> orderProductDetails;
	
	private Integer totalOrderCount;
	
	private Double totalSettlementAmount;
	
	private Double totalPendingAmount;

	private String excelFile;
	
	public String getExcelFile() {
		return excelFile;
	}
	public void setExcelFile(String excelFile) {
		this.excelFile = excelFile;
	}
	public Double getTotalPendingAmount() {
		return totalPendingAmount;
	}
	public void setTotalPendingAmount(Double totalPendingAmount) {
		this.totalPendingAmount = totalPendingAmount;
	}
	public List<OrderProductDetails> getOrderProductDetails() {
		return orderProductDetails;
	}
	public void setOrderProductDetails(List<OrderProductDetails> orderProductDetails) {
		this.orderProductDetails = orderProductDetails;
	}
	public Integer getTotalOrderCount() {
		return totalOrderCount;
	}
	public void setTotalOrderCount(Integer totalOrderCount) {
		this.totalOrderCount = totalOrderCount;
	}
	public Double getTotalSettlementAmount() {
		return totalSettlementAmount;
	}
	public void setTotalSettlementAmount(Double sum) {
		this.totalSettlementAmount = sum;
	}
	
}
