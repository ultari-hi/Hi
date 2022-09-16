package com.hi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class AccommodationReqDto {
    private String nameKor;
    private String nameEng;
    private String postCode;
    private String address;
    private String location;
    private String introduction;
    private Integer numberOfPeople;
    private String region;
    private String filtering;
    List<String> urlList;
}
