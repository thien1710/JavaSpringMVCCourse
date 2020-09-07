package com.example.demo.io.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@Entity(name="emp_customer")
public class EmpCustomerEntity implements Serializable {

    private static final long serialVersionUID = -137595367217590338L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private long companyCode;

    @Column(nullable = false)
    private long employeeCode;

    @Column
    private long permType;



    public long getId() {
        return id;
    }

    public long getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(long companyCode) {
        this.companyCode = companyCode;
    }

    public long getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(long employeeCode) {
        this.employeeCode = employeeCode;
    }

    public long getPermType() {
        return permType;
    }

    public void setPermType(long permType) {
        this.permType = permType;
    }
}
