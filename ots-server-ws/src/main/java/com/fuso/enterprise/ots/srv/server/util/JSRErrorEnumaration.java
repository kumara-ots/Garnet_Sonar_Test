
package com.fuso.enterprise.ots.srv.server.util;

public enum JSRErrorEnumaration {
	
	SIZE(1999),
    LENGTH(2000),
    NOTNULL(2001),
    DIGITS(2002),
    ASSERTFALSE(2003),
    ASSERTTRUE(2004),
    DECIMALMAX(2005),
    DECIMALMIN(2006),
    EMAIL(2007),
    FUTURE(2008),
    MAX(2009),
    MIN(2010),
    NOTEMPTY(2011),
    NULL(2012),
    PAST(2013),
    PATTERN(2014),
    RANGE(2015),
    VALID(2016);	

    private final int errorCode;
    
    private static final int INTEGER_ZERO = 0;
    
	

	public int getErrorCode() {
		return errorCode;
	}

	private JSRErrorEnumaration( int errorCode)
    {
        this.errorCode = errorCode;
        
    }
	
	public static int fromString(String value){
		if(value != null){
			for(JSRErrorEnumaration values : JSRErrorEnumaration.values()){
				if(value.equalsIgnoreCase(values.name())){
					return values.errorCode;
				}
			}
		}
		return INTEGER_ZERO;
		
	}
}
