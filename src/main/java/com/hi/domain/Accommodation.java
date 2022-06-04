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
    @Column(name = "accommodation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "accommodation")
    private List<Room> room = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation")
    private List<AccommodationImage> accommodationImage = new ArrayList<>();

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

    private int price;

    @ColumnDefault("0.0")
    private Float rating;

    private String region;

    private String filtering;

    @Builder
    public Accommodation(User user, String nameKor, String nameEng, int postCode, String address, String location, String introduction, int numberOfPeople, int price, Float rating, String region, String filtering) {
        this.user = user;
        this.nameKor = nameKor;
        this.nameEng = nameEng;
        this.postCode = postCode;
        this.address = address;
        this.location = location;
        this.introduction = introduction;
        this.numberOfPeople = numberOfPeople;
        this.price = price;
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
                .filtering(dto.getFiltering())
                .build();
    }

    public Accommodation modifyAccommodation(AccommodationReqDto dto){
        return Accommodation.builder()
                .nameKor(dto.getNameKor())
                .nameEng(dto.getNameEng())
                .postCode(dto.getPostCode())
                .address(dto.getAddress())
                .location(dto.getLocation())
                .introduction(dto.getIntroduction())
                .filtering(dto.getFiltering())
                .build();
    }


}


