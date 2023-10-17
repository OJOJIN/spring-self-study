package com.study.jinyoung.domain.user.service;

import com.study.jinyoung.common.error.ErrorCode;
import com.study.jinyoung.common.error.EntityNotFoundException;
import com.study.jinyoung.domain.user.dto.response.UserCheckResponseDto;
import com.study.jinyoung.domain.user.entity.User;
import com.study.jinyoung.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserCheckResponseDto getUserByToken(Long userId) {
        User findUser = getUserByUserId(userId);
        return UserCheckResponseDto.builder()
                .userId(findUser.getId())
                .username(findUser.getUsername())
                .build();
    }
    private User getUserByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
