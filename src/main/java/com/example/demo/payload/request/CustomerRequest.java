package com.example.demo.payload.request;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CustomerRequest {

    @NotNull(message = "customerName must not be null")
    private String customerName;

    @NotNull(message = "phone must not be null")
    @Digits(message = "phone must be a number", integer = 10, fraction = 0)
    @Size(min = 10, max = 10)
    private String phone;

    @NotNull(message = "email must not be null")
    @Email
    private String email;

    private String address;

    private String filed;

    private String note;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFiled() {
        return filed;
    }

    public void setFiled(String filed) {
        this.filed = filed;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
