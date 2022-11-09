package com.hi.controller;

import com.hi.config.auth.CustomUserDetails;
import com.hi.dto.accommodation.ReviewReqDto;
import com.hi.dto.accommodation.ReviewResDto;
import com.hi.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    //리뷰 쓰기 기능
    @PostMapping("/review/new")
    public String newReview(@AuthenticationPrincipal CustomUserDetails user, @RequestBody ReviewReqDto dto){
        return reviewService.createReview(user.getUser(), dto);
    }

    //숙소에 대한 리뷰 조회
    @GetMapping("/accommodation/{accommodationId}/review")
    public List<ReviewResDto> findReview(@PathVariable Long accommodationId) {
        return reviewService.findReview(accommodationId);
    }
}
