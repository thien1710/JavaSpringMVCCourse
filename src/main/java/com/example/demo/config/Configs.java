package com.example.demo.config;

public class Configs {
    public interface URL {
        interface USER {
            String SIGN_IN = "/rest/v1/entities/users/login";
            String USERS = "/rest/v1/entities/users";
        }
        interface PROJECT {
            String PROJECTS = "/rest/v1/entities/customers/{customerId}/projects";
        }
        interface CUSTOMER {
            String CUSTOMERS = "/rest/v1/entities/customers";
        }
        interface AUTH {
            String AUTHS = "/rest/v1/entities/auth";
        }
    }

    public interface PAGING {
        interface USER {
            String LIMIT = "25";
            String PAGE = "0";
        }
    }

    public static final int USER_ID_LENGTH = 30;
}