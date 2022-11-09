package com.hi.dto;

import com.hi.domain.Reservation;
import com.hi.enums.Status;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Data
public class ReservationDto {
    private Long id;
    private Long accommodationId;
    private String accommodationName;
    private Long roomId;
    private String roomName;
    private String enquiry;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer priceKor;
    private Status status;

    public ReservationDto(Reservation reservation, List<LocalDate> dates) {
        Collections.sort(dates);
        this.id = reservation.getId();
        this.accommodationId = reservation.getRoom().getAccommodation().getId();
        this.accommodationName = reservation.getRoom().getAccommodation().getNameKor();
        this.roomId = reservation.getRoom().getId();
        this.roomName = reservation.getRoom().getName();
        this.enquiry = reservation.getEnquiry();
        this.checkInDate = dates.get(0);
        this.checkOutDate = dates.get(dates.toArray().length-1);
        this.priceKor = reservation.getPriceKor();
        this.status = reservation.getStatus();
    }
}
