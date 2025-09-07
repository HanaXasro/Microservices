package com.erp.userservice.services;

import com.erp.userservice.dto.LoginRequestDto;
import com.erp.userservice.dto.LoginResponseDto;

public interface IUserService {
    LoginResponseDto login(LoginRequestDto loginRequestDto);
    String User();
}
