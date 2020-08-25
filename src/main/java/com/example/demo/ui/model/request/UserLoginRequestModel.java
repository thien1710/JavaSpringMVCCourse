package com.example.demo.ui.model.request;
//
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;

public class UserLoginRequestModel {
//    @NotNull
//    @Email(message = ValidationMessages.EMAIL_NOT_VALID)
    private String email;

//    @NotNull
//    @Size(min = 6, message = ValidationMessages.PASSWORD_AT_LEAST_6_CHARACTERS)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}