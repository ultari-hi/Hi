package com.hi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class ReservationDto {
    private String enquiry;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
