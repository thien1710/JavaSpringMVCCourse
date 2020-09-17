package com.example.demo.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Configs {
    public interface URL {
        interface USER {
            String SIGN_IN = "/api/v1/users/login";
            String USERS = "/api/v1/users";
            String PASSWORD_FORGOT_REQUEST_URL = "/password-forgot-request";
            String PASSWORD_RESET_REQUEST_URL = "/password-reset-request";
        }

        interface PROJECT {
            String PROJECTS = "/api/v1/customers/{customerId}/projects";
        }

        interface CUSTOMER {
            String CUSTOMERS = "/api/v1/customers";
        }

        interface AUTH {
            String AUTHS = "/api/v1/auth";
        }
    }

    public interface PAGING {
        interface USER {
            String LIMIT = "25";
            String PAGE = "0";
        }
    }

    public static final int USER_ID_LENGTH = 30;

    public class AppConstant {
        public static final String CUSTOMER = "Customer";
        public static final String PROJECT = "Project";
        public static final String ID = "id";
    }

    public static boolean isValidTextRegrex(String textCompareWithRegrex, String regrexPattern) {
        Pattern p = Pattern.compile(regrexPattern);
        Matcher m = p.matcher(textCompareWithRegrex);
        return m.matches();
    }
}