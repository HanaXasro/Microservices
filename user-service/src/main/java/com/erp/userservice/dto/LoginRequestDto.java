package com.erp.userservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class LoginRequestDto {
    private String username;
    private String password;
}
