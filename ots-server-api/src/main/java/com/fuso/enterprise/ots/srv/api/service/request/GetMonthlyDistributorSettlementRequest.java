package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.GetMonthlyDistributorSettlementRequestModel;

public class GetMonthlyDistributorSettlementRequest {
	private GetMonthlyDistributorSettlementRequestModel request;

	public GetMonthlyDistributorSettlementRequestModel getRequest() {
		return request;
	}

	public void setRequest(GetMonthlyDistributorSettlementRequestModel request) {
		this.request = request;
	}
}
