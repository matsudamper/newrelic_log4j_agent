package com.newrelic.api.agent;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

@SuppressWarnings("unused")
public final class NewRelic {
    private static final Logger logger = LogManager.getLogger(NewRelic.class);
    public static Agent getAgent() {
        return NoOpAgent.INSTANCE;
    }

    public static void recordMetric(String name, float value) {
    }

    public static void recordResponseTimeMetric(String name, long millis) {
    }

    public static void incrementCounter(String name) {
    }

    public static void incrementCounter(String name, int count) {
    }

    // ************************** Error collector ***********************************//
    public static void noticeError(Throwable throwable, Map<String, String> params) {
        logger.debug(params, throwable);
    }

    public static void noticeError(Throwable throwable) {
        logger.debug(throwable);
    }

    public static void noticeError(String message, Map<String, String> params) {
        logger.debug("message:" + message + ", params:" + params);
    }

    public static void noticeError(String message) {
        logger.debug(message);
    }

    // **************************** Transaction APIs ********************************//
    public static void addCustomParameter(String key, Number value) {
    }

    public static void addCustomParameter(String key, String value) {
    }

    public static void setTransactionName(String category, String name) {
    }

    public static void ignoreTransaction() {
    }

    public static void ignoreApdex() {
    }

    public static void setRequestAndResponse(Request request, Response response) {
    }

    public static String getBrowserTimingHeader() {
        return "";
    }

    public static String getBrowserTimingFooter() {
        return "";
    }

    public static void setUserName(String name) {
    }

    public static void setAccountName(String name) {
    }

    public static void setProductName(String name) {
    }
}
