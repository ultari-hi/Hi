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
    @Column(name = "accommodation_image_id", columnDefinition = "bigint")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @Column(name = "url", columnDefinition = "text", nullable = false)
    private String url;

    public AccommodationImage(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    public AccommodationImage(Accommodation accommodation, String url) {
        this.accommodation = accommodation;
        this.url = url;
    }


    public static AccommodationImage newAccommodationImg(Accommodation accommodation, String url){
        return new AccommodationImage(accommodation, url);
    }
}