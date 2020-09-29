package com.example.demo.config;

public enum ErrorMessages {
    YOU_DON_T_HAVE_PERMISSION_TO("You don't have permission to "),
    NOT_FOUND_WITH(" not found with "),
    IS_MANDATORY(" is mandatory"),
    DOES_NOT_BELONG_TO(" does not belong to "),
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