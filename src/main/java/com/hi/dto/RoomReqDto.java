package com.hi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class RoomReqDto {
    private String name;
    private String information;
    private String guide;
    private Integer price;
    private Integer numberPeople;
    private Boolean isAvailable;
    List<String> imageUrls;
}
