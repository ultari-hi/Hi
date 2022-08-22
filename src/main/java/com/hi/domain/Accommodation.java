package com.hi.domain;

import com.hi.dto.AccommodationReqDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accommodation_id", columnDefinition = "bigint")
    private Long id;

    @OneToMany(mappedBy = "accommodation")
    private final List<AccommodationImage> accommodationImage = new ArrayList<>();

    @Column(name = "name_kor", columnDefinition = "varchar(20)", nullable = false)
    private String nameKor;

    @Column(name = "name_eng", columnDefinition = "varchar(20)", nullable = false)
    private String nameEng;

    @Column(name = "post_code", columnDefinition = "varchar(5)")
    private String postCode;

    @Column(name = "address", columnDefinition = "varchar(50)", nullable = false)
    private String address;

    @Column(name = "name_eng", columnDefinition = "varchar(50)", nullable = false)
    private String location;

    @Column(name = "introduction", columnDefinition = "text")
    private String introduction;

    @Column(name = "number_of_people", columnDefinition = "integer", nullable = false)
    private Integer numberOfPeople;

    @Column(name = "price", columnDefinition = "varchar(10)", nullable = false)
    @ColumnDefault("0")
    private String price;

    @Column(name = "price_kor", columnDefinition = "integer", nullable = false)
    @ColumnDefault("0")
    private Integer priceKor;

    @Column(name = "rating", columnDefinition = "float", nullable = false)
    @ColumnDefault("0.0")
    private Float rating;

    @Column(name = "region", columnDefinition = "varchar(10)", nullable = false)
    private String region;

    @Column(name = "filtering", columnDefinition = "text")
    private String filtering;

    @Builder
    public Accommodation(String nameKor, String nameEng, String postCode, String address, String location, String introduction, Integer numberOfPeople, String price, Integer priceKor, Float rating, String region, String filtering) {
        this.nameKor = nameKor;
        this.nameEng = nameEng;
        this.postCode = postCode;
        this.address = address;
        this.location = location;
        this.introduction = introduction;
        this.numberOfPeople = numberOfPeople;
        this.price = price;
        this.priceKor = priceKor;
        this.rating = rating;
        this.region = region;
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
                .priceKor(dto.getPriceKor())
                .region(dto.getRegion())
                .filtering(dto.getFiltering())
                .build();
    }

    public void update(AccommodationReqDto dto){
        this.nameKor = dto.getNameKor();
        this.nameEng = dto.getNameEng();
        this.postCode = dto.getPostCode();
        this.address = dto.getAddress();
        this.location = dto.getLocation();
        this.introduction = dto.getIntroduction();
        this.numberOfPeople = dto.getNumberOfPeople();
        this.price = dto.getPrice();
        this.filtering = dto.getFiltering();
    }
}


