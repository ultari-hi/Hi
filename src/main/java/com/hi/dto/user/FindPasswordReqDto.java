package com.hi.dto.user;

import lombok.Data;

@Data
public class FindPasswordReqDto {
    private String username;
    private String email;
    private String birthDate;
}
