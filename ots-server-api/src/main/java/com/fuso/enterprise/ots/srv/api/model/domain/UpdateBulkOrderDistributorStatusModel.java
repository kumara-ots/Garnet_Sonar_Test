package com.fuso.enterprise.ots.srv.api.model.domain;

public class UpdateBulkOrderDistributorStatusModel {
	private String BulkOrderId;
	private String DistributorId;
	private String ProductId;
	private String BulkOrderDistStatus;
	private String BulkOrderFulFillqty;
	
	public String getBulkOrderId() {
		return BulkOrderId;
	}
	public void setBulkOrderId(String bulkOrderId) {
		BulkOrderId = bulkOrderId;
	}
	public String getDistributorId() {
		return DistributorId;
	}
	public void setDistributorId(String distributorId) {
		DistributorId = distributorId;
	}
	public String getProductId() {
		return ProductId;
	}
	public void setProductId(String productId) {
		ProductId = productId;
	}
	public String getBulkOrderDistStatus() {
		return BulkOrderDistStatus;
	}
	public void setBulkOrderDistStatus(String bulkOrderDistStatus) {
		BulkOrderDistStatus = bulkOrderDistStatus;
	}
	public String getBulkOrderFulFillqty() {
		return BulkOrderFulFillqty;
	}
	public void setBulkOrderFulFillqty(String bulkOrderFulFillqty) {
		BulkOrderFulFillqty = bulkOrderFulFillqty;
	}
	
}
