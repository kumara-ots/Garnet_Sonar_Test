package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.AssignOrderToEmployeeModel;

public class AssignOrderToEmployeeRequest {
	private AssignOrderToEmployeeModel request;

	public AssignOrderToEmployeeModel getRequest() {
		return request;
	}

	public void setRequest(AssignOrderToEmployeeModel request) {
		this.request = request;
	}
}
