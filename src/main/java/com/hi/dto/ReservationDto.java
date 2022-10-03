package com.hi.dto;

import com.hi.domain.Accommodation;
import com.hi.domain.Reservation;
import com.hi.domain.Room;
import com.hi.domain.User;
import com.hi.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Data
public class ReservationDto {
    private Long id;
    private Accommodation accommodation;
    private Room room;
    private String enquiry;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer priceKor;
    private Status status;

    public ReservationDto(Reservation reservation, List<LocalDate> dates) {
        Collections.sort(dates);
        this.id = reservation.getId();
        this.accommodation = reservation.getAccommodation();
        this.room = reservation.getRoom();
        this.enquiry = reservation.getEnquiry();
        this.checkInDate = dates.get(0);
        this.checkOutDate = dates.get(dates.toArray().length);
        this.priceKor = reservation.getPriceKor();
        this.status = reservation.getStatus();
    }
}
