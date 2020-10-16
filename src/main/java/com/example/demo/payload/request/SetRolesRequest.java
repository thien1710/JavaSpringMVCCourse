package com.example.demo.payload.request;

import com.example.demo.model.role.Role;

import java.util.Set;

public class SetRolesRequest {
    private Set<Role> role;

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }
}
