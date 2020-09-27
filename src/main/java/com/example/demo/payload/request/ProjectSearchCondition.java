package com.example.demo.payload.request;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

public class ProjectSearchCondition {
    private Long id;

    private String projectName;

    private Instant startTime;

    private Instant finishtTime;

    private Long totalTime;

    private Long budget;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
