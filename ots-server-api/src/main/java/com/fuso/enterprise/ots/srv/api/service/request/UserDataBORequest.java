package com.fuso.enterprise.ots.srv.api.service.request;

import java.util.Map;

import javax.validation.constraints.NotNull;

public class UserDataBORequest {
	
	@NotNull(message = "No input "
			+ "")
	private Map<String, String> RequestData;
	
	private Map<String, String> RequestHeader;
	

	String userId;
	
		public Map<String, String> getRequestData() {
			return RequestData;
		}
		public void setRequestData(Map<String, String> requestData) {
			this.RequestData = requestData;
		}
		public Map<String, String> getRequestHeader() {
			return RequestHeader;
		}
		public void setRequestHeader(Map<String, String> requestHeader) {
			this.RequestHeader = requestHeader;
		}
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
