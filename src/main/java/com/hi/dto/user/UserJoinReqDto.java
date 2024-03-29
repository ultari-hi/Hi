package com.hi.dto.user;

import com.hi.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserJoinReqDto {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String phoneNumber;
    private String postcode;
    private String address;
    private String detailedAddress;
    private String lastNameKor;
    private String firstNameKor;
    private String lastNameEng;
    private String firstNameEng;
    private Gender gender;
    private String birthDate;
}
