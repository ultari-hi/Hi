package com.hi.dto;

import com.hi.domain.Accommodation;
import com.hi.domain.Reservation;
import com.hi.domain.Room;
import com.hi.domain.User;
import com.hi.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class ReservationDto {
    private Long id;
    private Accommodation accommodation;
    private Room room;
    private String enquiry;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String price;
    private Integer priceKor;
    private Status status;

    public ReservationDto(Reservation reservation) {
        this.id = reservation.getId();
        this.accommodation = reservation.getAccommodation();
        this.room = reservation.getRoom();
        this.enquiry = reservation.getEnquiry();
        this.checkInDate = LocalDate.ofEpochDay(20220907);
        this.checkOutDate = LocalDate.ofEpochDay(20220907);
        this.price = reservation.getPrice();
        this.priceKor = reservation.getPriceKor();
        this.status = reservation.getStatus();
    }
}
