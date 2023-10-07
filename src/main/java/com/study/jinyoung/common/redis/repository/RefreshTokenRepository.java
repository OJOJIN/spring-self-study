package com.study.jinyoung.common.redis.repository;

import com.study.jinyoung.common.redis.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
}
