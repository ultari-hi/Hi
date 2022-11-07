package com.hi.dto;

import lombok.Data;

@Data
public class FindUsernameReqDto {
    private String email;
    private String birthDate;
}
