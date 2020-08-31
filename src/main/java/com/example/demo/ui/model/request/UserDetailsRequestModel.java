package com.example.demo.ui.model.request;

import com.example.demo.ui.model.response.ValidationMessages;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDetailsRequestModel {
    private String firstname;
    private String lastname;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 6, message = ValidationMessages.PASSWORD_AT_LEAST_6_CHARACTERS)
    private String password;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

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
