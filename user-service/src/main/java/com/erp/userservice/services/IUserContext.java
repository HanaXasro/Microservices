package com.erp.userservice.services;

import java.util.List;

public interface IUserContext {
     Long getUserId();
     String getUsername();
     List<String> getPermissions();
}
