package com.hi.dto.accommodation;

import com.hi.domain.Review;
import lombok.Data;

@Data
public class ReviewResDto {
    private Long id;
    private String content;
    private float cleanliness;
    private float facilities;
    private float service;
    private float costEffectiveness;
    private float rating;

    public ReviewResDto(Review review) {
        this.id = review.getId();
        this.content = review.getContent();
        this.cleanliness = review.getCleanliness();
        this.facilities = review.getFacilities();
        this.service = review.getService();
        this.costEffectiveness = review.getCostEffectiveness();
        this.rating = review.getRating();
    }
}
