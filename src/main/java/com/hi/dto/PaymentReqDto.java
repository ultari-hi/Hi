package com.hi.dto;

import com.hi.enums.Status;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentReqDto {
    private Long reservationId;
    private Status status;

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

    public PaymentReqDto(Long userId, Integer totalAmount, Integer pointPay, Integer cashPay, String method, Long roomId, String enquiry, LocalDate checkInDate, LocalDate checkOutDate) {
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.pointPay = pointPay;
        this.cashPay = cashPay;
        this.method = method;

        this.roomId = roomId;
        this.enquiry = enquiry;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public PaymentReqDto(Long userId, Long reservationId, Long roomId, Status status, LocalDate checkInDate, LocalDate checkOutDate) {
        this.userId = userId;
        this.reservationId = reservationId;
        this.roomId = roomId;
        this.status = status;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }
}
