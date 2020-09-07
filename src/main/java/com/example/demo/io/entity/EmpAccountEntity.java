package com.example.demo.io.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity(name="emp_account")
public class EmpAccountEntity implements Serializable {
    private static final long serialVersionUID = 1595083351156627214L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private long employeeCode;

    @Column
    private Timestamp accountStopDate;

    @Column
    private long loginFailed;

    public long getId() {
        return id;
    }

    public long getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(long employeeCode) {
        this.employeeCode = employeeCode;
    }

    public Timestamp getAccountStopDate() {
        return accountStopDate;
    }

    public void setAccountStopDate(Timestamp accountStopDate) {
        this.accountStopDate = accountStopDate;
    }

    public long getLoginFailed() {
        return loginFailed;
    }

    public void setLoginFailed(long loginFailed) {
        this.loginFailed = loginFailed;
    }
}
