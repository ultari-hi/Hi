package com.hi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class RoomReqDto {
    private String name;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String information;
    private String guide;
    private Integer price;
    private Integer numberOfPeople;
    private Boolean available;
    List<String> urlList;
}
