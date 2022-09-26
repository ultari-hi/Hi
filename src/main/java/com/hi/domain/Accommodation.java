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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "accommodation")
    private final List<AccommodationImage> accommodationImages = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation")
    private final List<Room> rooms = new ArrayList<>();

    @Column(name = "name_kor", columnDefinition = "varchar(20)", nullable = false)
    private String nameKor;

    @Column(name = "name_eng", columnDefinition = "varchar(20)", nullable = false)
    private String nameEng;

    @Column(name = "postcode", columnDefinition = "varchar(10)")
    private String postcode;

    @Column(name = "address", columnDefinition = "varchar(50)", nullable = false)
    private String address;

    @Column(name = "directions", columnDefinition = "varchar(50)")
    private String directions;

    @Column(name = "introduction", columnDefinition = "text")
    private String introduction;

    @Column(name = "number_people", columnDefinition = "int", nullable = false)
    private int numberPeople;

    @Column(name = "rating", columnDefinition = "float(3,2)")
    @ColumnDefault("0.0")
    private Float rating;

    @Column(name = "region", columnDefinition = "varchar(10)", nullable = false)
    private String region;

    @Column(name = "filtering", columnDefinition = "text")
    private String filtering;

    @Builder
    public Accommodation(String nameKor, String nameEng, String postcode, String address, String directions, String introduction, int numberPeople, float rating, String region, String filtering) {
        this.nameKor = nameKor;
        this.nameEng = nameEng;
        this.postcode = postcode;
        this.address = address;
        this.directions = directions;
        this.introduction = introduction;
        this.numberPeople = numberPeople;
        this.rating = rating;
        this.region = region;
        this.filtering = filtering;
    }

    public static Accommodation newAccommodation(AccommodationReqDto dto){
        return Accommodation.builder()
                .nameKor(dto.getNameKor())
                .nameEng(dto.getNameEng())
                .postcode(dto.getPostcode())
                .address(dto.getAddress())
                .directions(dto.getDirections())
                .introduction(dto.getIntroduction())
                .numberPeople(dto.getNumberPeople())
                .region(dto.getRegion())
                .filtering(dto.getFiltering())
                .build();
    }

    public void update(AccommodationReqDto dto){
        this.nameKor = dto.getNameKor();
        this.nameEng = dto.getNameEng();
        this.postcode = dto.getPostcode();
        this.address = dto.getAddress();
        this.directions = dto.getDirections();
        this.introduction = dto.getIntroduction();
        this.numberPeople = dto.getNumberPeople();
        this.filtering = dto.getFiltering();
    }
}


