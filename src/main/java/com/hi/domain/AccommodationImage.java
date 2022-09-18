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

    @Column(name = "acm_image_url", columnDefinition = "text", nullable = false)
    private String acmImageUrl;

    public AccommodationImage(Long id, String acmImageUrl) {
        this.id = id;
        this.acmImageUrl = acmImageUrl;
    }

    public AccommodationImage(Accommodation accommodation, String acmImageUrl) {
        this.accommodation = accommodation;
        this.acmImageUrl = acmImageUrl;
    }


    public static AccommodationImage create(Accommodation accommodation, String acmImageUrl){
        return new AccommodationImage(accommodation, acmImageUrl);
    }
}