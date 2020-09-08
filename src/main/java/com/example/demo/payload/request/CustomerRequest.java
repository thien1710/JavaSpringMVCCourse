package com.example.demo.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CustomerRequest {

    @NotBlank
    @Size(min = 10)
    private String customerName;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
