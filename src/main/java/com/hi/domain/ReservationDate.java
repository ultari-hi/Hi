package com.hi.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ReservationDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_date_id", columnDefinition = "bigint")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "date", columnDefinition = "date", nullable = false)
    private LocalDate date;

    public ReservationDate(Reservation reservation, Room room, LocalDate date){
        this.reservation = reservation;
        this.room = room;
        this.date = date;
    }

    public static ReservationDate newReservationDate(Reservation reservation, Room room, LocalDate date){
        return new ReservationDate(reservation, room, date);
    }
}
