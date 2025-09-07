package com.erp.apigateway.services.impl;

import com.erp.apigateway.services.IUserContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserContext implements IUserContext {

    private Long userId;
    private String username;
    private List<String> permissions;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public List<String> getPermissions() {
        return permissions;
    }

    @Override
    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
