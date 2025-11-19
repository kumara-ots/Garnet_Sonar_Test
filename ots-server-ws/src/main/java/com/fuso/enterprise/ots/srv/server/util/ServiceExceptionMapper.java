package com.fuso.enterprise.ots.srv.server.util;

import java.io.EOFException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.common.exception.ErrorEnumeration;

@Provider
@Service
public class ServiceExceptionMapper implements ExceptionMapper<Exception>, MessageSourceAware {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private MessageSource messageSource;

	private static final String DELIMETER_COMMA = ", ";

	private static final String DELIMETER_HYPHEN = "- ";

	private static final int INTEGER_ONE = 1;

	private static final int INTEGER_TWO = 2;

	private static final String ENUM = "No enum constant";

	private static final String UNPARSABLE = "Unparseable";

	private static final String RESOLVED_MESSAGE = "resolvedMessage";

	@Required
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public Response toResponse(Exception exception) {
		Map<String, Object> appSpecific = new HashMap<>();
		Object[] args = null;
		ResponseState responseState = new ResponseState();
		ResponseWrapper responseWrapper = new ResponseWrapper();
		logger.info("Handling exception", exception);
		Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
		ErrorEnumeration errorEnumeration = ErrorEnumeration.SYSTEM_ERROR;
		if (exception instanceof ValidationException) {
			errorEnumeration = ErrorEnumeration.INPUT_PARAMETER_INCORRECT;
			Set<ConstraintViolation<?>> cvs = ((ConstraintViolationException) exception).getConstraintViolations();
			String cnstrntViolationPath = cvs.iterator().next().getPropertyPath().toString();
			String cnstrntViolationMessage = cvs.iterator().next().getMessage();
			String message = JSRErrorEnumaration.fromString(
					cvs.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().getSimpleName())
					+ DELIMETER_COMMA + cnstrntViolationPath + DELIMETER_HYPHEN + cnstrntViolationMessage;
			appSpecific.put(RESOLVED_MESSAGE, message);
			status = Response.Status.BAD_REQUEST;
		} else if (exception instanceof WebApplicationException || exception instanceof EOFException
				|| exception instanceof JsonProcessingException) {
			errorEnumeration = ErrorEnumeration.INPUT_PARAMETER_INCORRECT;
			if (exception instanceof JsonProcessingException) {
				appSpecific.put(RESOLVED_MESSAGE, prepareResolvedMessages(exception.getMessage()));
			} else {
				appSpecific.put(RESOLVED_MESSAGE, exception.getMessage());
			}
			// Handles exception of invalid enum
			if ((null != exception.getCause()) && (exception.getCause().toString().contains(ENUM)
					|| exception.getCause().toString().contains(UNPARSABLE))) {
				appSpecific.put(RESOLVED_MESSAGE, prepareResolvedMessage(exception.getCause().toString()));
			}
			status = Response.Status.BAD_REQUEST;
		} else if (exception instanceof BusinessException) {
			BusinessException businessException = (BusinessException) exception;
			errorEnumeration = businessException.getErrorEnumeration();
			if(null == errorEnumeration) {
				errorEnumeration  = ErrorEnumeration.INPUT_PARAMETER_INCORRECT;
			}
			status = Response.Status.BAD_REQUEST;
			if (exception.getMessage() != null && exception.getMessage().contains(":")) {
				appSpecific.put(RESOLVED_MESSAGE, prepareResolvedMessage(exception.getMessage()));
			}
		}
		responseState.setApplicationSpecific(appSpecific);
		responseState.setCode(Integer.toString(errorEnumeration.getErrorCode()));
		responseState.setMessage(resolveErrorMessage(errorEnumeration, args));
		
		responseWrapper.setResponseCode(errorEnumeration.getErrorCode());
		responseWrapper.setResponseDescription(resolveErrorMessage(errorEnumeration, args));
		responseWrapper.setApplicationSpecific(appSpecific);
		return Response.status(status).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
				.entity(responseWrapper).build();
	}

	private String resolveErrorMessage(ErrorEnumeration errorEnumeration, Object[] args) {
		return messageSource.getMessage(errorEnumeration.getMessageKey(), args, Locale.getDefault());
	}

	private String prepareResolvedMessage(String value) {
		String message = null;
		if (value != null) {
			String[] spiltedArray = value.split(":", INTEGER_TWO);
			message = spiltedArray[INTEGER_ONE].trim();
		}
		return message;
	}

	private String prepareResolvedMessages(final String value) {
		String message = null;
		if (null != value) {
			int splitLength = value.lastIndexOf("->");
			if (splitLength != 0) {
				String[] spiltedArray = value.split("->", splitLength);
				String splitMessage = spiltedArray[0].trim().split("\"")[0] + value.split("\"")[INTEGER_ONE] + " ";
				int size = spiltedArray.length - INTEGER_ONE;
				message = splitMessage.concat(spiltedArray[size].trim());
			} else {
				message = value;
			}
		}
		return message;
	}

}
