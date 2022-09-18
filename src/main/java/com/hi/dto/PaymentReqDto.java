package com.hi.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentReqDto {
    private Long paymentId;
    private int amountPaid;

    private Long userId;
    private int totalAmount;
    private int pointAmount;
    private int cashAmount;
    private String method;

    private Long roomId;
    private String enquiry;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    //결제하기 전 결제 정보 저장
    public PaymentReqDto(Long userId, int totalAmount, int pointAmount, int cashAmount, String method, Long roomId, String enquiry, LocalDate checkInDate, LocalDate checkOutDate) {
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.pointAmount = pointAmount;
        this.cashAmount = cashAmount;
        this.method = method;

        this.roomId = roomId;
        this.enquiry = enquiry;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    //결제한 후 결과에 따른 상태 저장
    public PaymentReqDto(Long paymentId, int amountPaid) {
        this.paymentId = paymentId;
        this.amountPaid = amountPaid;
    }
}
