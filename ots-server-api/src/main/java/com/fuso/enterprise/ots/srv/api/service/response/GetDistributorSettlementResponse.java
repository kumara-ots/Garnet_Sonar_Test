package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.OrderProductDetails;

public class GetDistributorSettlementResponse {
	private List<OrderProductDetails> orderProductDetails;
	
	private Integer totalOrderCount;
	
	private Double totalSettlementAmount;
	
	private Double totalPendingAmount;
	
//	private Double totalTenPercTrans;
//	
//	private Double totalEighteenPercGstOnTenPercTrans;
//	
//	private Double totalOnePercTdsOnProductPrice;
//	
//	private Double totalLLPPayablePrice;
//	
//	private Double totalPvtPayablePrice;
//	
//	private Double totalEighteenPercGstOnProductPrice;
//	
//	private Double totalOnePercTds;
//	
//	private Double totalThreePercProfitPvt;
	
//	private GetDetailsForExcelRequest getDetailsForExcelRequest;
	
	private String excelFile;
	
	public String getExcelFile() {
		return excelFile;
	}
	public void setExcelFile(String excelFile) {
		this.excelFile = excelFile;
	}
//	public GetDetailsForExcelRequest getGetDetailsForExcelRequest() {
//		return getDetailsForExcelRequest;
//	}
//	public void setGetDetailsForExcelRequest(GetDetailsForExcelRequest getDetailsForExcelRequest) {
//		this.getDetailsForExcelRequest = getDetailsForExcelRequest;
//	}
//	public Double getTotalThreePercProfitPvt() {
//		return totalThreePercProfitPvt;
//	}
//	public void setTotalThreePercProfitPvt(Double totalThreePercProfitPvt) {
//		this.totalThreePercProfitPvt = totalThreePercProfitPvt;
//	}
//	public Double getTotalEighteenPercGstOnProductPrice() {
//		return totalEighteenPercGstOnProductPrice;
//	}
//	public void setTotalEighteenPercGstOnProductPrice(Double totalEighteenPercGstOnProductPrice) {
//		this.totalEighteenPercGstOnProductPrice = totalEighteenPercGstOnProductPrice;
//	}
//	public Double getTotalOnePercTds() {
//		return totalOnePercTds;
//	}
//	public void setTotalOnePercTds(Double totalOnePercTds) {
//		this.totalOnePercTds = totalOnePercTds;
//	}
//	public Double getTotalLLPPayablePrice() {
//		return totalLLPPayablePrice;
//	}
//	public void setTotalLLPPayablePrice(Double totalLLPPayablePrice) {
//		this.totalLLPPayablePrice = totalLLPPayablePrice;
//	}
//	public Double getTotalPvtPayablePrice() {
//		return totalPvtPayablePrice;
//	}
//	public void setTotalPvtPayablePrice(Double totalPvtPayablePrice) {
//		this.totalPvtPayablePrice = totalPvtPayablePrice;
//	}
//	public Double getTotalTenPercTrans() {
//		return totalTenPercTrans;
//	}
//	public void setTotalTenPercTrans(Double totalTenPercTrans) {
//		this.totalTenPercTrans = totalTenPercTrans;
//	}
//	public Double getTotalEighteenPercGstOnTenPercTrans() {
//		return totalEighteenPercGstOnTenPercTrans;
//	}
//	public void setTotalEighteenPercGstOnTenPercTrans(Double totalEighteenPercGstOnTenPercTrans) {
//		this.totalEighteenPercGstOnTenPercTrans = totalEighteenPercGstOnTenPercTrans;
//	}
//	public Double getTotalOnePercTdsOnProductPrice() {
//		return totalOnePercTdsOnProductPrice;
//	}
//	public void setTotalOnePercTdsOnProductPrice(Double totalOnePercTdsOnProductPrice) {
//		this.totalOnePercTdsOnProductPrice = totalOnePercTdsOnProductPrice;
//	}
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
