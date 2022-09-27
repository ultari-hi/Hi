package com.hi.dto;

import com.hi.domain.Room;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RoomResDto {
    private Long id;
    private String name;
    private String information;
    private String guide;
    private Integer price;
    private Integer numberOfPeople;
    private List<String> imageUrl;

    public RoomResDto(Room room, ImageDto dto){
        this.id = room.getId();
        this.name = room.getName();
        this.information = room.getInformation();
        this.guide = room.getGuide();
        this.price = room.getPriceKor();
        this.numberOfPeople = room.getNumberPeople();
        this.imageUrl = dto.getUrls();
    }
}
