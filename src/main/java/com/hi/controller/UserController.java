package com.hi.controller;

import com.hi.config.auth.CustomUserDetails;
import com.hi.dto.UserJoinReqDto;
import com.hi.dto.UserUpdateReqDto;
import com.hi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/join")
    public String join(@RequestBody UserJoinReqDto dto) {
        userService.join(dto);
        return "회원가입 성공";
    }

    //회원수정
    @PutMapping("/modify")
    public String update(@RequestBody UserUpdateReqDto dto, @AuthenticationPrincipal CustomUserDetails user) {
        userService.update(dto, user.getUserId());
        return "회원정보 수정";
    }

    //회원탈퇴
    @DeleteMapping("/delete")
    public String delete(@AuthenticationPrincipal CustomUserDetails user){
        userService.delete(user.getUserId());
        return "회원탈퇴";
    }

    //닉네임 중복검사
    @GetMapping("/nickname/{nickname}")
    public boolean nicknameDuplicateCheck(@PathVariable String nickname) {
        return userService.nicknameDuplicateCheck(nickname);
    }
}
