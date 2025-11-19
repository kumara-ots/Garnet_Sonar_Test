package com.fuso.enterprise.ots.srv.common.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 3071306682645964866L;

	private ErrorEnumeration errorEnumeration;

	public BusinessException() {

	}

	public BusinessException(ErrorEnumeration errorEnumeration) {
		this.errorEnumeration = errorEnumeration;
	}

	public BusinessException(Throwable throwable, ErrorEnumeration errorEnumeration) {
		super(throwable);
		this.errorEnumeration = errorEnumeration;
	}

	public BusinessException(String errorMessage) {
		super(errorMessage);
	}

	public BusinessException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
	}

	public ErrorEnumeration getErrorEnumeration() {
		return errorEnumeration;
	}

	public void setErrorEnumeration(ErrorEnumeration errorEnumeration) {
		this.errorEnumeration = errorEnumeration;
	}
}
