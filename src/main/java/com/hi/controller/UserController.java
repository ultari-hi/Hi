package com.hi.controller;

import com.hi.config.auth.CustomUserDetails;
import com.hi.dto.*;
import com.hi.dto.user.*;
import com.hi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PutMapping("/update")
    public String update(@RequestBody UserUpdateReqDto dto, @AuthenticationPrincipal CustomUserDetails user) {
        userService.update(dto, user.getUserId());
        return "회원정보 수정";
    }

    //회원탈퇴
    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@AuthenticationPrincipal CustomUserDetails user){
        userService.delete(user.getUserId());
        return ResponseEntity.ok().build();
    }

    //아이디 중복검사
    @GetMapping("/username/{username}")
    public boolean usernameDuplicateCheck(@PathVariable String username) {
        return userService.checkDuplicateUsername(username);
    }

    //닉네임 중복검사
    @GetMapping("/nickname/{nickname}")
    public boolean nicknameDuplicateCheck(@PathVariable String nickname) {
        return userService.checkDuplicateNickname(nickname);
    }

    //이메일 인증 메일 전송
    @PostMapping("/email")
    public String sendEmailKey(@RequestBody EmailReqDto email){
        return userService.sendKey(email.getEmail());
    }

    //이메일 인증번호 확인
    @PostMapping("/email/check")
    public String checkEmailKey(@RequestBody EmailKeyReqDto dto){
        return userService.checkEmailKey(dto);
    }

    //아이디 찾기
    @PostMapping("/findUsername")
    public FindUsernameResDto findUsername(@RequestBody FindUsernameReqDto dto){
        return userService.findUsername(dto);
    }

    //비밀번호 찾기
    @PostMapping("/findPassword")
    public ResponseEntity<Object> findPassword(@RequestBody FindPasswordReqDto dto){
        userService.findPassword(dto);
        return new ResponseEntity<>(userService.findPassword(dto),HttpStatus.OK);
    }

    //비밀번호 변경
    @PostMapping("/password/update")
    public String changePassword(@RequestBody ChangePasswordReqDto dto){
        return userService.changePassword(dto);
    }
}
