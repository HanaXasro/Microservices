package com.erp.userservice.services.impl;

import com.erp.commonlib.exceptions.BadRequestException;
import com.erp.commonlib.exceptions.NotFoundException;
import com.erp.commonlib.exceptions.UnauthorizedException;
import com.erp.userservice.dto.LoginRequestDto;
import com.erp.userservice.dto.LoginResponseDto;
import com.erp.userservice.entities.User;
import com.erp.userservice.helper.JwtTokenUtil;
import com.erp.userservice.repositories.IUser;
import com.erp.userservice.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUser userRepo;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        User user = userRepo.findByUsername(loginRequestDto.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Unauthorized"));

        if (!BCrypt.checkpw(loginRequestDto.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Unauthorized");
        }

        String token = jwtTokenUtil.generateToken(user);

        return new LoginResponseDto(token);
    }

    @Override
    public String User() {
        return "";
    }
}