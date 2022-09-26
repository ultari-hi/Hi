package com.hi.dto;

import com.hi.domain.Accommodation;
import lombok.Data;

import java.util.List;

@Data
public class AccommodationDetailDto {
    private Long id;
    private String nameKor;
    private String nameEng;
    private String postcode;
    private String address;
    private String directions;
    private String introduction;
    private int numberOfPeople;
    private Float rating;
    private String region;
    private String filtering;
    private List<String> imageUrls;

    public AccommodationDetailDto(Accommodation acm, ImageDto dto) {
        this.id = acm.getId();
        this.nameKor = acm.getNameKor();
        this.nameEng = acm.getNameEng();
        this.postcode = acm.getPostcode();
        this.address = acm.getAddress();
        this.directions = acm.getDirections();
        this.introduction = acm.getIntroduction();
        this.numberOfPeople = acm.getNumberPeople();
        this.rating = acm.getRating();
        this.region = acm.getRegion();
        this.filtering = acm.getFiltering();
        this.imageUrls = dto.getUrls();
    }
}
