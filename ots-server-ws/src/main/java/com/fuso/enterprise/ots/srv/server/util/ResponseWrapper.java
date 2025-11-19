package com.fuso.enterprise.ots.srv.server.util;

import java.io.Serializable;
import java.util.Map;

import javax.ws.rs.core.Response;

public class ResponseWrapper implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int responseCode;
	private String responseDescription="";
	private Object responseData;
	private Map<String, Object> applicationSpecific;
	
	public ResponseWrapper() {
	}

	/**
	 * @param body
	 */
	public ResponseWrapper(Object responseData) 
	{
		this.responseData = responseData;
	}
	public ResponseWrapper(int responseCode, Object responseData) 
	{
		this.responseCode = responseCode;
		this.responseData = responseData;
	}
	
	/**
	 * @param responseCode
	 * @param responseDescription
	 * @param responseData
	 */
	public ResponseWrapper(int responseCode, String responseDescription, Object responseData) {
		this.responseCode = responseCode;
		this.responseDescription = responseDescription;
		this.responseData = responseData;
	}

	/**
	 * @param responseCode
	 * @param responseDescription
	 */
	public ResponseWrapper(int responseCode, String responseDescription) {
		this.responseCode = responseCode;
		this.responseDescription = responseDescription;
	}
	
	
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	public void setResponseData(Object responseData) {
		this.responseData = responseData;
	}

	public void setApplicationSpecific(Map<String, Object> applicationSpecific) {
		this.applicationSpecific = applicationSpecific;
	}
	
	public int getResponseCode() {
		return responseCode;
	}

	public String getResponseDescription() {
		return responseDescription;
	}

	public Object getResponseData() {
		return responseData;
	}
	
	public Map<String, Object> getApplicationSpecific() {
		return this.applicationSpecific;
	}

	static public Response buildResponse(Object data) {
		ResponseWrapper wrapper = new ResponseWrapper(200, data);
		return Response.ok(wrapper).build();
	}
	
	static public Response buildResponse(Object data,String responseDescription) {
		ResponseWrapper wrapper = new ResponseWrapper(200,responseDescription, data);
		return Response.ok(wrapper).build();
	}
	
	static public Response buildResponse(int responseCode,String responseDescription) {
		ResponseWrapper wrapper = new ResponseWrapper(responseCode,responseDescription);
		return Response.ok(wrapper).build();
	}
	static public Response buildResponse(int responseCode,Object data,String responseDescription) {
		ResponseWrapper wrapper = new ResponseWrapper(responseCode,responseDescription, data);
		return Response.ok(wrapper).build();
	}
	
}
