package com.hi.domain;

import com.hi.dto.AccommodationReqDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accommodation_id")
    private Long id;

    @Column(name = "name_kor")
    private String nameKor;

    @Column(name = "name_eng")
    private String nameEng;

    @Column(name = "post_code")
    private int postCode;

    private String address;

    private String location;

    private String introduction;

    @Column(name = "number_of_people")
    private int numberOfPeople;

    @ColumnDefault("0")
    private int price;

    @ColumnDefault("0.0")
    private Float rating;


    private String filtering;

    @Builder
    public Accommodation(String nameKor, String nameEng, int postCode, String address, String location, String introduction, int numberOfPeople, int price, Float rating, String filtering) {
        this.nameKor = nameKor;
        this.nameEng = nameEng;
        this.postCode = postCode;
        this.address = address;
        this.location = location;
        this.introduction = introduction;
        this.numberOfPeople = numberOfPeople;
        this.price = price;
        this.rating = rating;
        this.filtering = filtering;
    }

    public static Accommodation createAccommodation(AccommodationReqDto dto){
        return Accommodation.builder()
                .nameKor(dto.getNameKor())
                .nameEng(dto.getNameEng())
                .postCode(dto.getPostCode())
                .address(dto.getAddress())
                .location(dto.getLocation())
                .introduction(dto.getIntroduction())
                .numberOfPeople(dto.getNumberOfPeople())
                .price(dto.getPrice())
                .filtering(dto.getFiltering())
                .build();
    }

    public void modifyAccommodation(AccommodationReqDto dto){
        Accommodation.builder()
                .nameKor(dto.getNameKor())
                .nameEng(dto.getNameEng())
                .postCode(dto.getPostCode())
                .address(dto.getAddress())
                .location(dto.getLocation())
                .introduction(dto.getIntroduction())
                .numberOfPeople(dto.getNumberOfPeople())
                .price(dto.getPrice())
                .filtering(dto.getFiltering())
                .build();
    }


}


