package com.hi.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentReqDto {
    private Long reservationId;
    private String status;

    private Long userId;
    private Integer totalAmount;
    private Integer pointPay;
    private Integer cashPay;
    private String method;

    private Long accommodationId;
    private Long roomId;
    private String enquiry;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public PaymentReqDto(Long userId, Integer totalAmount, Integer pointPay, Integer cashPay, String method, Long accommodationId, Long roomId, String enquiry, LocalDate checkInDate, LocalDate checkOutDate) {
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.pointPay = pointPay;
        this.cashPay = cashPay;
        this.method = method;
        this.accommodationId = accommodationId;
        this.roomId = roomId;
        this.enquiry = enquiry;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public PaymentReqDto(Long reservationId, String status) {
        this.reservationId = reservationId;
        this.status = status;
    }
}
