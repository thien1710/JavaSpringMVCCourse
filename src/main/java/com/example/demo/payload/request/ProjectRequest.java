package com.example.demo.payload.request;

import com.example.demo.model.customer.Customer;
import com.example.demo.model.user.User;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;

public class ProjectRequest {

    @NotBlank
    @Size(min = 4, max = 50)
    private String projectName;

    private Instant startTime;

    private Instant finishtTime;

    private Long totalTime;

    private Long budget;

    private String status;

    private String note;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getFinishtTime() {
        return finishtTime;
    }

    public void setFinishtTime(Instant finishtTime) {
        this.finishtTime = finishtTime;
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Long totalTime) {
        this.totalTime = totalTime;
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
