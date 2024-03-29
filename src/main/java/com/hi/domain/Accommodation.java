package com.hi.domain;


import com.google.common.collect.ImmutableList;
import com.hi.dto.accommodation.AccommodationReqDto;
import lombok.*;

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
    @OrderBy("priceKor asc")
    private final List<Room> rooms = new ArrayList<>();

    @Column(name = "name_kor", columnDefinition = "varchar(50)", nullable = false)
    private String nameKor;

    @Column(name = "name_eng", columnDefinition = "varchar(50)", nullable = false)
    private String nameEng;

    @Column(name = "postcode", columnDefinition = "varchar(10)")
    private String postcode;

    @Column(name = "address", columnDefinition = "varchar(255)", nullable = false)
    private String address;

    @Column(name = "directions", columnDefinition = "varchar(255)")
    private String directions;

    @Column(name = "introduction", columnDefinition = "text")
    private String introduction;

    @Column(name = "region", columnDefinition = "varchar(30)", nullable = false)
    private String region;

    @Column(name = "filtering", columnDefinition = "text")
    private String filtering;

    @Builder
    public Accommodation(Long id, User user, String nameKor, String nameEng, String postcode, String address, String directions, String introduction, String region, List<String> filtering) {
        this.id = id;
        this.user = user;
        this.nameKor = nameKor;
        this.nameEng = nameEng;
        this.postcode = postcode;
        this.address = address;
        this.directions = directions;
        this.introduction = introduction;
        this.region = region;
        this.filtering = combineFiltering(filtering);
    }

    public static Accommodation newAccommodation(AccommodationReqDto dto, User user){
        return Accommodation.builder()
                .user(user)
                .nameKor(dto.getNameKor())
                .nameEng(dto.getNameEng())
                .postcode(dto.getPostcode())
                .address(dto.getAddress())
                .directions(dto.getDirections())
                .introduction(dto.getIntroduction())
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
        this.filtering = combineFiltering((dto.getFiltering()));
    }

    //객실이 포함되어있는지 검증
    public boolean hasRoom() {
        return !this.rooms.isEmpty();
    }

    //숙소에 포함된 최저가 객실
    public Room cheapestRoom(){
        return this.getRooms().get(0);
    }

    //필터링 한 단어로 만들기
    public String combineFiltering(List<String> filtering){
        StringBuilder combinedFiltering = new StringBuilder();
        for (String keywords: ImmutableList.sortedCopyOf(filtering)) {
            combinedFiltering.append(keywords).append(",");
        }
        return new String(combinedFiltering);
    }

    //한 단어로 합쳐서 넣었던 필터링 분리
    public List<String> separateLetters(String filtering){
        return List.of(filtering.split(","));
    }
}


