package com.hi.dto;

import com.hi.domain.Point;
import com.hi.domain.User;
import lombok.Data;

@Data
public class PaymentResDto {
    private Long userId;
    private String username;
    private String phoneNumber;
    private int balance;

    public PaymentResDto(User user, Point point){
        userId = user.getId();
        username = user.getUsername();
        phoneNumber = user.getPhoneNumber();
        balance = point.getBalance();
    }
}
