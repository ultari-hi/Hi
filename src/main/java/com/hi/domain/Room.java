package com.hi.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @OneToMany(mappedBy = "room")
    private List<RoomImage> roomImage = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    private List<Reservation> reservation = new ArrayList<>();

    private String name;

    @Column(name = "check_in_date")
    private LocalDate checkInDate;

    @Column(name = "check_out_date")
    private LocalDate checkOutDate;

    private String information;

    private String guide;

    private Integer price;

    @Column(name = "number_of_people")
    private Integer numberOfPeople;

    @ColumnDefault("TRUE")
    private boolean available;

    public Room(Long id, Accommodation accommodation, List<RoomImage> roomImage, List<Reservation> reservation, String name, LocalDate checkInDate, LocalDate checkOutDate, String information, String guide, Integer price, Integer numberOfPeople, boolean available) {
        this.id = id;
        this.accommodation = accommodation;
        this.roomImage = roomImage;
        this.reservation = reservation;
        this.name = name;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.information = information;
        this.guide = guide;
        this.price = price;
        this.numberOfPeople = numberOfPeople;
        this.available = available;
    }

    public Room(LocalDate checkInDate, LocalDate checkOutDate, int numberOfPeople, Accommodation accommodation){
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfPeople = numberOfPeople;
        this.accommodation = accommodation;
    }
}