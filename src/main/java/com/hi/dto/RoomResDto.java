package com.hi.dto;

import com.hi.domain.Room;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RoomResDto {
    private Long id;
    private String name;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String information;
    private String guide;
    private Integer price;
    private Integer numberOfPeople;

    public RoomResDto(Room room){
        this.id = room.getId();
        this.name = room.getName();
        this.checkInDate = room.getCheckInDate();
        this.checkOutDate = room.getCheckOutDate();
        this.information = room.getInformation();
        this.guide = room.getGuide();
        this.price = room.getPrice();
        this.numberOfPeople = room.getNumberOfPeople();
    }
}
