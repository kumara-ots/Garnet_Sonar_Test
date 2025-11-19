package com.fuso.enterprise.ots.srv.server.util;

import java.io.Serializable;

public class ResponseWrapper_old implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int code;
	private String message="";
	private Object applicationSpecific;
	
	public ResponseWrapper_old() {
	}

	/**
	 * @param body
	 */
	public ResponseWrapper_old(Object applicationSpecific) 
	{
		this.applicationSpecific = applicationSpecific;
	}
	public ResponseWrapper_old(int code, Object applicationSpecific) 
	{
		this.code = code;
		this.applicationSpecific = applicationSpecific;
	}
	
	/**
	 * @param ret
	 * @param error
	 * @param body
	 */
	public ResponseWrapper_old(int code, String message, Object applicationSpecific) {
		this.code = code;
		this.message = message;
		this.applicationSpecific = applicationSpecific;
	}

	/**
	 * @param ret
	 * @param error
	 */
	public ResponseWrapper_old(int code, String error) {
		this.code = code;
		this.message = message;
	}


	

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public Object getApplicationSpecific() {
		return applicationSpecific;
	}

	

}
