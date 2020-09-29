package com.example.demo.utils;

public enum EnumConstants {
    PROJECT("Project"),
    CUSTOMER("Customer"),
    USER("User"),
    DEPARTMENT("Department"),
    ID("Id"),
    ADD("add"),
    DELETE("delete"),
    UPDATE("update"),
    SEARCH("search"),
    THIS_PROJECT (" this project"),
    THIS_CUSTOMER(" this customer"),
    USER_CONDITION("User condition"),
    CUSTOMER_CONDITION("Customer condition"),
    PROJECT_CONDITION("Project condition"),
    SEARCH_REQUEST("Search request"),
    EQUAL(" = "),
    USER_ROLE("User role"),
    USER_NAME("User name"),
    PASSWORD("Password"),
    EMAIL("Email"),
    TOKEN("Token"),
    ;

    private String enumConstants;

    EnumConstants(String enumConstants) {
        this.setEnumConstants(enumConstants);
    }

    @Override
    public String toString() {
        return enumConstants;
    }

    /**
     * @return the error message
     */
    public String getEnumConstants() {
        return enumConstants;
    }

    /**
     * @param enumConstants the error message to set
     */
    public void setEnumConstants(String enumConstants) {
        this.enumConstants = enumConstants;
    }
}