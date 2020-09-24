package com.example.demo.payload.request;

import com.example.demo.model.role.Role;
import java.util.Set;

public class UserSearchCondition {

    private Long id;

    private String userIdHash;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private String email;

    private String address;

    private String phone;

    private String website;

    private Long salaryNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserIdHash() {
        return userIdHash;
    }

    public void setUserIdHash(String userIdHash) {
        this.userIdHash = userIdHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Long getSalaryNum() {
        return salaryNum;
    }

    public void setSalaryNum(Long salaryNum) {
        this.salaryNum = salaryNum;
    }
}
