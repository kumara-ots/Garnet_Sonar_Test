package com.fuso.enterprise.ots.srv.api.model.domain;

public class ProductStockDetail {
	
	String productId;
	
	String productName;
	
	private String otsprodcutStockActQty;
	
    private String  otsOrderedQty;
	
	private String  otsProductStockHistoryQty;
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String string) {
		this.productId = string;
	}
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getOtsprodcutStockActQty() {
		return otsprodcutStockActQty;
	}

	public void setOtsprodcutStockActQty(String otsprodcutStockActQty) {
		this.otsprodcutStockActQty = otsprodcutStockActQty;
	}

	public String getOtsOrderedQty() {
		return otsOrderedQty;
	}

	public void setOtsOrderedQty(String otsOrderedQty) {
		this.otsOrderedQty = otsOrderedQty;
	}

	public String getOtsProductStockHistoryQty() {
		return otsProductStockHistoryQty;
	}

	public void setOtsProductStockHistoryQty(String otsProductStockHistoryQty) {
		this.otsProductStockHistoryQty = otsProductStockHistoryQty;
	}
	
}
