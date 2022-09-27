package com.hi.dto;

import com.hi.domain.Accommodation;
import lombok.Data;

import java.util.List;

@Data
public class AccommodationResDto {
    private Long id;
    private String nameKor;
    private String nameEng;
    private int priceKor;
    private Float rating;
    private String region;
    private List<String> imageUrls;

    public AccommodationResDto(Accommodation acm, ImageDto dto) {
        this.id = acm.getId();
        this.nameKor = acm.getNameKor();
        this.nameEng = acm.getNameEng();
//        this.rating = review.getRating();
        this.region = acm.getRegion();
        this.imageUrls = dto.getUrls();
        this.priceKor = acm.cheapestRoom().getPriceKor();
    }

}
