package com.hi.dto.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
public class ChangePasswordReqDto {
    private Long userId;
    private String password;

    public ChangePasswordReqDto(Long userId, String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.userId = userId;
        this.password = bCryptPasswordEncoder.encode(password);
    }
}
