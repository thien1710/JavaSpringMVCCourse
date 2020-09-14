package com.example.demo.config;

public enum ErrorMessages {
    YOU_DON_T_HAVE_PERMISSION_TO("You don't have permission to"),
    THIS_PROJECT (" this project"),
    THIS_CUSTOMER(" this customer")
    ;

    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.setErrorMessage(errorMessage);
    }

    @Override
    public String toString() {
        return errorMessage;
    }

    /**
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the error message to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}