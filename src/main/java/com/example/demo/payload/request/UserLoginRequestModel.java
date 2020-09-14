package com.example.demo.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserLoginRequestModel {

    private String object;
    private SubUserLoginRequestModel item;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public SubUserLoginRequestModel getItem() {
        return item;
    }

    public void setItem(SubUserLoginRequestModel item) {
        this.item = item;
    }

}
