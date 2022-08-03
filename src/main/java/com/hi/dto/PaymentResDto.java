package com.hi.dto;

import com.hi.domain.User;
import lombok.Data;

@Data
public class PaymentResDto {
    private Long userId;
    private String username;
    private String phoneNumber;
    private Integer point;

    public PaymentResDto(User user){
        userId = user.getId();
        username = user.getUsername();
        phoneNumber = user.getPhoneNumber();
        point = user.getPoint();
    }
}
