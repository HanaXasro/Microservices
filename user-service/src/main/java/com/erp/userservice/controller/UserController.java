package com.erp.userservice.controller;

import com.erp.userservice.dto.LoginRequestDto;
import com.erp.userservice.dto.LoginResponseDto;
import com.erp.userservice.services.IUserContext;
import com.erp.userservice.services.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "auth")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IUserContext userContext;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
            return ResponseEntity.ok(userService.login(loginRequestDto));
    }

    @GetMapping("/user")
    public Mono<ResponseEntity<List<String>>> getUserPermissions() {
        List<String> permissions = userContext.getPermissions();
        return Mono.just(ResponseEntity.ok(permissions));
    }
}