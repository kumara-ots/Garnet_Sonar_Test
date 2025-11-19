package com.fuso.enterprise.ots.srv.api.service.request;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.RegisteredDistWeeklySettlementDetailsForExcel;
import com.fuso.enterprise.ots.srv.api.model.domain.UnRegisteredDistWeeklySettlementDetailsForExcel;

public class GetDetailsForExcelRequest {
	private List<UnRegisteredDistWeeklySettlementDetailsForExcel> unregisteredDist;
	
	private List<RegisteredDistWeeklySettlementDetailsForExcel> registeredDist;
	
	private Double totalPendingAmount;
	
	private Double totalTenPercTrans;
	
	private Double totalEighteenPercGstOnTenPercTrans;
	
	private Double totalOnePercTdsOnProductPrice;
	
	private Double totalLLPPayablePrice;
	
	private Double totalPvtPayablePrice;
	
	private Double totalEighteenPercGstOnProductPrice;
	
	private Double totalOnePercTds;
	
	private Double totalThreePercProfitPvt;

	public Double getTotalPendingAmount() {
		return totalPendingAmount;
	}

	public void setTotalPendingAmount(Double totalPendingAmount) {
		this.totalPendingAmount = totalPendingAmount;
	}

	public Double getTotalTenPercTrans() {
		return totalTenPercTrans;
	}

	public void setTotalTenPercTrans(Double totalTenPercTrans) {
		this.totalTenPercTrans = totalTenPercTrans;
	}

	public Double getTotalEighteenPercGstOnTenPercTrans() {
		return totalEighteenPercGstOnTenPercTrans;
	}

	public void setTotalEighteenPercGstOnTenPercTrans(Double totalEighteenPercGstOnTenPercTrans) {
		this.totalEighteenPercGstOnTenPercTrans = totalEighteenPercGstOnTenPercTrans;
	}

	public Double getTotalOnePercTdsOnProductPrice() {
		return totalOnePercTdsOnProductPrice;
	}

	public void setTotalOnePercTdsOnProductPrice(Double totalOnePercTdsOnProductPrice) {
		this.totalOnePercTdsOnProductPrice = totalOnePercTdsOnProductPrice;
	}

	public Double getTotalLLPPayablePrice() {
		return totalLLPPayablePrice;
	}

	public void setTotalLLPPayablePrice(Double totalLLPPayablePrice) {
		this.totalLLPPayablePrice = totalLLPPayablePrice;
	}

	public Double getTotalPvtPayablePrice() {
		return totalPvtPayablePrice;
	}

	public void setTotalPvtPayablePrice(Double totalPvtPayablePrice) {
		this.totalPvtPayablePrice = totalPvtPayablePrice;
	}

	public Double getTotalEighteenPercGstOnProductPrice() {
		return totalEighteenPercGstOnProductPrice;
	}

	public void setTotalEighteenPercGstOnProductPrice(Double totalEighteenPercGstOnProductPrice) {
		this.totalEighteenPercGstOnProductPrice = totalEighteenPercGstOnProductPrice;
	}

	public Double getTotalOnePercTds() {
		return totalOnePercTds;
	}

	public void setTotalOnePercTds(Double totalOnePercTds) {
		this.totalOnePercTds = totalOnePercTds;
	}

	public Double getTotalThreePercProfitPvt() {
		return totalThreePercProfitPvt;
	}

	public void setTotalThreePercProfitPvt(Double totalThreePercProfitPvt) {
		this.totalThreePercProfitPvt = totalThreePercProfitPvt;
	}

	public List<UnRegisteredDistWeeklySettlementDetailsForExcel> getUnregisteredDist() {
		return unregisteredDist;
	}

	public void setUnregisteredDist(List<UnRegisteredDistWeeklySettlementDetailsForExcel> unregisteredDist) {
		this.unregisteredDist = unregisteredDist;
	}

	public List<RegisteredDistWeeklySettlementDetailsForExcel> getRegisteredDist() {
		return registeredDist;
	}

	public void setRegisteredDist(List<RegisteredDistWeeklySettlementDetailsForExcel> registeredDist) {
		this.registeredDist = registeredDist;
	}


}
