package com.hi.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserUpdateReqDto {
    private String password;
    private String nickname;
    private String email;
    private String phoneNumber;
    private String postcode;
    private String address;
    private String detailedAddress;
}
