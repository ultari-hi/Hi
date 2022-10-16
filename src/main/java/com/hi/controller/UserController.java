package com.hi.controller;

import com.hi.config.auth.CustomUserDetails;
import com.hi.dto.JoinReqDto;
import com.hi.dto.LoginReqDto;
import com.hi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/join")
    public String join(@RequestBody JoinReqDto dto) {
        userService.join(dto);
        return "회원가입 성공";
    }
}
