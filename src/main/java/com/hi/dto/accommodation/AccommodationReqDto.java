package com.hi.dto.accommodation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationReqDto {
    private String nameKor;
    private String nameEng;
    private String postcode;
    private String address;
    private String directions;
    private String introduction;
    private String region;
    private List<String> filtering;
    List<String> imageUrls;
}
