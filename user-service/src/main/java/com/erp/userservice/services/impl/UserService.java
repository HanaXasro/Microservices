package com.erp.userservice.services.impl;

import com.erp.commonlib.exceptions.BadRequestException;
import com.erp.commonlib.exceptions.NotFoundException;
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
    private JwtTokenUtil jwtTokenUtil; // You'll need a JWT utility class

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        // Find user by username
        User user = userRepo.findByUsername(loginRequestDto.getUsername())
                .orElseThrow(() -> new BadRequestException("User not found"));

        // Verify password
        if (!BCrypt.checkpw(loginRequestDto.getPassword(), user.getPassword())) {
            throw new NotFoundException("Invalid password");
        }

        // Generate token (using JWT in this example)
        String token = jwtTokenUtil.generateToken(user);

        // Create and return response
        return new LoginResponseDto(token);
    }
}