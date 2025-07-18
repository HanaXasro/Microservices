package com.erp.userservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class LoginRequestDto {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
