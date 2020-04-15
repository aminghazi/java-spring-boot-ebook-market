package com.ebookmarket.domain.security;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Role {

    @Id
    private int roleId;
    private String name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserRole> userRoles = new HashSet<>();

    public int getRoleId() {
        return roleId;
    }

    public String getName() {
        return name;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
