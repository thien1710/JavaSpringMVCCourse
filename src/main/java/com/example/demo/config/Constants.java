package com.example.demo.config;

public interface Constants {
    interface TIME {
        long TEN_SECONDS = 10000;
        long ONE_MINUS = 60 * 1000;
        long ONE_HOUR = 60 * 60 * 1000;
        long ONE_DAY = 24 * 60 * 60 * 1000;
    }
    interface RANDOM {
        String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuwxyz";
    }
    interface REGREX {
        String PASSWORD = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,}$";
        String USERNAME = "[a-z0-9_-]{1,25}$";
    }

}