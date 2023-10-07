package com.study.jinyoung.domain.user.controller;

import com.study.jinyoung.common.auth.UserId;
import com.study.jinyoung.common.dto.SuccessResponse;
import com.study.jinyoung.common.dto.code.SuccessCode;
import com.study.jinyoung.domain.user.dto.response.UserCheckResponseDto;
import com.study.jinyoung.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/check")
    public ResponseEntity<SuccessResponse<UserCheckResponseDto>> getUserName(@UserId Long userId) {
        UserCheckResponseDto response = userService.getUserByToken(userId);
        return SuccessResponse.of(SuccessCode.OK, response);
    }



}
