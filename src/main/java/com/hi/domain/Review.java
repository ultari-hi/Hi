package com.hi.domain;

import com.hi.dto.accommodation.ReviewReqDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", columnDefinition = "bigint")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Column(name = "content", columnDefinition = "float(2,1)", nullable = false)
    private String content;

    @Column(name = "cleanliness", columnDefinition = "float(2,1)", nullable = false)
    private float cleanliness;

    @Column(name = "facilities", columnDefinition = "float(2,1)", nullable = false)
    private float facilities;

    @Column(name = "service", columnDefinition = "float(2,1)", nullable = false)
    private float service;

    @Column(name = "cost_effectiveness", columnDefinition = "float(2,1)", nullable = false)
    private float costEffectiveness;

    @Column(name = "rating", columnDefinition = "float(3,2)", nullable = false)
    private float rating;

    @Builder
    public Review(User user, Room room, Reservation reservation, String content, float cleanliness, float facilities, float service, float costEffectiveness, float rating) {
        this.user = user;
        this.room = room;
        this.reservation = reservation;
        this.content = content;
        this.cleanliness = cleanliness;
        this.facilities = facilities;
        this.service = service;
        this.costEffectiveness = costEffectiveness;
        this.rating = rating;
    }

    public static Review newReview(User user, Room room, Reservation reservation, ReviewReqDto dto){
        return Review.builder()
                .user(user)
                .room(room)
                .reservation(reservation)
                .content(dto.getContent())
                .cleanliness(dto.getCleanliness())
                .facilities(dto.getFacilities())
                .service(dto.getService())
                .costEffectiveness(dto.getCostEffectiveness())
                .rating(calRating(dto))
                .build();
    }

    public static float calRating(ReviewReqDto dto) {
        return (dto.getCleanliness() + dto.getFacilities() + dto.getService() + dto.getCostEffectiveness())/4;
    }
}
