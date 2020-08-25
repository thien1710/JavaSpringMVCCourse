package com.example.demo.security;

import com.example.demo.SpringApplicationContext;
import com.example.demo.config.Constants;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = Constants.TIME.ONE_DAY;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");

        return appProperties.getTokenSecret();
    }}
