
package com.fuso.enterprise.ots.srv.server.util;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseState {
	private String system;

	private String code;

	private String message;

	private List<?> params;

	private Map<String, Object> applicationSpecific;

	public String getSystem() {
		return this.system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	public List<?> getParams() {
		return this.params;
	}

	public void setParams(List<?> params) {
		this.params = params;
	}

	public Map<String, Object> getApplicationSpecific() {
		return this.applicationSpecific;
	}

	public void setApplicationSpecific(Map<String, Object> applicationSpecific) {
		this.applicationSpecific = applicationSpecific;
	}

}