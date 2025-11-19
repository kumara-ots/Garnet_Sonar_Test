package com.fuso.enterprise.ots.srv.common.utils;

public class GenericUtils {
	
	public static Double asDecimal(String number) {		
		Double dNumber = (double) ((asInteger(Double.parseDouble(number)))/100);		
		return dNumber;
	}
	
	public static Double as4Decimal(String number) {		
		Double dNumber = (double) ((as4Integer(Double.parseDouble(number)))/10000);		
		return dNumber;
	}
	
	public static long asInteger(Double number) {
		return Math.round(number*100);		
	}
	
	public static long as4Integer(Double number) {
		return Math.round(number*10000);		
	}

}
