
package com.fuso.enterprise.ots.srv.server.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Service;

import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.common.exception.ErrorEnumeration;

@Provider
@Service
public class LocalDateParamConverterProvider implements ParamConverterProvider {

    @SuppressWarnings("unchecked")
    @Override
    public <T> ParamConverter<T> getConverter(final Class<T> rawType, final Type genericType, final Annotation[] annotations) {
        if (rawType.equals(LocalDate.class)) {
            return new LocalDateTimeParamConverter();
        } else {
            return null;
        }
    }

    @SuppressWarnings("rawtypes")
    private static class LocalDateTimeParamConverter implements ParamConverter {

        @Override
        public LocalDate fromString(String dateString) {
            try {
                if (dateString != null && !dateString.trim().isEmpty()) {
                    dateString = dateString.length() == 9 ? "0" + dateString : dateString;
                    return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("mm/dd/yyyy"));
                }
                return null;
            } catch (DateTimeParseException e) {
                throw new BusinessException(e, ErrorEnumeration.INPUT_PARAMETER_INCORRECT);
            }
        }

        @Override
        public String toString(Object value) {
            return value != null ? value.toString() : null;
        }
    }
}
