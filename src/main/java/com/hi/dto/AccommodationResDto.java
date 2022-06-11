package com.hi.dto;

import com.hi.domain.Accommodation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccommodationResDto {
    private Long id;
    private String nameKor;
    private String nameEng;
    private int postCode;
    private String address;
    private String location;
    private String introduction;
    private int numberOfPeople;
    private int price;
    private int priceKor;
    private Float rating;
    private String filtering;

    public AccommodationResDto(Accommodation accommodation) {
        this.id = accommodation.getId();
        this.nameKor = accommodation.getNameKor();
        this.nameEng = accommodation.getNameEng();
        this.postCode = accommodation.getPostCode();
        this.address = accommodation.getAddress();
        this.location = accommodation.getLocation();
        this.introduction = accommodation.getIntroduction();
        this.numberOfPeople = accommodation.getNumberOfPeople();
        this.price = accommodation.getPrice();
        this.rating = accommodation.getRating();
        this.filtering = accommodation.getFiltering();
    }

}
