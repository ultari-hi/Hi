package com.hi.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentReqDto {
    private int totalAmount;
    private int pointAmount;
    private int cashAmount;
    private String method;
    private Long roomId;
    private String enquiry;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    //결제하기 전 결제 정보 저장
    public PaymentReqDto(int totalAmount, int pointAmount, int cashAmount, String method, Long roomId, String enquiry, LocalDate checkInDate, LocalDate checkOutDate) {
        this.totalAmount = totalAmount;
        this.pointAmount = pointAmount;
        this.cashAmount = cashAmount;
        this.method = method;
        this.roomId = roomId;
        this.enquiry = enquiry;
        this.checkInDate = checkInDate;
        // 퇴실 날짜와 다른 사람의 입실 날짜가 겹쳐도 상관 없으므로 -1일
        // 최소 1박 2일이므로 1일만 저장해도 상관없음
        this.checkOutDate = checkOutDate.minusDays(1);
    }
}
