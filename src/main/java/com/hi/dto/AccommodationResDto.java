package com.hi.dto;

import com.hi.domain.Accommodation;
import com.hi.domain.AccommodationImage;
import com.hi.domain.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccommodationResDto {
    private Long id;
    private List<Room> room = new ArrayList<>();
    private List<AccommodationImage> accommodationImage = new ArrayList<>();
    private String nameKor;
    private String nameEng;
    private int postCode;
    private String address;
    private String location;
    private String introduction;
    private int numberOfPeople;
    private int price;
    private Float rating;
    private String filtering;

    public AccommodationResDto(Accommodation accommodation) {
        this.id = accommodation.getId();
        this.room = accommodation.getRoom();
        this.accommodationImage = accommodation.getAccommodationImage();
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
