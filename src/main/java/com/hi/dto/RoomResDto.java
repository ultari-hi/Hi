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
    private String type;
    private Integer numberPeople;
    private List<String> filtering;
    private List<String> imageUrl;
    private Boolean isAvailable;

    public RoomResDto(Room room, boolean isAvailable, ImageDto dto){
        this.id = room.getId();
        this.name = room.getName();
        this.information = room.getInformation();
        this.guide = room.getGuide();
        this.price = room.getPriceKor();
        this.type = room.getType();
        this.numberPeople = room.getNumberPeople();
        this.filtering = room.separateLetters(room.getFiltering());
        this.imageUrl = dto.getUrls();
        this.isAvailable = isAvailable;
    }
}
