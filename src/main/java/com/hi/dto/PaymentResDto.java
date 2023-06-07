package com.hi.dto;

import com.hi.domain.Payment;
import com.hi.domain.Room;
import com.hi.domain.User;
import lombok.Data;

@Data
public class PaymentResDto {
    private Long merchantUid;
    private String payMethod;
    private String roomName;
    private int amount;
    private String buyerEmail;
    private String buyerName;
    private String buyerTel;
    private String buyerAddr;
    private String buyerPostcode;

    public PaymentResDto(Payment payment, Room room, User user) {
        this.merchantUid = payment.getId();
        this.payMethod = payment.getMethod();
        this.roomName = room.getName();
        this.amount = payment.getCashAmount();
        this.buyerEmail = user.getEmail();
        this.buyerName = user.getLastNameKor() + user.getFirstNameEng();
        this.buyerTel = user.getPhoneNumber();
        this.buyerAddr = user.getAddress();
        this.buyerPostcode = user.getPostcode();
    }
}
