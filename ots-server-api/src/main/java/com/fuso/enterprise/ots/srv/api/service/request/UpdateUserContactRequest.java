package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.UpdateUserContactModel;

public class UpdateUserContactRequest {
	private UpdateUserContactModel Request;

	public UpdateUserContactModel getRequest() {
		return Request;
	}

	public void setRequest(UpdateUserContactModel request) {
		Request = request;
	}
}