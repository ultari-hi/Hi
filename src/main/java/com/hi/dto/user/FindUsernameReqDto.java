package com.hi.dto.user;

import lombok.Data;

@Data
public class FindUsernameReqDto {
    private String email;
    private String birthDate;
}
