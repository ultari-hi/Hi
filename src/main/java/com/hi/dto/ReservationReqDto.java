package com.hi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class ReservationReqDto {
    private int totalAmount;
    private Long roomId;
    private String enquiry;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
