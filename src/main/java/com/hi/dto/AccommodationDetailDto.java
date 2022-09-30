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
//    private Float rating;
    private String region;
    private List<String> filtering;
    private List<String> imageUrls;
    private List<RoomResDto> rooms;

    public AccommodationDetailDto(Accommodation acm, ImageDto imageDto, List<RoomResDto> roomResDto) {
        this.id = acm.getId();
        this.nameKor = acm.getNameKor();
        this.nameEng = acm.getNameEng();
        this.postcode = acm.getPostcode();
        this.address = acm.getAddress();
        this.directions = acm.getDirections();
        this.introduction = acm.getIntroduction();
//        this.rating = review.getRating();
        this.region = acm.getRegion();
        this.filtering = acm.separateLetters(acm.getFiltering());
        this.imageUrls = imageDto.getUrls();
        this.rooms = roomResDto;
    }
}
