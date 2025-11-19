package com.fuso.enterprise.ots.srv.common.utils;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

public class TimeZoneInitializer {

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        System.out.println("Forced JVM TimeZone: " + TimeZone.getDefault().getID());
    }
}
