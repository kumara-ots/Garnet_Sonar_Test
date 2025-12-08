package com.fuso.enterprise.ots.srv.common.utils;

public class GenericUtils {

	private GenericUtils() {
		// Utility class: prevent instantiation
	}

	public static Double asDecimal(String number) {
		if (number == null || number.trim().isEmpty()) {
			return null;
		}
		try {
			double num = Double.parseDouble(number.trim());
			return Math.round(num * 100) / 100.0;
		} catch (NumberFormatException e) {
			return null; // or throw custom exception
		}
	}

	public static Double as4Decimal(String number) {
		if (number == null || number.trim().isEmpty()) {
			return null;
		}
		try {
			double num = Double.parseDouble(number.trim());
			return Math.round(num * 10000) / 10000.0;
		} catch (NumberFormatException e) {
			return null; // or throw custom exception
		}
	}

	public static long asInteger(Double number) {
		if (number == null) {
			return 0;
		}
		return Math.round(number * 100);
	}

	public static long as4Integer(Double number) {
		if (number == null) {
			return 0;
		}
		return Math.round(number * 10000);
	}
}
