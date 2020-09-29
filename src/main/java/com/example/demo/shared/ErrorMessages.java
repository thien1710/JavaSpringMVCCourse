package com.example.demo.shared;

public enum ErrorMessages {
    MISSING_REQUIRED_FIELD("Missing required field"),
    RECORD_ALREADY_ERROR("Record already error"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    NO_RECORD_FOUND("No record found"),
    AUTHENTICATION_FAILED("Authentication failed"),
    COULD_NOT_CREATE_RECORD("Could not create record"),
    COULD_NOT_UPDATE_RECORD("Could not update record"),
    COUNT_NOT_DELETE_RECORD("Could not delete record"),
    EMAIL_ADDRESS_NOT_VERIFIED("Email address not verified"),
    VALIDATION_ERROR("Validation error");

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