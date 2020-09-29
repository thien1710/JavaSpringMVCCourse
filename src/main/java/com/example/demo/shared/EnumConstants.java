package com.example.demo.shared;

public enum EnumConstants {
    PROJECT("Project"),
    CUSTOMER("Customer"),
    ID("Id"),
    ADD("add"),
    DELETE("delete"),
    UPDATE("update"),
    SEARCH("search"),
    THIS_PROJECT (" this project"),
    THIS_CUSTOMER(" this customer"),
    USER_CONDITION("userCondition"),
    CUSTOMER_CONDITION("customerCondition"),
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