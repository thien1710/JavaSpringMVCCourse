package com.example.demo.config;

public class Configs {
    public interface URL {
        interface USER {
            String SIGN_IN = "/users/login";
            String SIGN_UP = "/users";
        }
    }
    public static final int USER_ID_LENGTH = 30;
}