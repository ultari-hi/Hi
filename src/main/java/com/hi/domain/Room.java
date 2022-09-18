package com.hi.domain;

import com.hi.dto.RoomReqDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id", columnDefinition = "bigint")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @OneToMany(mappedBy = "room")
    private final List<RoomImage> roomImage = new ArrayList<>();

    @Column(name = "name", columnDefinition = "varchar(20)", nullable = false)
    private String name;

    @Column(name = "information", columnDefinition = "text")
    private String information;

    @Column(name = "guide", columnDefinition = "text")
    private String guide;

    @Column(name = "price", columnDefinition = "varchar(10)", nullable = false)
    @ColumnDefault("0")
    private String price;

    @Column(name = "price_kor", columnDefinition = "integer", nullable = false)
    @ColumnDefault("0")
    private Integer priceKor;

    @Column(name = "number_of_people", columnDefinition = "integer", nullable = false)
    private Integer numberOfPeople;

    @Column(name = "type", columnDefinition = "varchar(10)", nullable = false)
    private String type;

    @Column(name = "filtering", columnDefinition = "text")
    private String filtering;

    @Column(name = "available", columnDefinition = "boolean", nullable = false)
    @ColumnDefault("True")
    private Boolean available;

    @Builder
    public Room(Long id, Accommodation accommodation, String name, String information, String guide, Integer price, Integer numberOfPeople, Boolean available) {
        this.id = id;
        this.accommodation = accommodation;
        this.name = name;
        this.information = information;
        this.guide = guide;
        this.priceKor = price;
        this.numberOfPeople = numberOfPeople;
        this.available = available;
    }

    public static Room createRoom(RoomReqDto dto, Accommodation accommodation){
        return Room.builder()
                .accommodation(accommodation)
                .name(dto.getName())
                .information(dto.getInformation())
                .guide(dto.getGuide())
                .price(dto.getPrice())
                .numberOfPeople(dto.getNumberOfPeople())
                .build();
    }

    public void update(RoomReqDto dto){
        this.name = dto.getName();
        this.information = dto.getInformation();
        this.guide = dto.getGuide();
        this.priceKor = dto.getPrice();
        this.numberOfPeople = dto.getNumberOfPeople();
        this.available = dto.getAvailable();
    }
}
