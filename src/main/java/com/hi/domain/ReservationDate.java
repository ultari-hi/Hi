package com.hi.domain;

import lombok.AccessLevel;
import lombok.Builder;
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
    @Column(name = "reservation_date_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    private LocalDate date;

    @Builder
    public ReservationDate(Reservation reservation, Room room, LocalDate date){
        this.reservation = reservation;
        this.room = room;
        this.date = date;
    }

    public ReservationDate reservation(Room room, LocalDate date) {
        return ReservationDate.builder()
                .reservation(reservation)
                .date(date)
                .build();
    }

    public static ReservationDate create(Reservation reservation, Room room, LocalDate date){
        return new ReservationDate(reservation, room, date);
    }
}
