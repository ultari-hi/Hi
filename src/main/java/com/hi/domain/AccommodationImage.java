package com.hi.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class AccommodationImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accommdation_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    private String url;

    public AccommodationImage(Long id, Accommodation accommodation, String url) {
        this.id = id;
        this.accommodation = accommodation;
        this.url = url;
    }

    public AccommodationImage(Accommodation accommodation, String url) {
        this.accommodation = accommodation;
        this.url = url;
    }


    public static AccommodationImage create(Accommodation accommodation, String url){
        return new AccommodationImage(accommodation, url);
    }
}