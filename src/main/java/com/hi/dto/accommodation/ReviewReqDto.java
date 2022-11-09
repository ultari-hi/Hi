package com.hi.dto.accommodation;

import lombok.Data;

@Data
public class ReviewReqDto {
    private Long roomId;
    private Long reservationId;
    private String content;
    private float cleanliness;
    private float facilities;
    private float service;
    private float costEffectiveness;
}
