package com.hi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AccommodationReqDto {
    private Long userId;
    private String nameKor;
    private String nameEng;
    private String postcode;
    private String address;
    private String directions;
    private String introduction;
    private String region;
    private String filtering;
    List<String> imageUrls;
}
