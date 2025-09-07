package com.erp.apigateway.services;

import java.util.List;

public interface IUserContext {
    Long getUserId();
    void setUserId(Long userId);
    String getUsername();
    void setUsername(String username);
    List<String> getPermissions();
    void setPermissions(List<String> permissions);
}
