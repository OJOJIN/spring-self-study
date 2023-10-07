package com.study.jinyoung.domain.auth.controller;

import com.study.jinyoung.common.dto.SuccessResponse;
import com.study.jinyoung.common.dto.code.SuccessCode;
import com.study.jinyoung.domain.auth.dto.request.LoginRequestDto;
import com.study.jinyoung.domain.auth.dto.request.RegisterRequestDto;
import com.study.jinyoung.domain.auth.dto.response.LoginResponseDto;
import com.study.jinyoung.domain.auth.dto.response.RegisterResponseDto;
import com.study.jinyoung.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse<RegisterResponseDto>> register(@RequestBody RegisterRequestDto request) {
        RegisterResponseDto response = authService.register(request);
        return SuccessResponse.of(SuccessCode.MEMBER_CREATED, response);
    }
    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto request) {
        LoginResponseDto response = authService.login(request);
        return SuccessResponse.of(SuccessCode.OK, response);
    }

}
