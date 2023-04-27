package com.hi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailKeyReqDto {
    private String email;
    private String checkNum;
}
