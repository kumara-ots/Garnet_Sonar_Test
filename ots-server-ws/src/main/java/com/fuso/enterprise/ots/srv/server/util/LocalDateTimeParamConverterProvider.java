package com.fuso.enterprise.ots.srv.server.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Service;

import com.fuso.enterprise.ots.srv.common.exception.ErrorEnumeration;

@Provider
@Service
public class LocalDateTimeParamConverterProvider implements ParamConverterProvider {

	@SuppressWarnings("unchecked")
	@Override
	public <T> ParamConverter<T> getConverter(final Class<T> rawType, final Type genericType,
			final Annotation[] annotations) {
		if (rawType.equals(LocalDateTime.class)) {
			return new LocalDateTimeParamConverter();
		} else {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	private static class LocalDateTimeParamConverter implements ParamConverter {

		@Override
		public LocalDateTime fromString(String dateString) {
			try {
				return LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME);
			} catch (DateTimeParseException e) {
				throw new com.fuso.enterprise.ots.srv.common.exception.BusinessException(e,
						ErrorEnumeration.INPUT_PARAMETER_INCORRECT);
			}
		}

		@Override
		public String toString(Object value) {
			return value.toString();
		}
	}
}
