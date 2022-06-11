package com.hi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class RoomReqDto {
    private String name;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String information;
    private String guide;
    private Integer price;
    private Integer numberOfPeople;
}
