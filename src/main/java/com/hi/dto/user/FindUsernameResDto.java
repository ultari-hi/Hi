package com.hi.dto.user;

import lombok.Data;

@Data
public class FindUsernameResDto {
    private String nickname;

    public FindUsernameResDto(String nickname){
        this.nickname = nickname;
    }
}
