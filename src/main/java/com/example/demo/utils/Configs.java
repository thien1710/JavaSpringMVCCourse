package com.example.demo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Configs {
    public interface URL {
        interface USER {
            String SIGN_IN = "/api/v1/users/login";
            String USERS = "/api/v1/users";
            String FORGOT_PASSWORD = "/forgot-password";
            String FORGOT_PASSWORD_REQUEST = "/request";
            String FORGOT_PASSWORD_RESET = "/reset";
        }

        interface PROJECT {
            String PROJECTS = "/api/v1";
            String CUSTOMERID_PATH = "/customers/{customerId}/projects";
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
            String LIMIT = "5";
            String PAGE = "1";
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