package com.study.jinyoung.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity(name = "Member")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;

    private String password;


    public static User createUser(String username, String password) {
        User user = User.builder()
                .username(username)
                .password(password)
                .build();
        return user;
    }
}
