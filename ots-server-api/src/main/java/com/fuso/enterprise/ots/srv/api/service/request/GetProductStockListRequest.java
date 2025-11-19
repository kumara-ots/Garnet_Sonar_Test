package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.GetProductStockListDomain;

public class GetProductStockListRequest {
	
	private GetProductStockListDomain requestData;

	public GetProductStockListDomain getRequestData() {
		return requestData;
	}

	public void setRequestData(GetProductStockListDomain requestData) {
		this.requestData = requestData;
	}
	
	

}
