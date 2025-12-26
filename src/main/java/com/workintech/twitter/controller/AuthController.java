package com.workintech.twitter.controller;

import com.workintech.twitter.dto.requests.LoginRequest;
import com.workintech.twitter.dto.responses.LoginResponse;
import com.workintech.twitter.dto.requests.RegisterRequest;
import com.workintech.twitter.dto.responses.UserSummaryResponse;
import com.workintech.twitter.mapper.UserMapper;
import com.workintech.twitter.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public UserSummaryResponse register(@RequestBody @Valid RegisterRequest request) {
        return UserMapper.toSummary(authService.register(request));
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        String token = authService.login(request);
        return new LoginResponse(token);
    }
}
