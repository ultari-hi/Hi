package com.hi.dto.accommodation;

import com.hi.domain.Accommodation;
import com.hi.dto.ImageDto;
import lombok.Data;

import java.util.List;

@Data
public class AccommodationResDto {
    private Long id;
    private String nameKor;
    private String nameEng;
    private int priceKor;
    private double rating;
    private String region;
    private List<String> imageUrls;

    public AccommodationResDto(Accommodation acm, ImageDto dto) {
        this.id = acm.getId();
        this.nameKor = acm.getNameKor();
        this.nameEng = acm.getNameEng();
        this.rating = 4.5;
        this.region = acm.getRegion();
        this.imageUrls = dto.getUrls();
        this.priceKor = acm.cheapestRoom().getPriceKor();
    }

}
