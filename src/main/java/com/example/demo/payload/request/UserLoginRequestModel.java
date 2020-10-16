package com.example.demo.payload.request;

import javax.validation.constraints.NotBlank;

public class UserLoginRequestModel {

    //    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
//    @Size(min = 8, message = "Password must be 8 or more characters in length")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
