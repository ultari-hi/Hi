package com.hi.dto.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@RequiredArgsConstructor
public class ChangePasswordReqDto {
    private Long userId;
    private String password;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String hashPassword(String password){
        return bCryptPasswordEncoder.encode(password);
    }
}
