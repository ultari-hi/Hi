package com.hi.dto.accommodation;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
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
    private String type;
    private List<String> filtering;
    private Boolean isAvailable;
    List<String> imageUrls;
}
