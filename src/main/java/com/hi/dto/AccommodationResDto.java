package com.hi.dto;

import com.hi.domain.Accommodation;
import com.hi.domain.AccommodationImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class AccommodationResDto {
    private Long id;
    private String nameKor;
    private String nameEng;
    private String postCode;
    private String address;
    private String location;
    private String introduction;
    private int numberOfPeople;
//    private String price;
    private int priceKor;
//    private Float rating;
    private String region;
    private String filtering;
    private List<String> urlList;

    public AccommodationResDto(Accommodation acm, ImageDto dto) {
        this.id = acm.getId();
        this.nameKor = acm.getNameKor();
        this.nameEng = acm.getNameEng();
        this.postCode = acm.getPostCode();
        this.address = acm.getAddress();
        this.location = acm.getLocation();
        this.introduction = acm.getIntroduction();
        this.numberOfPeople = acm.getNumberOfPeople();
//        this.rating = acm.getRating();
        this.region = acm.getRegion();
        this.filtering = acm.getFiltering();
        this.urlList = dto.getUrlList();
    }

}
